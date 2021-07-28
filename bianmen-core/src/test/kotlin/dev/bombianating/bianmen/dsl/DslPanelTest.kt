package dev.bombianating.bianmen.dsl

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombinating.bianmen.dsl.DslPanel
import dev.bombinating.bianmen.dsl.tag.PanelTag
import dev.bombinating.bianmen.dsl.tag.WicketDiv.Companion.div
import dev.bombinating.bianmen.dsl.tag.WicketSpan.Companion.span
import dev.bombinating.bianmen.factory.LabelFactory.label
import dev.bombinating.bianmen.factory.WebMarkupContainerFactory.webMarkupContainer
import kotlinx.html.span
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import java.util.Base64
import kotlin.random.Random
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
                    span(component = label(id = "test2", label = "hi") {
                        span { +"hi" }
                    })
                }
            }
        }.test {
            tester.assertComponent("$COMP_ID:test", WebMarkupContainer::class.java)
            tester.assertComponent("$COMP_ID:test:test2", Label::class.java)
        }
    }

}
