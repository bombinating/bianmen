package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.AjaxButtonFactory.ajaxButton
import dev.bombinating.bianmen.factory.TextFieldFactory
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class AjaxButtonFactoryTest : AbstractWicketTest() {

    companion object {
        private const val FORM_ID = "formId"
        private fun markup(formId: String = FORM_ID, buttonId: String = COMP_ID) =
            """<form wicket:id="$formId"><button wicket:id="$buttonId"></button></form>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID, renderBodyOnly = it)).test(markup = markup()) {
                val c = tester.getComponentFromLastRenderedPage("$FORM_ID:$COMP_ID")
                assertEquals(it ?: false, c.renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID, escapeModelStrings = it)).test(markup = markup()) {
                val c = tester.getComponentFromLastRenderedPage("$FORM_ID:$COMP_ID")
                assertEquals(it ?: false, c.escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID, visibleWhen = it)).test(markup = markup()) {
                tester.assertVisibleValue("$FORM_ID:$COMP_ID:", it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("enabledWhen=${it?.obj}") {
            Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID, enabledWhen = it)).test(markup = markup()) {
                tester.assertEnabledValue("$FORM_ID:$COMP_ID:", it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID, refType = it)).test(markup = markup()) {
                tester.getComponentFromLastRenderedPage("$FORM_ID:$COMP_ID").check(it, none = true to false)
            }
        }
    }

    @Test
    fun `behaviors Test`() {
        Form<String>(FORM_ID).add(ajaxButton(id = COMP_ID) { add(AttributeAppender("class", "foo")) })
            .test(markup = markup()) {
                tester.assertBehavior("$FORM_ID:$COMP_ID", AttributeAppender::class.java)
            }
    }

    @Test
    fun `onError Test`() {
        var i = 0
        val model = "".model()
        Form(FORM_ID, model)
            .add(TextFieldFactory.textField(id = "text", model = model, required = true))
            .add(ajaxButton(id = COMP_ID) {
                onError { i = 1 }
            })
            .test(
                markup = """
                |<form wicket:id="$FORM_ID">
                |   <input type="text" wicket:id="text"/>
                |   <button wicket:id="$COMP_ID"></button>
                |</form>""".trimMargin()
            ) {
                tester.newFormTester(FORM_ID).submit(COMP_ID)
                assertEquals(1, i)
            }
    }

    @Test
    fun `onSubmit Test`() {
        var i = 0
        val model = "test".model()
        Form(FORM_ID, model)
            .add(TextFieldFactory.textField(id = "text", model = model))
            .add(ajaxButton(id = COMP_ID) {
                onSubmit { i = 2 }
            })
            .test(
                markup = """
                |<form wicket:id="$FORM_ID">
                |   <input type="text" wicket:id="text"/>
                |   <button wicket:id="$COMP_ID"></button>
                |</form>""".trimMargin()
            ) {
                tester.newFormTester(FORM_ID).submit(COMP_ID)
                assertEquals(2, i)
            }
    }

}

