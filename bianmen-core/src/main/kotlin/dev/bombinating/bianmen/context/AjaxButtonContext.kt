package dev.bombinating.bianmen.context

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxButton

/**
 * Context for configuring an [AjaxButton].
 */
public interface AjaxButtonContext : ComponentContext {
    /**
     * Sets a handler to be used for [AjaxButton.onError]
     *
     * @param handler lambda to invoked in [AjaxButton.onError]
     */
    public fun onError(handler: AjaxButton.(target: AjaxRequestTarget) -> Unit)

    /**
     * Sets a handler to be used for [AjaxButton.onSubmit]
     *
     * @param handler lambda to invoked in [AjaxButton.onSubmit]
     */
    public fun onSubmit(handler: AjaxButton.(target: AjaxRequestTarget) -> Unit)
}
