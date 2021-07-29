package dev.bombinating.bianmen.dsl.tag

import dev.bombinating.bianmen.ComponentExt.wicketIdAttr
import dev.bombinating.bianmen.dsl.WicketTag
import kotlinx.html.FlowContent
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.TagConsumer
import kotlinx.html.attributesMapOf
import kotlinx.html.visit
import org.apache.wicket.Component

public class WicketInput(
    override val component: Component,
    type: InputType?,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : INPUT(initialAttributes = initialAttributes, consumer = consumer),
    WicketTag {

    init {
        type?.let { this.type = it }
    }

    public companion object {

        public fun FlowContent.input(
            component: Component,
            type: InputType? = null,
            classes: String? = null,
            block: WicketInput.() -> Unit = {}
        ): Unit =
            WicketInput(
                component = component,
                type = type,
                initialAttributes = attributesMapOf("class", classes, wicketIdAttr, component.id),
                consumer = consumer
            ).visit(block)

    }

}
