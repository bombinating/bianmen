package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.ExternalLinkFactory.externalLink
import dev.bombinating.bianmen.factory.PopupSettingsFactory.popupSettings
import org.apache.wicket.behavior.AttributeAppender
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import dev.bombianating.bianmen.TestUtils.get

class ExternalLinkFactoryTest : AbstractWicketTest() {

    companion object {
        private val URL_MODEL = "https://wicket.apache.org".model()
    }

    @TestFactory
    fun `id Test`() = listOf(COMP_ID, null).map {
        dynamicTest("id=${if (it == null) "<not set>" else "<set>"}") {
            val c = if (it == null) {
                externalLink(href = URL_MODEL)
            } else {
                externalLink(id = it, href = URL_MODEL)
            }
            assertNotNull(c.id)
        }
    }

    @Test
    fun `model Test`() {
        val link = externalLink(id = COMP_ID, href = URL_MODEL)
        assertEquals(URL_MODEL.obj, link.defaultModelObject)
    }

    @TestFactory
    fun `label Test`() = listOf("Test".model(), null).map {
        dynamicTest("label=${it?.obj}") {
            externalLink(id = COMP_ID, href = URL_MODEL, label = it).test {
                assertEquals(it, body)
            }
        }
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            externalLink(id = COMP_ID, href = URL_MODEL, renderBodyOnly = it).test {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            externalLink(id = COMP_ID, href = URL_MODEL, escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("visibleWhen=$it") {
            externalLink(id = COMP_ID, href = URL_MODEL, visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("enabledWhen=$it") {
            externalLink(id = COMP_ID, href = URL_MODEL, enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            externalLink(id = COMP_ID, href = URL_MODEL, refType = it).test {
                check(it, none = true to false)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        val behavior = AttributeAppender("class", "foo")
        externalLink(id = COMP_ID, href = URL_MODEL) { add(behavior) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

    @Test
    fun `popup Settings Test`() {
        val left = 15
        externalLink(id = COMP_ID, href = URL_MODEL, popupSettings = popupSettings(left = left)).test {
            assertEquals(left, popupSettings["left"])
        }
    }

}
