package com.github.ericytsang.lib.prop

import java.io.Closeable
import java.lang.Exception
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.withLock
import kotlin.concurrent.write

abstract class Prop<Context:Any,Value:Any>:MutableProp<Context,Value>
{
    companion object
    {
        var debugMode = false
        private fun debug(string:String)
        {
            if (debugMode) println(string)
        }
    }

    private val readWriteLock = ReentrantReadWriteLock()
    private val notifyingListenersLock = ReentrantLock()

    final override operator fun get(context:Context):Value
    {
        return readWriteLock.read()
        {
            val value = doGet(context)
            debug("$this.get[$context] = $value")
            value
        }
    }

    protected abstract fun doGet(context:Context):Value

    override operator fun set(context:Context,value:Value):Value
    {
        debug("$this.set[$context] = $value")
        return readWriteLock.write()
        {
            check(notifyingListenersLock.isHeldByCurrentThread.not()) {throw RecursiveSettingIsNotAllowedException()}
            notifyingListenersLock.withLock()
            {
                doSet(context,value)
                listeners.toList().forEach {it(this)}
                value
            }
        }
    }

    protected abstract fun doSet(context:Context,value:Value)

    private val listeners = mutableSetOf<(ReadOnlyProp<Context,Value>)->Unit>()

    final override fun listen(onChanged:(ReadOnlyProp<Context,Value>)->Unit):Closeable
    {
        listeners += onChanged
        return Closeable {listeners -= onChanged}
    }

    class RecursiveSettingIsNotAllowedException:Exception("recursive call to set detected; recursive setting is not allowed.")
}
