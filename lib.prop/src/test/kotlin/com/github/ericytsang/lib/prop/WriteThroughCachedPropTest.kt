package com.github.ericytsang.lib.prop

import org.junit.Test
import java.util.concurrent.LinkedBlockingQueue
import kotlin.test.assertEquals

class WriteThroughCachedPropTest
{
    private val actions = LinkedBlockingQueue<String>()
    private fun getActions() = generateSequence {actions.poll()}.toList()
    private val underlyingProp = object:Prop<Unit,Int>()
    {
        private var field = 1
        override fun doGet(context:Unit):Int
        {
            actions += "get() = $field"
            return field
        }

        override fun doSet(context:Unit,value:Int)
        {
            actions += "set($value)"
            field = value
        }
    }
    private val cachedProp = WriteThroughCachedProp(underlyingProp)

    @Test
    fun set_sets_underlying_property_without_listeners()
    {
        assert(getActions().isEmpty())
        cachedProp.value = 2
        assertEquals(
                listOf(
                        "set(2)"),  // setting underlying prop
                getActions())
    }

    @Test
    fun set_sets_underlying_property_with_listener_on_underlying_prop()
    {
        assert(getActions().isEmpty())
        underlyingProp.listen {}
        cachedProp.value = 2
        assertEquals(
                listOf(
                        "set(2)"),  // setting underlying prop
                getActions())
    }

    @Test
    fun set_sets_underlying_property_with_listener_on_cached_prop()
    {
        assert(getActions().isEmpty())
        cachedProp.listen {}
        cachedProp.value = 2
        assertEquals(
                listOf(
                        "set(2)"),  // setting underlying prop
                getActions())
    }

    @Test
    fun set_sets_underlying_property_with_listener_on_cached_prop_and_underlying_prop()
    {
        assert(getActions().isEmpty())
        underlyingProp.listen {}
        cachedProp.listen {}
        cachedProp.value = 2
        assertEquals(
                listOf(
                        "set(2)"),  // setting underlying prop
                getActions())
    }

    @Test
    fun set_then_get_sets_underlying_property_get_underlying_prop_only_called_on_get_after_set()
    {
        assert(getActions().isEmpty())
        cachedProp.value
        assertEquals("get() = 1",getActions().single())
        cachedProp.value
        assert(getActions().isEmpty())
        cachedProp.value = 2
        assertEquals("set(2)",getActions().single())
        cachedProp.value
        assertEquals("get() = 2",getActions().single())
        cachedProp.value
        assert(getActions().isEmpty())
    }

    @Test
    fun get_gets_correct_value()
    {
        assertEquals(1,cachedProp.value)
    }

    @Test
    fun get_gets_correct_value_after_set()
    {
        cachedProp.value = 2
        assertEquals(2,cachedProp.value)
    }

    @Test
    fun get_calls_get_from_underlying_prop_on_first_get_call()
    {
        assert(getActions().isEmpty())
        cachedProp.value
        assertEquals(
                listOf("get() = 1"),
                getActions())
    }

    @Test
    fun get_does_not_call_get_from_underlying_prop_on_subsequent_get_calls()
    {
        assert(getActions().isEmpty())
        cachedProp.value
        assertEquals(listOf("get() = 1"),getActions())
        cachedProp.value
        assert(getActions().isEmpty())
    }
}
