package dev.bombianating.bianmen.dsl

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.dsl.DslListView
import dev.bombinating.bianmen.dsl.tag.WicketSpan.Companion.span
import kotlinx.html.FlowContent
import org.apache.wicket.markup.html.basic.Label
import java.io.Serializable
import kotlin.test.Test

class DslListViewTest : AbstractWicketTest() {

    companion object {
        @Suppress("SerialVersionUIDInSerializableClass")
        class Person(val firstName: String, val lastName: String) : Serializable
    }

    @Test
    fun h() {
        val x = object : DslListView<Person>(id = "testId", model = listOf(Person("First", "Last")).listModel()) {
            override val populate: FlowContent.() -> Unit = {
                span(component = Label("col1", "test"))
                span(component = Label("col2", "test"))
            }
        }
        x.test {
            tester.assertComponent("testId:0:col1", Label::class.java)
        }
    }
}
