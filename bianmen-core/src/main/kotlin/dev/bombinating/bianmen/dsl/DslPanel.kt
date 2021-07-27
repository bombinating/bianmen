package dev.bombinating.bianmen.dsl

import dev.bombinating.bianmen.dsl.tag.PanelTag
import dev.bombinating.bianmen.dsl.tag.PanelTag.Companion.panel
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.AbstractResourceStream
import org.apache.wicket.util.resource.IResourceStream
import java.io.ByteArrayInputStream
import java.io.InputStream

/**
 * [Panel] where the HTML is defined using a DSL, and the [org.apache.wicket.Component]s associated with the markup
 * are defined in the DSL.
 *
 * @param id Wicket id for the panel
 * @param model [IModel] for the panel
 */
public abstract class DslPanel(id: String, model: IModel<*>? = null) : Panel(id, model), IMarkupResourceStreamProvider {
    /**
     * Markup derived from the DSL
     */
    private var markup: String? = null

    /**
     * DSL lambda defining both the markup and the [org.apache.wicket.Component]s associated with the markup
     */
    protected abstract val contents: PanelTag.() -> Unit

    override fun onInitialize() {
        val dsl = dsl()
        dsl.panel { contents.invoke(this) }
        val regionInfo = dsl.finalize()
        markup = regionInfo.markup
        super.onInitialize()
        add(*regionInfo.roots.toTypedArray())
    }

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream? =
        object : AbstractResourceStream() {
            @Suppress("EmptyFunctionBlock")
            override fun close() { }

            override fun getInputStream(): InputStream = object : InputStream() {
                var wrappedInputStream: InputStream? = null
                override fun read(): Int {
                    if (wrappedInputStream == null) {
                        wrappedInputStream = ByteArrayInputStream(markup?.toByteArray())
                    }
                    return wrappedInputStream?.read() ?: -1
                }
            }
        }

}
