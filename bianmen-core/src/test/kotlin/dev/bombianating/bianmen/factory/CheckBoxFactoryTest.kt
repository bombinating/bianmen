package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.CheckboxFactory.checkbox
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.apache.wicket.validation.validator.RangeValidator
import org.apache.wicket.validation.validator.StringValidator
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckBoxFactoryTest : AbstractWicketTest() {

    companion object {
        private fun checkboxMarkup(id: String = COMP_ID) = """<input type="checkbox" wicket:id="$id"/>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            checkbox(id = COMP_ID, model = true.model(), renderBodyOnly = it).test(markup = checkboxMarkup()) {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            checkbox(id = COMP_ID, model = false.model(), visibleWhen = it).test(markup = checkboxMarkup()) {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            checkbox(id = COMP_ID, model = true.model(), enabledWhen = it).test(markup = checkboxMarkup()) {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            checkbox(id = COMP_ID, model = true.model(), refType = it).test(markup = checkboxMarkup()) {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        checkbox(id = COMP_ID, model = false.model()) { add(behavior) }.test(markup = checkboxMarkup()) {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("required=$it") {
            checkbox(id = COMP_ID, model = false.model(), label = it).test(checkboxMarkup()) {
                assertEquals(it, label)
            }
        }
    }

    @Test
    fun `validator Test`() {
        checkbox(id = COMP_ID, model = false.model()) { validate(RangeValidator.minimum(true)) }
            .test(checkboxMarkup()) {
                tester.assertBehavior(COMP_ID, RangeValidator::class.java)
            }
    }

    @Test
    fun `null model Test`() {
        checkbox(id = COMP_ID).test(markup = checkboxMarkup()) {
            assertEquals(Boolean::class.javaObjectType, type)
        }
    }

}

