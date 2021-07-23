package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import java.io.Serializable

/**
 * Factory methods for creating [WebMarkupContainer]s.
 */
public object WebMarkupContainerFactory {
    /**
     * Creates a new [WebMarkupContainer] based on the parameters.
     *
     * @param id Wicket id of the [WebMarkupContainer]
     * @param model the model for the [WebMarkupContainer]
     * @param refType how the [WebMarkupContainer] may be updated from the server
     * @param visibleWhen model of when the [WebMarkupContainer] is visible
     * @param enabledWhen model of when the [WebMarkupContainer] is enabled
     * @param renderBodyOnly whether to only render the body of the [WebMarkupContainer]
     * @param escapeModelStrings whether to escape the [WebMarkupContainer]'s model string
     * @param config lambda for configuring the [WebMarkupContainer]
     */
    @Suppress("LongParameterList")
    public fun webMarkupContainer(
        id: String,
        model: IModel<*>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): WebMarkupContainer {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return WebMarkupContainer(id, model).config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        )
    }

}
