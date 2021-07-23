package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.WebMarkupContainerFactory.webMarkupContainer
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class WebMarkupContainerFactoryTest : AbstractWicketTest() {

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            webMarkupContainer(id = COMP_ID, model = "Test".model(), renderBodyOnly = it).test {
                assertEquals(it ?: false, renderBodyOnly)
            }
        }
    }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null).map {
        dynamicTest("escapeModelStrings=$it") {
            webMarkupContainer(id = COMP_ID, model = "Test".model(), escapeModelStrings = it).test {
                assertEquals(it ?: true, escapeModelStrings)
            }
        }
    }

    @TestFactory
    fun `visibleWhen Test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            webMarkupContainer(id = COMP_ID, model = "Test".model(), visibleWhen = it).test {
                tester.assertVisibleValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("visibleWhen=${it?.obj}") {
            webMarkupContainer(id = COMP_ID, model = "Test".model(), enabledWhen = it).test {
                tester.assertEnabledValue(COMP_ID, it?.obj)
            }
        }
    }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values().map {
        dynamicTest("refType=$it") {
            webMarkupContainer(id = COMP_ID, refType = it).test {
                check(it)
            }
        }
    }

    @Test
    fun `add Behavior Test`() {
        webMarkupContainer(id = COMP_ID) { add(AttributeAppender("class", "foo")) }.test {
            tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
        }
    }

}

