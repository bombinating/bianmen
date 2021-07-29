package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.form.Button

/**
 * Implementation of [ButtonContext] that exposes the onClick lambda handler to use for an [Button].
 */
internal class ButtonContextImpl : ComponentContextImpl(), ButtonContext {
    /**
     * Mutable lambda to invoke in a [Button.onSubmit] method
     */
    private var _onSubmit: (Button.() -> Unit)? = null

    /**
     * Mutable lambda to invoke in a [Button.onError] method
     */
    private var _onError: (Button.() -> Unit)? = null

    /**
     * Read-only lambda to invoke in a [Button.onSubmit] method
     */
    internal val onSubmit: (Button.() -> Unit)?
        get() = _onSubmit

    /**
     * Read-only lambda to invoke in a [Button.onError] method
     */
    internal val onError: (Button.() -> Unit)?
        get() = _onError

    override fun onError(handler: Button.() -> Unit) {
        _onError = handler
    }

    override fun onSubmit(handler: Button.() -> Unit) {
        _onSubmit = handler
    }
}
