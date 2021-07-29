package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.configFormComponent
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormComponentContext
import dev.bombinating.bianmen.context.FormComponentContextImpl
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [TextField]s.
 */
public object TextFieldFactory {
    /**
     * Creates a new [TextField] based on the parameters.
     *
     * @param T type of [TextField] to create
     * @param id Wicket id of the [TextField]
     * @param model the model for the [TextField]
     * @param refType how the [TextField] may be updated from the server
     * @param visibleWhen model of when the [TextField] is visible
     * @param enabledWhen model of when the [TextField] is enabled
     * @param renderBodyOnly whether to only render the body of the [TextField]
     * @param escapeModelStrings whether to escape the [TextField]'s model string
     * @param config lambda for configuring the [TextField]
     */
    @Suppress("LongParameterList")
    public fun <T> textField(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        type: Class<out T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        required: Boolean? = null,
        label: IModel<String>? = null,
        config: (FormComponentContext<TextField<T>, T>.() -> Unit)? = null
    ): TextField<T> {
        val context = FormComponentContextImpl<TextField<T>, T>()
        config?.let { it(context) }
        @Suppress("UNCHECKED_CAST")
        return (if (type == null) TextField(id, model) else TextField(id, model, type as Class<T>))
            .config(
                refType = refType,
                visibleWhen = visibleWhen,
                enabledWhen = enabledWhen,
                renderBodyOnly = renderBodyOnly,
                escapeModelStrings = escapeModelStrings,
                behaviors = context.behaviors
            )
            .configFormComponent(
                required = required,
                label = label,
                validators = context.validators
            )
    }
}
