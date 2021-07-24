package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.AjaxButtonFactory.ajaxButton
import dev.bombinating.bianmen.factory.FormFactory.form
import dev.bombinating.bianmen.factory.TextFieldFactory
import dev.bombinating.bianmen.factory.TextFieldFactory.textField
import org.apache.commons.lang3.reflect.MethodUtils
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.form.validation.EqualInputValidator
import org.apache.wicket.model.Model
import org.apache.wicket.util.lang.Bytes
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class FormFactoryTest : AbstractWicketTest() {

    companion object {
        private fun formMarkup(id: String = COMP_ID) = """<form wicket:id="$id"></form>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            form(id = COMP_ID, model = "test".model(), renderBodyOnly = it).test(formMarkup()) {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapedModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            form<Any>(id = COMP_ID, escapeModelStrings = it).test(formMarkup()) {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            form(id = COMP_ID, model = 5.model(), visibleWhen = it).test(formMarkup()) {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            form(id = COMP_ID, model = null.model(), enabledWhen = it).test(formMarkup()) {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            form(id = COMP_ID, model = null.model(), refType = it).test(formMarkup()) {
                check(it, none = true to false)
            }
        }
    }

    @Test
    fun `behaviors Test`() {
        form(id = COMP_ID, model = 5.4.model()) { add(AttributeAppender("class", "foo")) }.test(formMarkup()) {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @Test
    fun `onError Test`() {
        var i = 0
        val model = "".model()
        form(id = COMP_ID, model = model) { onError { i = 1 } }
            .add(TextFieldFactory.textField(id = "text", model = model, required = true))
            .add(ajaxButton(id = "button"))
            .test(
                markup = """
                |<form wicket:id="$COMP_ID">
                |   <input type="text" wicket:id="text"/>
                |   <button wicket:id="button"></button>
                |</form>""".trimMargin()
            ) {
                tester.newFormTester(COMP_ID).submit("button")
                assertEquals(1, i)
            }
    }

    @Test
    fun `onSubmit Test`() {
        var i = 0
        val model = "".model()
        form(id = COMP_ID, model = model) { onSubmit { i = 2 } }
            .add(TextFieldFactory.textField(id = "text", model = model))
            .add(ajaxButton(id = "button"))
            .test(
                markup = """
                |<form wicket:id="$COMP_ID">
                |   <input type="text" wicket:id="text"/>
                |   <button wicket:id="button"></button>
                |</form>""".trimMargin()
            ) {
                tester.newFormTester(COMP_ID).submit("button")
                assertEquals(2, i)
            }
    }

    @TestFactory
    fun `wantSubmitOnNestedFormSubmit Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            form<Any>(id = COMP_ID, wantSubmitOnNestedFormSubmit = it).test(formMarkup()) {
                assertEquals(it ?: false, MethodUtils.invokeMethod(this, true, "wantSubmitOnNestedFormSubmit"))
            }
        }
    }

    @TestFactory
    fun `multiPart Test`() = listOf(true, false, null).map {
        dynamicTest("multiPart=$it") {
            form(id = COMP_ID, model = BigDecimal.ZERO.model(), multiPart = it).test(formMarkup()) {
                assertEquals(it ?: false, isMultiPart)
            }
        }
    }

    @TestFactory
    fun `maxSize Test`() = listOf(Bytes.megabytes(3), Bytes.kilobytes(3.4), null).map {
        dynamicTest("maxSize=$it") {
            form(id = COMP_ID, model = BigInteger.ONE.model(), maxSize = it).test(formMarkup()) {
                assertEquals(it ?: Bytes.MAX, maxSize)
            }
        }
    }

    @TestFactory
    fun `fileMaxSize Test`() = listOf(Bytes.megabytes(3), Bytes.kilobytes(3.4), null).map {
        dynamicTest("fileMaxSize=$it") {
            form(id = COMP_ID, model = LocalDateTime.now().model(), fileMaxSize = it).test(formMarkup()) {
                assertEquals(it, fileMaxSize)
            }
        }
    }

    @TestFactory
    fun `defaultButtonTest Test`(): List<DynamicTest> {
        val button1 = ajaxButton(id = "button1")
        val button2 = ajaxButton(id = "button2")
        return listOf(button1, button2, null).map {
            dynamicTest("default button=$it") {
                val form = form(id = COMP_ID, model = "".model(), defaultButton = it)
                form.add(button1, button2)
                form.test(
                    markup = """
                |<form wicket:id="$COMP_ID">
                |   <button wicket:id="button1"></button>
                |   <button wicket:id="button2"></button>
                |</form>""".trimMargin()
                ) {
                    assertEquals(it, defaultButton)
                }
            }
        }
    }

    @Test
    fun `validator Test`() {
        val textField1 = textField(id = "field1", model = "test1".model())
        val textField2 = textField(id = "field2", model = "test2".model())
        val validator = EqualInputValidator(textField1, textField2)
        val form = form<Any>(id = COMP_ID) { validate(validator) }
        form.add(textField1, textField2)
        form.test(
            markup = """
                |<form wicket:id="$COMP_ID">
                |   <input type="text" wicket:id="field1"/>
                |   <input type="text" wicket:id="field2"/>
                |</form>""".trimMargin()
        ) {
            assertContains(this.formValidators, validator)
        }
    }

}

