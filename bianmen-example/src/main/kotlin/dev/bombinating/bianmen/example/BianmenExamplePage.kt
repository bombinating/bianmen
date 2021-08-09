package dev.bombinating.bianmen.example

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.mapper.parameter.PageParameters

open class BianmenExamplePage(params: PageParameters) : WebPage(params) {
    constructor() : this(PageParameters())

    init {
        @Suppress("LeakingThis")
        add(buildHeader("pageHeader"))
        @Suppress("LeakingThis")
        explain()
    }

    protected open fun buildHeader(id: String) = BianmenExampleHeader(id)

    protected open fun explain() { }

}
