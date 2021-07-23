package dev.bombinating.bianmen.context

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink

/**
 * Context for configuring an [AjaxLink].
 *
 * @param T type of [AjaxLink] to configure
 */
public interface AjaxLinkContext<T> : ComponentContext {
    /**
     * Sets a handler to be used for [AjaxLink.onClick]
     *
     * @param handler lambda to invoked in [AjaxLink.onClick]
     */
    public fun onClick(handler: AjaxLink<T>.(target: AjaxRequestTarget) -> Unit)
}
