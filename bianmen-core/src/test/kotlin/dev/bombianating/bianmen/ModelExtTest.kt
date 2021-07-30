package dev.bombianating.bianmen

import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ModelExt.ldm
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
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

}
