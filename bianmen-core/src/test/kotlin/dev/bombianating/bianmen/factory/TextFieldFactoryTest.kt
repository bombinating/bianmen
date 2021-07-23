package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.TextFieldFactory.textField
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.apache.wicket.validation.validator.StringValidator
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class TextFieldFactoryTest : AbstractWicketTest() {

    companion object {
        private fun textFieldMarkup(id: String = COMP_ID) = """<input type="text" wicket:id="$id"/>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            textField(id = COMP_ID, model = "Test".model(), renderBodyOnly = it).test(markup = textFieldMarkup()) {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            textField(id = COMP_ID, model = "Test".model(), escapeModelStrings = it).test(markup = textFieldMarkup()) {
                assertEquals(it ?: false, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            textField(id = COMP_ID, model = "Test".model(), visibleWhen = it).test(markup = textFieldMarkup()) {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            textField(id = COMP_ID, model = "Test".model(), enabledWhen = it).test(markup = textFieldMarkup()) {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            textField(id = COMP_ID, model = "test".model(), refType = it).test(markup = textFieldMarkup()) {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        textField(id = COMP_ID, model = "test".model()) { add(behavior) }.test(markup = textFieldMarkup()) {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @TestFactory
    fun `required Test`() = listOf(true, false, null).map {
        dynamicTest("required=$it") {
            textField(id = COMP_ID, model = "test".model(), required = it).test(textFieldMarkup()) {
                assertEquals(it ?: false, isRequired)
            }
        }
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("required=$it") {
            textField(id = COMP_ID, model = "test".model(), label = it).test(textFieldMarkup()) {
                assertEquals(it, label)
            }
        }
    }

    @Test
    fun `validator Test`() {
        val validator = StringValidator.exactLength(15)
        textField(id = COMP_ID, model = "test".model()) { validate(StringValidator.exactLength(15)) }
            .test(textFieldMarkup()) {
                tester.assertBehavior(COMP_ID, StringValidator::class.java)
            }
    }

}

