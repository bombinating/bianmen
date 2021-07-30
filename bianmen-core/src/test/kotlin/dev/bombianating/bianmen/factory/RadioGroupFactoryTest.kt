package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.RadioGroupFactory.radioGroup
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.apache.wicket.validation.validator.RangeValidator
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RadioGroupFactoryTest : AbstractWicketTest() {

    @Test
    fun `nullable model object Test`() {
        radioGroup(id = COMP_ID, model = null.model()).test {
            assertNull(this.defaultModelObject)
        }
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            radioGroup(id = COMP_ID, model = "Test".model(), renderBodyOnly = it).test {
                assertEquals(it ?: true, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            radioGroup(id = COMP_ID, model = "Test".model(), escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            radioGroup(id = COMP_ID, model = "Test".model(), visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            radioGroup(id = COMP_ID, model = "Test".model(), enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            radioGroup(id = COMP_ID, model = "test".model(), refType = it).test {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        radioGroup(id = COMP_ID, model = "test".model()) { add(behavior) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @TestFactory
    fun `required Test`() = listOf(true, false, null).map {
        dynamicTest("required=$it") {
            radioGroup(id = COMP_ID, model = "test".model(), required = it).test {
                assertEquals(it ?: false, isRequired)
            }
        }
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("required=$it") {
            radioGroup(id = COMP_ID, model = "test".model(), label = it).test {
                assertEquals(it, label)
            }
        }
    }

    @Test
    fun `validator Test`() {
        radioGroup(id = COMP_ID, model = 5.model()) { validate(RangeValidator.maximum(10)) }.test{
                tester.assertBehavior(COMP_ID, RangeValidator::class.java)
            }
    }

}

