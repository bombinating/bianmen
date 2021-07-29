package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.markup.html.link.ExternalLink
import org.apache.wicket.markup.html.link.PopupSettings
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [ExternalLink]s.
 */
public object ExternalLinkFactory {
    /**
     * Creates a new [ExternalLink] based on the parameters.
     *
     * @param id Wicket id of the [ExternalLink]
     * @param href the model for the [ExternalLink]
     * @param refType how the [ExternalLink] may be updated from the server
     * @param visibleWhen model of when the [ExternalLink] is visible
     * @param enabledWhen model of when the [ExternalLink] is enabled
     * @param renderBodyOnly whether to only render the body of the [ExternalLink]
     * @param escapeModelStrings whether to escape the [ExternalLink]'s model string
     * @param config lambda for configuring the [ExternalLink]
     */
    @Suppress("LongParameterList")
    public fun externalLink(
        id: String = idGenerator(),
        href: IModel<String>?,
        label: IModel<String>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        popupSettings: PopupSettings? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): ExternalLink {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return ExternalLink(id, href, label).config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        ).apply {
            popupSettings?.let { setPopupSettings(it) }
        }
    }

}
