package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.form.Radio
import org.apache.wicket.markup.html.form.RadioGroup
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [Radio]s.
 */
public object RadioFactory {
    /**
     * Creates a new [Radio] based on the parameters.
     *
     * @param T type of [Radio] to create
     * @param id Wicket id of the [Radio]
     * @param model the model for the [Radio]
     * @param refType how the [Radio] may be updated from the server
     * @param visibleWhen model of when the [Radio] is visible
     * @param enabledWhen model of when the [Radio] is enabled
     * @param renderBodyOnly whether to only render the body of the [Radio]
     * @param escapeModelStrings whether to escape the [Radio]'s model string
     * @param config lambda for configuring the [Radio]
     */
    @Suppress("LongParameterList")
    public fun <T> radio(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        group: RadioGroup<T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        label: IModel<String>? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): Radio<T> {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return if (group != null) Radio(id, model, group) else Radio(id, model)
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
