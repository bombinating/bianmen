package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.form.validation.IFormValidator

/**
 * Context for configuring a Wicket [org.apache.wicket.markup.html.form.Form].
 */
public interface FormContext : ComponentContext {
    /**
     * [IFormValidator]s to add to a [org.apache.wicket.markup.html.form.Form]
     * @param validators [IFormValidator]s to add to a [org.apache.wicket.markup.html.form.Form]
     */
    public fun validate(vararg validators: IFormValidator)

    /**
     * Sets a handler to be used for [org.apache.wicket.markup.html.form.Form.onSubmit]
     *
     * @param handler lambda to invoked in [org.apache.wicket.markup.html.form.Form.onSubmit]
     */
    public fun onSubmit(handler: () -> Unit)

    /**
     * Sets a handler to be used for [org.apache.wicket.markup.html.form.Form.onError]
     *
     * @param handler lambda to invoked in [org.apache.wicket.markup.html.form.Form.onError]
     */
    public fun onError(handler: () -> Unit)
}
