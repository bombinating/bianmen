package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.configFormComponent
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormComponentContext
import dev.bombinating.bianmen.context.FormComponentContextImpl
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [DropDownChoice]s.
 */
public object DropDownChoiceFactory {
    /**
     * Creates a new [DropDownChoice] based on the parameters.
     *
     * @param T type of [DropDownChoice] to create
     * @param id Wicket id of the [DropDownChoice]
     * @param model the model for the [DropDownChoice]
     * @param refType how the [DropDownChoice] may be updated from the server
     * @param visibleWhen model of when the [DropDownChoice] is visible
     * @param enabledWhen model of when the [DropDownChoice] is enabled
     * @param renderBodyOnly whether to only render the body of the [DropDownChoice]
     * @param escapeModelStrings whether to escape the [DropDownChoice]'s model string
     * @param config lambda for configuring the [DropDownChoice]
     */
    @Suppress("LongParameterList")
    public fun <T> dropDownChoice(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        choices: IModel<out List<T>>? = null,
        renderer: IChoiceRenderer<in T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        required: Boolean? = null,
        label: IModel<String>? = null,
        config: (FormComponentContext<DropDownChoice<T>, T>.() -> Unit)? = null
    ): DropDownChoice<T> {
        val context = FormComponentContextImpl<DropDownChoice<T>, T>()
        config?.let { it(context) }
        return DropDownChoice(id, model, choices, renderer)
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
