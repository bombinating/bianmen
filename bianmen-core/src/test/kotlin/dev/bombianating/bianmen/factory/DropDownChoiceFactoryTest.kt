package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.DropDownChoiceFactory.dropDownChoice
import dev.bombinating.bianmen.factory.TextFieldFactory.textField
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.apache.wicket.validation.validator.StringValidator
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DropDownChoiceFactoryTest : AbstractWicketTest() {

    companion object {
        private fun selectMarkup(id: String = COMP_ID) = """<select wicket:id="$id"></select>"""
    }

    @Test
    fun `nullable model object Test`() {
        dropDownChoice(id = COMP_ID, model = null.model(), choices = listOf("A", "B").listModel())
            .test(selectMarkup()) {
                assertNull(defaultModelObject)
            }
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                renderBodyOnly = it
            )
                .test(markup = selectMarkup()) {
                    assertEquals(it ?: false, renderBodyOnly)
                }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                escapeModelStrings = it
            ).test(markup = selectMarkup()) {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                visibleWhen = it
            ).test(markup = selectMarkup()) {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                enabledWhen = it
            ).test(markup = selectMarkup()) {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                refType = it
            ).test(markup = selectMarkup()) {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        dropDownChoice(id = COMP_ID, model = null.model(), choices = listOf("A", "B").listModel()) { add(behavior) }
            .test(markup = selectMarkup()) {
                tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
            }
    }

    @TestFactory
    fun `required Test`() = listOf(true, false, null).map {
        dynamicTest("required=$it") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                required = it
            ).test(selectMarkup()) {
                assertEquals(it ?: false, isRequired)
            }
        }
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("required=$it") {
            dropDownChoice(
                id = COMP_ID,
                model = null.model(),
                choices = listOf("A", "B").listModel(),
                label = it
            ).test(selectMarkup()) {
                assertEquals(it, label)
            }
        }
    }

    @Test
    fun `validator Test`() {
        dropDownChoice(
            id = COMP_ID,
            model = null.model(),
            choices = listOf("A", "B").listModel()
        ) { validate(StringValidator.exactLength(15)) }
            .test(selectMarkup()) {
                tester.assertBehavior(COMP_ID, StringValidator::class.java)
            }
    }

}

