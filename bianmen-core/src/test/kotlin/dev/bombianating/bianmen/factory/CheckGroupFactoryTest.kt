package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.CheckGroupFactory.checkGroup
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckGroupFactoryTest : AbstractWicketTest() {

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), renderBodyOnly = it).test {
                assertEquals(it ?: true, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), refType = it).test {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        checkGroup(id = COMP_ID, listOf("A", "B").listModel()) { add(behavior) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @TestFactory
    fun `required Test`() = listOf(true, false, null).map {
        dynamicTest("required=$it") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), required = it).test {
                assertEquals(it ?: false, isRequired)
            }
        }
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("required=$it") {
            checkGroup(id = COMP_ID, model = listOf("A", "B").listModel(), label = it).test {
                assertEquals(it, label)
            }
        }
    }

}

