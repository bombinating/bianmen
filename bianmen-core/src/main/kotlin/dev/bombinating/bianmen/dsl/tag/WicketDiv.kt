package dev.bombinating.bianmen.dsl.tag

import dev.bombinating.bianmen.ComponentExt.wicketIdAttr
import dev.bombinating.bianmen.dsl.WicketTag
import kotlinx.html.DIV
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import kotlinx.html.attributesMapOf
import kotlinx.html.visit
import org.apache.wicket.Component

public class WicketDiv(
    override val component: Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : DIV(initialAttributes = initialAttributes, consumer = consumer),
    WicketTag

public fun FlowContent.div(
    component: Component,
    classes: String? = null,
    block: WicketDiv.() -> Unit = {}
): Unit =
    WicketDiv(
        component = component,
        initialAttributes = attributesMapOf("class", classes, wicketIdAttr, component.id),
        consumer = consumer
    ).visit(block)
