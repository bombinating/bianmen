package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.configFormComponent
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormComponentContext
import dev.bombinating.bianmen.context.FormComponentContextImpl
import org.apache.wicket.markup.html.form.RadioGroup
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [RadioGroup]s.
 */
public object RadioGroupFactory {
    /**
     * Creates a new [RadioGroup] based on the parameters.
     *
     * @param T type of [RadioGroup] to create
     * @param id Wicket id of the [RadioGroup]
     * @param model the model for the [RadioGroup]
     * @param refType how the [RadioGroup] may be updated from the server
     * @param visibleWhen model of when the [RadioGroup] is visible
     * @param enabledWhen model of when the [RadioGroup] is enabled
     * @param renderBodyOnly whether to only render the body of the [RadioGroup]
     * @param escapeModelStrings whether to escape the [RadioGroup]'s model string
     * @param config lambda for configuring the [RadioGroup]
     */
    @Suppress("LongParameterList")
    public fun <T> radioGroup(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        required: Boolean? = null,
        label: IModel<String>? = null,
        config: (FormComponentContext<RadioGroup<T>, T>.() -> Unit)? = null
    ): RadioGroup<T> {
        val context = FormComponentContextImpl<RadioGroup<T>, T>()
        config?.let { it(context) }
        @Suppress("UNCHECKED_CAST")
        return RadioGroup(id, model)
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
