package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.HomePage
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.BookmarkablePageLinkFactory.bookmarkablePageLink
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BookmarkablePageLinkFactoryTest : AbstractWicketTest() {

    @TestFactory
    fun `id Test`() = listOf(COMP_ID, null).map {
        dynamicTest("value=${if (it == null) "<not set>" else "<set>"}") {
            val c = if (it == null) {
                bookmarkablePageLink(page = HomePage::class.java)
            } else {
                bookmarkablePageLink(id = it, page = HomePage::class.java)
            }
            assertNotNull(c.id)
        }
    }

    @TestFactory
    fun `param test`() = listOf(PageParameters(), PageParameters().add("test", 1), null).map {
        dynamicTest("value=$it") {
            bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, params = it).test {
                assertEquals(it ?: PageParameters(), pageParameters)
            }
        }
    }

    @TestFactory
    fun `renderBodyOnly test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, renderBodyOnly = it).test {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("value=${it?.obj}") {
            bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null).map {
        dynamicTest("value=${it?.obj}") {
            bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values()
        .map {
            dynamicTest("value=$it") {
                bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java, refType = it).test {
                    check(it, none = true to false)
                }
            }
        }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        bookmarkablePageLink(id = COMP_ID, page = HomePage::class.java) { add(behavior) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

}

