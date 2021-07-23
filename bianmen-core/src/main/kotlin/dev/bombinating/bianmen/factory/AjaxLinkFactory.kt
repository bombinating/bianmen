package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.context.AjaxLinkContext
import dev.bombinating.bianmen.context.AjaxLinkContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [AjaxLink]s.
 */
public object AjaxLinkFactory {
    /**
     * Creates a new [AjaxLink] based on the parameters.
     *
     * @param T type of [AjaxLink] to create
     * @param id Wicket id of the [AjaxLink]
     * @param model the model for the [AjaxLink]
     * @param refType how the [AjaxLink] may be updated from the server
     * @param visibleWhen model of when the [AjaxLink] is visible
     * @param enabledWhen model of when the [AjaxLink] is enabled
     * @param renderBodyOnly whether to only render the body of the [AjaxLink]
     * @param escapeModelStrings whether to escape the [AjaxLink]'s model string
     * @param config lambda for configuring the [AjaxLink]
     */
    @Suppress("LongParameterList")
    public fun <T> ajaxLink(
        id: String,
        model: IModel<T>?,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (AjaxLinkContext<T>.() -> Unit)? = null
    ): AjaxLink<T> {
        val context = AjaxLinkContextImpl<T>()
        config?.let { it(context) }
        val clickHandler = context.onClick
        return object : AjaxLink<T>(id, model) {
            override fun onClick(target: AjaxRequestTarget) {
                clickHandler?.invoke(this, target)
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
     * Creates a new [AjaxLink] based on the parameters.
     *
     * @param T type of [AjaxLink] to create
     * @param id Wicket id of the [AjaxLink]
     * @param refType how the [AjaxLink] may be updated from the server
     * @param visibleWhen model of when the [AjaxLink] is visible
     * @param enabledWhen model of when the [AjaxLink] is enabled
     * @param renderBodyOnly whether to only render the body of the [AjaxLink]
     * @param escapeModelStrings whether to escape the [AjaxLink]'s model string
     * @param config lambda for configuring the [AjaxLink]
     */
    @Suppress("LongParameterList")
    public fun ajaxLink(
        id: String,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (AjaxLinkContext<*>.() -> Unit)? = null
    ): AjaxLink<*> = ajaxLink<Any?>(
        id = id, model = null, refType = refType, visibleWhen = visibleWhen,
        enabledWhen = enabledWhen, renderBodyOnly = renderBodyOnly, escapeModelStrings = escapeModelStrings,
        config = config
    )
}
