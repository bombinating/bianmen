package dev.bombianating.bianmen

import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.Component
import org.apache.wicket.markup.Markup
import org.apache.wicket.util.tester.WicketTester
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

open class AbstractWicketTest {

    companion object {
        @JvmStatic
        protected val COMP_ID: String = "id"
    }

    protected lateinit var tester: WicketTester

    @BeforeTest
    protected fun setup() {
        tester = WicketTester(TestWebApp())
    }

    protected fun <C : Component> C.test(markup: String? = null, lambda: C.() -> Unit): Unit {
        tester.startComponentInPage(this, if (markup == null) null else Markup.of(markup))
        lambda.invoke(this)
    }

    protected fun Component.check(refType: ComponentReferenceType, none: Pair<Boolean, Boolean> = false to false) {
        val (expectedOutputMarkupId, expectedOutputMarkupPlaceholderTag) = when (refType) {
            ComponentReferenceType.None -> none
            ComponentReferenceType.Visible -> true to false
            ComponentReferenceType.Always -> true to true
        }
        assertEquals(expectedOutputMarkupId, outputMarkupId)
        assertEquals(expectedOutputMarkupPlaceholderTag, outputMarkupPlaceholderTag)
    }

}
