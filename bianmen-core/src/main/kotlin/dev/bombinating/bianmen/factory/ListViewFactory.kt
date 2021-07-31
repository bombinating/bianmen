package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.ListViewContext
import dev.bombinating.bianmen.context.ListViewContextImpl
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel

/**
 * Factory methods for creating [ListView]s.
 */
public object ListViewFactory {
    /**
     * Creates a new [ListView] based on the parameters.
     *
     * @param id Wicket id of the [ListView]
     * @param model the model for the [ListView]
     * @param refType how the [ListView] may be updated from the server
     * @param visibleWhen model of when the [ListView] is visible
     * @param enabledWhen model of when the [ListView] is enabled
     * @param renderBodyOnly whether to only render the body of the [ListView]
     * @param escapeModelStrings whether to escape the [ListView]'s model string
     * @param config lambda for configuring the [ListView]
     */
    @Suppress("LongParameterList")
    public fun <T> listView(
        id: String = idGenerator(),
        model: IModel<List<T>>?,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        reuseItems: Boolean? = null,
        config: (ListViewContext<T>.() -> Unit)? = null
    ): ListView<T> {
        val context = ListViewContextImpl<T>()
        config?.let { it(context) }
        val handler = context.populateItem
        return object : ListView<T>(id, model) {
            override fun populateItem(item: ListItem<T>) {
                handler?.invoke(this, item)
            }
        }.config(
            refType = refType,
            visibleWhen = visibleWhen,
            enabledWhen = enabledWhen,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = context.behaviors
        ).apply {
            reuseItems?.let { setReuseItems(it) }
        }
    }

}
