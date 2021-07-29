package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.form.Button

/**
 * Context for configuring an [Button].
 */
public interface ButtonContext : ComponentContext {
    /**
     * Sets a handler to be used for [Button.onError]
     *
     * @param handler lambda to invoked in [Button.onError]
     */
    public fun onError(handler: Button.() -> Unit)

    /**
     * Sets a handler to be used for [Button.onSubmit]
     *
     * @param handler lambda to invoked in [Button.onSubmit]
     */
    public fun onSubmit(handler: Button.() -> Unit)
}
