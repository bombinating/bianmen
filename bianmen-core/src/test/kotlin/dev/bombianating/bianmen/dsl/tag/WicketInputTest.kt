package dev.bombianating.bianmen.dsl.tag

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.dsl.DslPanel
import dev.bombinating.bianmen.dsl.tag.PanelTag
import dev.bombinating.bianmen.dsl.tag.WicketForm.Companion.form
import dev.bombinating.bianmen.dsl.tag.WicketInput.Companion.input
import dev.bombinating.bianmen.factory.FormFactory.form
import dev.bombinating.bianmen.factory.TextFieldFactory.textField
import kotlinx.html.InputType
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.panel.Panel
import kotlin.streams.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WicketInputTest : AbstractWicketTest() {

    @Test
    fun `input text test`() {
        object : DslPanel(id = COMP_ID) {
            override val contents: PanelTag.() -> Unit = {
                val model = "test".model()
                form(component = form(model = model)) {
                    input(component = textField(model = model)) {
                        type = InputType.text
                    }
                }
            }
        }.test {
            val panel = tester.getComponentFromLastRenderedPage(COMP_ID) as Panel
            val panelChildren = panel.streamChildren().toList()
            assertEquals(2, panelChildren.size)
            val form = panelChildren.firstOrNull { it is Form<*> } as Form<*>
            val textField = panelChildren.firstOrNull { it is TextField<*> } as TextField<*>
            assertNotNull(form)
            assertNotNull(textField)
            assertEquals(panel, form.parent)
            assertEquals(form, textField.parent)
        }
    }

}
