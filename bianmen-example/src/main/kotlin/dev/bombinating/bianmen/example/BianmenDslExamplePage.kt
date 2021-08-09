package dev.bombinating.bianmen.example

import dev.bombinating.bianmen.dsl.dsl
import dev.bombinating.bianmen.dsl.tag.ExtendTag
import dev.bombinating.bianmen.dsl.tag.ExtendTag.Companion.extend
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupCacheKeyProvider
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.util.resource.IResourceStream
import org.apache.wicket.util.resource.StringResourceStream

abstract class BianmenDslExamplePage : BianmenExamplePage(), IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {


    private var markup: String? = null

    protected abstract val contents: ExtendTag.() -> Unit

    override fun onInitialize() {
        val dsl = dsl()
        dsl.extend { contents.invoke(this) }
        val regionInfo = dsl.finalize()
        markup = regionInfo.markup
        super.onInitialize()
        @Suppress("SpreadOperator")
        add(*regionInfo.roots.toTypedArray())
    }

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream =
        StringResourceStream(markup)

    override fun getCacheKey(container: MarkupContainer, containerClass: Class<*>): String =
        containerClass.name
}
