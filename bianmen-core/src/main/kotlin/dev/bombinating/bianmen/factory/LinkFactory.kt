package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.LinkContext
import dev.bombinating.bianmen.context.LinkContextImpl
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [Link]s.
 */
public object LinkFactory {
    /**
     * Creates a new [Link] based on the parameters.
     *
     * @param T type of [Link] to create
     * @param id Wicket id of the [Link]
     * @param model the model for the [Link]
     * @param refType how the [Link] may be updated from the server
     * @param visibleWhen model of when the [Link] is visible
     * @param enabledWhen model of when the [Link] is enabled
     * @param renderBodyOnly whether to only render the body of the [Link]
     * @param escapeModelStrings whether to escape the [Link]'s model string
     * @param config lambda for configuring the [Link]
     */
    @Suppress("LongParameterList")
    public fun <T> link(
        id: String = idGenerator(),
        model: IModel<T>?,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (LinkContext<T>.() -> Unit)? = null
    ): Link<T> {
        val context = LinkContextImpl<T>()
        config?.let { it(context) }
        val clickHandler = context.onClick
        return object : Link<T>(id, model) {
            override fun onClick() {
                clickHandler?.invoke(this)
            }
        }.config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        )
    }

    /**
     * Creates a new [Link] based on the parameters.
     *
     * @param T type of [Link] to create
     * @param id Wicket id of the [Link]
     * @param refType how the [Link] may be updated from the server
     * @param visibleWhen model of when the [Link] is visible
     * @param enabledWhen model of when the [Link] is enabled
     * @param renderBodyOnly whether to only render the body of the [Link]
     * @param escapeModelStrings whether to escape the [Link]'s model string
     * @param config lambda for configuring the [Link]
     */
    @Suppress("LongParameterList")
    public fun link(
        id: String = idGenerator(),
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (LinkContext<*>.() -> Unit)? = null
    ): Link<*> = link<Any?>(
        id = id, model = null, refType = refType, visibleWhen = visibleWhen,
        enabledWhen = enabledWhen, renderBodyOnly = renderBodyOnly, escapeModelStrings = escapeModelStrings,
        config = config
    )
}
