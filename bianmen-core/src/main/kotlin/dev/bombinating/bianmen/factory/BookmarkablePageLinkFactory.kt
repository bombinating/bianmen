package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentContext
import dev.bombinating.bianmen.context.ComponentContextImpl
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.Page
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

/**
 * Factory methods for creating [BookmarkablePageLink]s.
 */
public object BookmarkablePageLinkFactory {
    /**
     * Creates a new [BookmarkablePageLink] based on the parameters.
     *
     * @param T type of [BookmarkablePageLink] to create
     * @param id Wicket id of the [BookmarkablePageLink]
     * @param model the model for the [BookmarkablePageLink]
     * @param refType how the [BookmarkablePageLink] may be updated from the server
     * @param visibleWhen model of when the [BookmarkablePageLink] is visible
     * @param enabledWhen model of when the [BookmarkablePageLink] is enabled
     * @param renderBodyOnly whether to only render the body of the [BookmarkablePageLink]
     * @param escapeModelStrings whether to escape the [BookmarkablePageLink]'s model string
     * @param config lambda for configuring the [BookmarkablePageLink]
     */
    @Suppress("LongParameterList")
    public fun <C: Page> bookmarkablePageLink(
        id: String = idGenerator(),
        page: Class<C>,
        params: PageParameters? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        config: (ComponentContext.() -> Unit)? = null
    ): BookmarkablePageLink<*> {
        val context = ComponentContextImpl()
        config?.let { it(context) }
        return BookmarkablePageLink<Any?, C>(id, page, params).config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        )
    }

}
