package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView

/**
 * Context for configuring an [ListView].
 */
public interface ListViewContext<T> : ComponentContext {
    /**
     * Sets a handler to be used for [ListView.populateItem]
     *
     * @param handler lambda to invoked in [ListView.populateItem]
     */
    public fun populateItem(handler: ListView<T>.(item: ListItem<T>) -> Unit)
}
