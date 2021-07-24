package dev.bombinating.bianmen.context

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxButton

/**
 * Implementation of [AjaxButtonContext] that exposes the onClick lambda handler to use for an [AjaxButton].
 */
internal class AjaxButtonContextImpl : ComponentContextImpl(), AjaxButtonContext {
    /**
     * Mutable lambda to invoke in a [AjaxButton.onSubmit] method
     */
    private var _onSubmit: (AjaxButton.(target: AjaxRequestTarget) -> Unit)? = null

    /**
     * Mutable lambda to invoke in a [AjaxButton.onError] method
     */
    private var _onError: (AjaxButton.(target: AjaxRequestTarget) -> Unit)? = null

    /**
     * Read-only lambda to invoke in a [AjaxButton.onSubmit] method
     */
    internal val onSubmit: (AjaxButton.(target: AjaxRequestTarget) -> Unit)?
        get() = _onSubmit

    /**
     * Read-only lambda to invoke in a [AjaxButton.onError] method
     */
    internal val onError: (AjaxButton.(target: AjaxRequestTarget) -> Unit)?
        get() = _onError

    override fun onError(handler: AjaxButton.(target: AjaxRequestTarget) -> Unit) {
        _onError = handler
    }

    override fun onSubmit(handler: AjaxButton.(target: AjaxRequestTarget) -> Unit) {
        _onSubmit = handler
    }
}
