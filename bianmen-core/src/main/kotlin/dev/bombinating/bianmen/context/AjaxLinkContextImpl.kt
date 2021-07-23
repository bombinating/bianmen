package dev.bombinating.bianmen.context

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink

/**
 * Implementation of [AjaxLinkContext] that exposes the onClick lambda handler to use for an [AjaxLink].
 *
 * @param T type of [AjaxLink] to configure
 */
internal class AjaxLinkContextImpl<T> : ComponentContextImpl(), AjaxLinkContext<T> {
    /**
     * Mutable lambda to invoke in a [AjaxLink.onClick] method
     */
    private var _onClick: (AjaxLink<T>.(target: AjaxRequestTarget) -> Unit)? = null

    /**
     * Read-only lambda to invoke in a [AjaxLink.onClick] method
     */
    internal val onClick: (AjaxLink<T>.(target: AjaxRequestTarget) -> Unit)?
        get() = _onClick

    override fun onClick(handler: AjaxLink<T>.(target: AjaxRequestTarget) -> Unit) {
        _onClick = handler
    }
}
