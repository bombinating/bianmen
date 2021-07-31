package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView

/**
 * Implementation of [ListViewContext] that exposes the onClick lambda handler to use for an [ListView].
 *
 * @param T type of [ListView] to configure
 */
internal class ListViewContextImpl<T> : ComponentContextImpl(), ListViewContext<T> {
    /**
     * Mutable lambda to invoke in a [ListView.populateItem] method
     */
    private var _populateItem: (ListView<T>.(item: ListItem<T>) -> Unit)? = null

    /**
     * Read-only lambda to invoke in a [ListView.populateItem] method
     */
    internal val populateItem: (ListView<T>.(item: ListItem<T>) -> Unit)?
        get() = _populateItem

    override fun populateItem(handler: ListView<T>.(item: ListItem<T>) -> Unit) {
        _populateItem = handler
    }
}
