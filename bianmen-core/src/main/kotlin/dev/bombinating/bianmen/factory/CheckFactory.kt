package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.form.Check
import org.apache.wicket.markup.html.form.CheckGroup
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [Check]s.
 */
public object CheckFactory {
    /**
     * Creates a new [Check] based on the parameters.
     *
     * @param T type of [Check] to create
     * @param id Wicket id of the [Check]
     * @param model the model for the [Check]
     * @param refType how the [Check] may be updated from the server
     * @param visibleWhen model of when the [Check] is visible
     * @param enabledWhen model of when the [Check] is enabled
     * @param renderBodyOnly whether to only render the body of the [Check]
     * @param escapeModelStrings whether to escape the [Check]'s model string
     * @param config lambda for configuring the [Check]
     */
    @Suppress("LongParameterList")
    public fun <T> check(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        group: CheckGroup<T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        label: IModel<String>? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): Check<T> {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return if (group != null) Check(id, model, group) else Check(id, model)
            .config(
                refType = refType,
                visibleWhen = visibleWhen,
                enabledWhen = enabledWhen,
                renderBodyOnly = renderBodyOnly,
                escapeModelStrings = escapeModelStrings,
                behaviors = context.behaviors
            ).apply {
                label?.let { setLabel(it) }
            }
    }
}
