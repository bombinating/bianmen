package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.LabelFactory.label
import dev.bombinating.bianmen.factory.ListViewFactory.listView
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ListViewFactoryTest : AbstractWicketTest() {

    companion object {
        private fun markup() =
            """<div wicket:id="$COMP_ID"><span wicket:id="test"></span></div>"""

        private val DEFAULT_MODEL: IModel<List<String>> = listOf("A", "B").listModel()
    }

    @TestFactory
    fun `id Test`() = listOf(COMP_ID, null).map {
        dynamicTest("id=${if (it == null) "<not set>" else "<set>"}") {
            val c = if (it == null) {
                listView(model = DEFAULT_MODEL)
            } else {
                listView(id = COMP_ID, model = DEFAULT_MODEL)
            }
            assertNotNull(c.id)
        }
    }

    @TestFactory
    fun `reuseItems Test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, reuseItems = it).test {
                assertEquals(it ?: false, reuseItems)
            }
        }
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, renderBodyOnly = it).test {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            listView(id = COMP_ID, model = DEFAULT_MODEL, refType = it).test {
                check(it)
            }
        }
    }

    @Test
    fun `behaviors Test`() {
        listView(id = COMP_ID, model = DEFAULT_MODEL) { add(AttributeAppender("class", "foo")) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @Test
    fun `populateItem Test`() {
        listView(id = COMP_ID, model = DEFAULT_MODEL) {
            populateItem {
                add(label(id = "test", model = model))
            }
        }
            .test(markup()) {
                DEFAULT_MODEL.obj.forEachIndexed { index, s ->
                    tester.assertComponent("$COMP_ID:$index:test", Label::class.java)
                    tester.assertModelValue("$COMP_ID:$index:test", s)
                }
            }
    }

}

