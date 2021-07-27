package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.AjaxButtonContext
import dev.bombinating.bianmen.context.AjaxButtonContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [AjaxButton]s.
 */
public object AjaxButtonFactory {
    /**
     * Creates a new [AjaxButton] based on the parameters.
     *
     * @param T type of [AjaxButton] to create
     * @param id Wicket id of the [AjaxButton]
     * @param model the model for the [AjaxButton]
     * @param refType how the [AjaxButton] may be updated from the server
     * @param visibleWhen model of when the [AjaxButton] is visible
     * @param enabledWhen model of when the [AjaxButton] is enabled
     * @param renderBodyOnly whether to only render the body of the [AjaxButton]
     * @param escapeModelStrings whether to escape the [AjaxButton]'s model string
     * @param config lambda for configuring the [AjaxButton]
     */
    @Suppress("LongParameterList")
    public fun ajaxButton(
        id: String = idGenerator(),
        model: IModel<String>? = null,
        form: Form<*>? = null,
        defaultFormProcessing: Boolean? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (AjaxButtonContext.() -> Unit)? = null
    ): AjaxButton {
        val context = AjaxButtonContextImpl()
        config?.let { it(context) }
        val submitHandler = context.onSubmit
        val errorHandler = context.onError
        return object : AjaxButton(id, model, form) {
            override fun onSubmit(target: AjaxRequestTarget) {
                submitHandler?.invoke(this, target)
            }

            override fun onError(target: AjaxRequestTarget) {
                errorHandler?.invoke(this, target)
            }
        }.apply {
            defaultFormProcessing?.let { this.defaultFormProcessing = it }
        }.config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        )
    }
}
