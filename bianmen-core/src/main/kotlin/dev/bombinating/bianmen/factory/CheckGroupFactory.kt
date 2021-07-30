package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.configFormComponent
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormComponentContext
import dev.bombinating.bianmen.context.FormComponentContextImpl
import org.apache.wicket.markup.html.form.CheckGroup
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [CheckGroup]s.
 */
public object CheckGroupFactory {
    /**
     * Creates a new [CheckGroup] based on the parameters.
     *
     * @param T type of [CheckGroup] to create
     * @param id Wicket id of the [CheckGroup]
     * @param model the model for the [CheckGroup]
     * @param refType how the [CheckGroup] may be updated from the server
     * @param visibleWhen model of when the [CheckGroup] is visible
     * @param enabledWhen model of when the [CheckGroup] is enabled
     * @param renderBodyOnly whether to only render the body of the [CheckGroup]
     * @param escapeModelStrings whether to escape the [CheckGroup]'s model string
     * @param config lambda for configuring the [CheckGroup]
     */
    @Suppress("LongParameterList")
    public fun <T> checkGroup(
        id: String = idGenerator(),
        model: IModel<out Collection<T>>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        required: Boolean? = null,
        label: IModel<String>? = null,
        config: (FormComponentContext<CheckGroup<T>, Collection<T>>.() -> Unit)? = null
    ): CheckGroup<out T> {
        val context = FormComponentContextImpl<CheckGroup<T>, Collection<T>>()
        config?.let { it(context) }
        return CheckGroup(id, model)
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
