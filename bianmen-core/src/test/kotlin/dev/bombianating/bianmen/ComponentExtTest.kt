package dev.bombianating.bianmen

import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ComponentExtTest : AbstractWicketTest() {

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            Label(COMP_ID, "test").config(renderBodyOnly = it).test {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            Label(COMP_ID, "test").config(escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("model=${it?.`object` ?: "<no model>"}") {
            Label(COMP_ID, "test").config(visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("model=${it?.`object` ?: "<no model>"}") {
            Label(COMP_ID, "test").config(enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType None Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            Label(COMP_ID, "test").config(refType = it).test {
                check(it)
            }
        }
    }

    @Test
    fun `behaviors Test`() {
        Label(COMP_ID, "test").config(behaviors = listOf(AttributeAppender("class", "foo"))).test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

}
