package dev.bombinating.bianmen.dsl.tag

import dev.bombinating.bianmen.ComponentExt.wicketNamespacePrefix
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.TagConsumer
import kotlinx.html.visitAndFinalize

public class PanelTag(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "${wicketNamespacePrefix}:panel",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag {

    public companion object {
        public fun <T, C : TagConsumer<T>> C.panel(block: PanelTag.() -> Unit = {}): T =
            PanelTag(this).visitAndFinalize(this, block)
    }

}

