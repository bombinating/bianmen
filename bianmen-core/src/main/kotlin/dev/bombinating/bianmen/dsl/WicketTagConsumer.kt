package dev.bombinating.bianmen.dsl

import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.stream.HTMLStreamBuilder
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.w3c.dom.events.Event

/**
 * Consumes the Wicket DSL
 */
internal class WicketTagConsumer(
    private val downstream: TagConsumer<String>
) :
    TagConsumer<RegionInfo> {

    private var roots: MutableList<Component> = mutableListOf()
    private var current: Component? = null

    override fun onTagStart(tag: Tag) {
        if (tag is WicketTag) {
            val nextComponent = tag.component
            current?.let {
                if (it is MarkupContainer) {
                    it.add(nextComponent)
                } else {
                    throw RuntimeException("Not a MarkupContainer")
                }
            } ?: roots.add(nextComponent)
            current = nextComponent
        }
        downstream.onTagStart(tag)
    }

    @Suppress("EmptyFunctionBlock")
    override fun onTagComment(content: CharSequence) {}

    @Suppress("EmptyFunctionBlock")
    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {}

    @Suppress("EmptyFunctionBlock")
    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {}

    override fun onTagEnd(tag: Tag) {
        downstream.onTagEnd(tag)
        if (tag is WicketTag) {
            current = current?.parent
        }
    }

    override fun onTagContent(content: CharSequence) {
        downstream.onTagContent(content)
    }

    override fun onTagContentEntity(entity: Entities) {
        downstream.onTagContentEntity(entity)
    }

    override fun finalize(): RegionInfo =
        RegionInfo(markup = downstream.finalize(), roots = roots)

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        downstream.onTagContentUnsafe(block)
    }
}

public fun dsl(): TagConsumer<RegionInfo> = WicketTagConsumer(
    downstream = HTMLStreamBuilder(
        out = StringBuilder(),
        prettyPrint = false,
        xhtmlCompatible = true
    ).onFinalizeMap { sb, _ -> sb.toString() })
