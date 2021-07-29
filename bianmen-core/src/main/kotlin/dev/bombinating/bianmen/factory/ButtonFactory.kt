package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ButtonContext
import dev.bombinating.bianmen.context.ButtonContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [Button]s.
 */
public object ButtonFactory {
    /**
     * Creates a new [Button] based on the parameters.
     *
     * @param T type of [Button] to create
     * @param id Wicket id of the [Button]
     * @param model the model for the [Button]
     * @param refType how the [Button] may be updated from the server
     * @param visibleWhen model of when the [Button] is visible
     * @param enabledWhen model of when the [Button] is enabled
     * @param renderBodyOnly whether to only render the body of the [Button]
     * @param escapeModelStrings whether to escape the [Button]'s model string
     * @param config lambda for configuring the [Button]
     */
    @Suppress("LongParameterList")
    public fun button(
        id: String = idGenerator(),
        model: IModel<String>? = null,
        defaultFormProcessing: Boolean? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (ButtonContext.() -> Unit)? = null
    ): Button {
        val context = ButtonContextImpl()
        config?.let { it(context) }
        val submitHandler = context.onSubmit
        val errorHandler = context.onError
        return object : Button(id, model) {
            override fun onSubmit() {
                submitHandler?.invoke(this)
            }
            override fun onError() {
                errorHandler?.invoke(this)
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
