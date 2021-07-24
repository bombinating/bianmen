package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.form.validation.IFormValidator

/**
 * Implementation of [FormContext] that exposes the list of [IFormValidator]s to add to the
 * [org.apache.wicket.markup.html.form.Form]
 */
internal class FormContextImpl : ComponentContextImpl(), FormContext {
    /**
     * Private, mutable list of [IFormValidator]s to add to a [org.apache.wicket.markup.html.form.Form]
     */
    private val _validators: MutableList<IFormValidator> = mutableListOf()
    /**
     * Private, mutable handler to be used for [org.apache.wicket.markup.html.form.Form.onError]
     */
    private var _onError: (() -> Unit)? = null
    /**
     * Private, mutable handler to be used for [org.apache.wicket.markup.html.form.Form.onSubmit]
     */
    private var _onSubmit: (() -> Unit)? = null

    /**
     * Read-only list of [IFormValidator]s to add to a [org.apache.wicket.markup.html.form.Form]
     */
    internal val validators: List<IFormValidator>
        get() = _validators

    /**
     * Read-only handler to be used for [org.apache.wicket.markup.html.form.Form.onError]
     */
    internal val onError: (() -> Unit)?
        get() = _onError

    /**
     * Read-only handler to be used for [org.apache.wicket.markup.html.form.Form.onSubmit]
     */
    internal val onSubmit: (() -> Unit)?
        get() = _onSubmit

    override fun validate(vararg validators: IFormValidator) {
        _validators.addAll(validators)
    }

    override fun onSubmit(handler: () -> Unit) {
        _onSubmit = handler
    }

    override fun onError(handler: () -> Unit) {
        _onError = handler
    }

    internal val requiresSubclass: Boolean
        get() = onSubmit != null || onError != null

}
