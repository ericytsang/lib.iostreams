package com.github.ericytsang.lib.raii

import com.github.ericytsang.lib.optional.Opt
import java.io.Closeable
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class Raii<Raii:Closeable>(initialValue:Raii):ReadOnlyRaii<Raii>
{
    override var objOrNullIfClosing:Raii? = initialValue
        get() = lock.withLock {field}
        private set
    override var obj:Raii = initialValue
        get() = lock.withLock {field}
        private set

    private val lock = ReentrantLock()
    private val unblockWhenObjIsInitialized = lock.newCondition()

    private val listeners = mutableSetOf<ReadOnlyRaii.Listener>()

    fun set(raiiInstanceFactory:()->Raii):Raii = lock.withLock()
    {
        objOrNullIfClosing = null
        listeners.forEach {it.onAssigned(ReadOnlyRaii.Listener.Params(this,ReadOnlyRaii.Listener.Action.CLOSING))}
        obj.close()
        val raiiObject = raiiInstanceFactory()
        obj = raiiObject
        objOrNullIfClosing = raiiObject
        listeners.forEach {it.onAssigned(ReadOnlyRaii.Listener.Params(this,ReadOnlyRaii.Listener.Action.OPENING))}
        unblockWhenObjIsInitialized.signalAll()
        return raiiObject
    }

    override fun blockingGetNext(old:Raii):Raii = lock.withLock()
    {
        val returnedObj:Raii
        while (true)
        {
            val obj = this.obj
            if (obj != old)
            {
                returnedObj = obj
                break
            }
            else
            {
                unblockWhenObjIsInitialized.await()
            }
        }
        returnedObj
    }

    override fun <Return:Any> doWithLock(block:(Raii)->Return):Return? = lock.withLock()
    {
        block(obj)
    }

    override fun <Return:Any> doOnNextWithLock(old:Raii,block:(Raii)->Return):Return? = lock.withLock()
    {
        block(blockingGetNext(old))
    }

    override fun listen(listener:ReadOnlyRaii.Listener):Closeable = lock.withLock()
    {
        listeners += listener
        listener.onAssigned(ReadOnlyRaii.Listener.Params(this,ReadOnlyRaii.Listener.Action.NOTHING))
        Closeable()
        {
            listeners -= listener
        }
    }
}

interface ReadOnlyRaii<Raii:Closeable>
{
    /**
     * null if closed
     */
    val objOrNullIfClosing:Raii?
    val obj:Raii
    fun blockingGetNext(old:Raii):Raii
    fun <Return:Any> doWithLock(block:(Raii)->Return):Return?
    fun <Return:Any> doOnNextWithLock(old:Raii,block:(Raii)->Return):Return?
    fun listen(listener:Listener):Closeable

    interface Listener
    {
        fun onAssigned(params:Params)
        data class Params(
                val source:Raii<*>,
                val action:Action)
        enum class Action(val isClosing:Boolean)
        {
            NOTHING(false),
            OPENING(false),
            CLOSING(true);
        }
    }
}

fun <Raii:Closeable> ReadOnlyRaii<Raii>.listen(listener:(ReadOnlyRaii.Listener.Params)->Unit):Closeable
{
    val listenerObject = object:ReadOnlyRaii.Listener
    {
        override fun onAssigned(params:ReadOnlyRaii.Listener.Params)
        {
            listener(params)
        }
    }
    return listen(listenerObject)
}

fun Iterable<ReadOnlyRaii<*>>.listen(listener:(ReadOnlyRaii.Listener.Params)->Unit):Closeable
{
    val closeables = map {it.listen(listener)}
    return Closeable()
    {
        closeables.forEach {it.close()}
    }
}

class OptCloser<Wrapped:Closeable>(
        val opt:Opt<Wrapped>)
    :Closeable
{
    override fun close()
    {
        when(opt)
        {
            is Opt.Some -> opt.opt.close()
            is Opt.None -> Unit
        }
    }
}
