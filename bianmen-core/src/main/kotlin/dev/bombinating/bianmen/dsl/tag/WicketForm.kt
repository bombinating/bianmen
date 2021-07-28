package dev.bombinating.bianmen.dsl.tag

import dev.bombinating.bianmen.ComponentExt.wicketIdAttr
import dev.bombinating.bianmen.dsl.WicketTag
import kotlinx.html.FORM
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import kotlinx.html.attributesMapOf
import kotlinx.html.visit
import org.apache.wicket.Component

public class WicketForm(
    override val component: Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : FORM(initialAttributes = initialAttributes, consumer = consumer),
    WicketTag {

    public companion object {

        public fun FlowContent.form(
            component: Component,
            classes: String? = null,
            block: WicketForm.() -> Unit = {}
        ): Unit =
            WicketForm(
                component = component,
                initialAttributes = attributesMapOf("class", classes, wicketIdAttr, component.id),
                consumer = consumer
            ).visit(block)

    }

}
