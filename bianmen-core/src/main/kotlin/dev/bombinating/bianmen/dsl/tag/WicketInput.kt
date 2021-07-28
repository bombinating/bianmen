package dev.bombinating.bianmen.dsl.tag

import dev.bombinating.bianmen.ComponentExt.wicketIdAttr
import dev.bombinating.bianmen.dsl.WicketTag
import kotlinx.html.FlowContent
import kotlinx.html.INPUT
import kotlinx.html.TagConsumer
import kotlinx.html.attributesMapOf
import kotlinx.html.visit
import org.apache.wicket.Component

public class WicketInput(
    override val component: Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : INPUT(initialAttributes = initialAttributes, consumer = consumer),
    WicketTag {

    public companion object {

        public fun FlowContent.input(
            component: Component,
            classes: String? = null,
            block: WicketInput.() -> Unit = {}
        ): Unit =
            WicketInput(
                component = component,
                initialAttributes = attributesMapOf("class", classes, wicketIdAttr, component.id),
                consumer = consumer
            ).visit(block)

    }

}
