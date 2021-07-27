package dev.bombianating.bianmen.dsl

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombinating.bianmen.dsl.DslPanel
import dev.bombinating.bianmen.dsl.tag.PanelTag
import dev.bombinating.bianmen.dsl.tag.div
import dev.bombinating.bianmen.factory.LabelFactory.label
import dev.bombinating.bianmen.factory.WebMarkupContainerFactory.webMarkupContainer
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import kotlin.test.Test

class DslPanelTest : AbstractWicketTest() {

    @Test
    fun `one element test`() {
        object : DslPanel(id = COMP_ID) {
            override val contents: PanelTag.() -> Unit = {
                div(component = webMarkupContainer(id = "test"))
            }
        }.test {
            tester.assertComponent("$COMP_ID:test", WebMarkupContainer::class.java)
        }
    }
    @Test
    fun `nested element test`() {
        object : DslPanel(id = COMP_ID) {
            override val contents: PanelTag.() -> Unit = {
                div(component = webMarkupContainer(id = "test")) {
                    div(component = label(id = "test2", label = "hi"))
                }
            }
        }.test {
            tester.assertComponent("$COMP_ID:test", WebMarkupContainer::class.java)
            tester.assertComponent("$COMP_ID:test:test2", Label::class.java)
        }
    }

}
