package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.configFormComponent
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormComponentContext
import dev.bombinating.bianmen.context.FormComponentContextImpl
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [CheckBox]s.
 */
public object CheckboxFactory {
    /**
     * Creates a new [CheckBox] based on the parameters.
     *
     * @param id Wicket id of the [CheckBox]
     * @param model the model for the [CheckBox]
     * @param refType how the [CheckBox] may be updated from the server
     * @param visibleWhen model of when the [CheckBox] is visible
     * @param enabledWhen model of when the [CheckBox] is enabled
     * @param renderBodyOnly whether to only render the body of the [CheckBox]
     * @param escapeModelStrings whether to escape the [CheckBox]'s model string
     * @param config lambda for configuring the [CheckBox]
     */
    @Suppress("LongParameterList")
    public fun checkbox(
        id: String = idGenerator(),
        model: IModel<Boolean?>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        label: IModel<String>? = null,
        config: (FormComponentContext<CheckBox, Boolean>.() -> Unit)? = null
    ): CheckBox {
        val context = FormComponentContextImpl<CheckBox, Boolean>()
        config?.let { it(context) }
        return CheckBox(id, model)
            .config(
                refType = refType,
                visibleWhen = visibleWhen,
                enabledWhen = enabledWhen,
                renderBodyOnly = renderBodyOnly,
                behaviors = context.behaviors
            )
            .configFormComponent(
                label = label,
                validators = context.validators
            )
    }
}
