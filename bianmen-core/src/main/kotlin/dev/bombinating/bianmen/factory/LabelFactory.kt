package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import java.io.Serializable

/**
 * Factory methods for creating [Label]s.
 */
public object LabelFactory {
    /**
     * Creates a new [Label] based on the parameters.
     *
     * @param id Wicket id of the [Label]
     * @param model the model for the [Label]
     * @param refType how the [Label] may be updated from the server
     * @param visibleWhen model of when the [Label] is visible
     * @param enabledWhen model of when the [Label] is enabled
     * @param renderBodyOnly whether to only render the body of the [Label]
     * @param escapeModelStrings whether to escape the [Label]'s model string
     * @param config lambda for configuring the [Label]
     */
    @Suppress("LongParameterList")
    public fun label(
        id: String = idGenerator(),
        model: IModel<*>?,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): Label {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return Label(id, model).config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        )
    }

    /**
     * Creates a new [Label] based on the parameters.
     *
     * @param id Wicket id of the [Label]
     * @param label the object for the [Label] model
     * @param refType how the [Label] may be updated from the server
     * @param visibleWhen model of when the [Label] is visible
     * @param enabledWhen model of when the [Label] is enabled
     * @param renderBodyOnly whether to only render the body of the [Label]
     * @param escapeModelStrings whether to escape the [Label]'s model string
     * @param config lambda for configuring the [Label]
     */
    @Suppress("LongParameterList")
    public fun label(
        id: String = idGenerator(),
        label: Serializable,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): Label = label(
        id = id,
        model = label.model(),
        refType = refType,
        visibleWhen = visibleWhen,
        enabledWhen = enabledWhen,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        config = config
    )

}
