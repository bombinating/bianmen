package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.link.Link

/**
 * Context for configuring an [Link].
 *
 * @param T type of [Link] to configure
 */
public interface LinkContext<T> : ComponentContext {
    /**
     * Sets a handler to be used for [Link.onClick]
     *
     * @param handler lambda to invoked in [Link.onClick]
     */
    public fun onClick(handler: Link<T>.() -> Unit)
}
