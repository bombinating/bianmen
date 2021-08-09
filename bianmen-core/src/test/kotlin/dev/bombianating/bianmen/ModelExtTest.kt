package dev.bombianating.bianmen

import dev.bombinating.bianmen.ModelExt.ldm
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.ModelExt.plus
import dev.bombinating.bianmen.ModelExt.unaryPlus
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class ModelExtTest {

    @Test
    fun `get obj`() {
        val model = Model.of("test")
        assertEquals("test", model.obj)
    }

    @Test
    fun `set obj`() {
        val model = Model.of("test")
        model.obj = "random"
        assertEquals("random", model.obj)
    }

    @Test
    fun `ldm refreshes data after detach`() {
        var i: Int = 1
        val model = ldm { i }
        assertEquals(1, model.obj)
        model.detach()
        i++
        assertEquals(2, model.obj)
    }

    @TestFactory
    fun `model ext`() = listOf(true, "Hi", 5, 3.14, null).map {
        DynamicTest.dynamicTest("obj=$it") {
            val model = it.model()
            assertEquals(it, model.obj)
        }
    }

    @Test
    fun `list model`() {
        val list = listOf("A", "B", "C")
        val model = list.listModel()
        assertEquals(list, model.obj)
    }

    @TestFactory
    fun `model unary plus`() = listOf(true, "Hi", 5, 3.14, null).map {
        DynamicTest.dynamicTest("obj=$it") {
            val model = +it
            assertEquals(it, model.obj)
        }
    }

    @Test
    fun `model plus prop test`() {
        @Suppress("SerialVersionUIDInSerializableClass")
        class Person(var firstName: String, var lastName: String): Serializable
        val personModel = +Person(firstName = "Santa", lastName = "Claus")
        val firstNameModel = personModel + Person::firstName
        assertEquals("Santa", firstNameModel.obj)
    }

    @Test
    fun `model plus lambda test`() {
        val model = +"test"
        val newModel = model + { "$it$it" }
        assertEquals("testtest", newModel.obj)
    }

}
