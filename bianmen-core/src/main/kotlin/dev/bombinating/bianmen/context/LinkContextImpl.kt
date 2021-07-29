package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.link.Link

/**
 * Implementation of [LinkContext] that exposes the onClick lambda handler to use for an [Link].
 *
 * @param T type of [Link] to configure
 */
internal class LinkContextImpl<T> : ComponentContextImpl(), LinkContext<T> {
    /**
     * Mutable lambda to invoke in a [Link.onClick] method
     */
    private var _onClick: (Link<T>.() -> Unit)? = null

    /**
     * Read-only lambda to invoke in a [Link.onClick] method
     */
    internal val onClick: (Link<T>.() -> Unit)?
        get() = _onClick

    override fun onClick(handler: Link<T>.() -> Unit) {
        _onClick = handler
    }
}
