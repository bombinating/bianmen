package dev.bombinating.bianmen.dsl

import dev.bombinating.bianmen.dsl.tag.ExtendTag.Companion.extend
import kotlinx.html.FlowContent
import org.apache.wicket.markup.IMarkupFragment
import org.apache.wicket.markup.Markup
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel

/**
 * [ListView] where the HTML is defined using a DSL, and the [org.apache.wicket.Component]s associated with the markup
 * are defined in the DSL.
 *
 * @param id Wicket id for the panel
 * @param model [IModel] for the panel
 */
public abstract class DslListView<T>(id: String, model: IModel<List<T>>) : ListView<T>(id, model) {

    /**
     * Markup derived from the DSL
     */
    private lateinit var regionInfo: RegionInfo

    protected abstract val populate: FlowContent.() -> Unit

    override fun populateItem(item: ListItem<T>) {
        @Suppress("SpreadOperator")
        item.add(*regionInfo.roots.toTypedArray())
    }

    override fun onInitialize() {
        val dsl = dsl()
        dsl.extend {
            populate(this)
        }
        regionInfo = dsl.finalize()
        super.onInitialize()
    }

    override fun getMarkup(): IMarkupFragment {
        return Markup.of(regionInfo.markup)
    }

}
