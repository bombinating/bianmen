package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.AjaxButtonFactory
import dev.bombinating.bianmen.factory.AjaxLinkFactory.ajaxLink
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AjaxLinkFactoryTest : AbstractWicketTest() {

    @TestFactory
    fun `id Test`() = listOf(COMP_ID, null)
        .flatMap { id -> listOf(null, "Test".model()).map { model -> id to model } }
        .map {
        dynamicTest("use model=${it.second != null}, id=${if (it.first == null) "<not set>" else "<set>"}") {
            val c = if (it.first == null) {
                AjaxButtonFactory.ajaxButton()
            } else {
                AjaxButtonFactory.ajaxButton(id = COMP_ID)
            }
            assertNotNull(c.id)
        }
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null)
        .flatMap { renderBodyOnly -> listOf(null, "Test".model()).map { model -> renderBodyOnly to model } }
        .map {
            dynamicTest("use model=${it.second != null}, renderBodyOnly=${it.first}") {
                if (it.second == null) {
                    ajaxLink(id = COMP_ID, renderBodyOnly = it.first)
                } else {
                    ajaxLink(id = COMP_ID, model = it.second, renderBodyOnly = it.first)
                }.test {
                    assertEquals(it.first ?: false, renderBodyOnly)
                }
            }
        }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null)
        .flatMap { escapeModelStrings -> listOf(null, "Test".model()).map { model -> escapeModelStrings to model } }
        .map {
            dynamicTest("use model=${it.second != null}, escapeModelStrings=${it.first}") {
                if (it.second == null) {
                    ajaxLink(id = COMP_ID, escapeModelStrings = it.first)
                } else {
                    ajaxLink(id = COMP_ID, model = it.second, escapeModelStrings = it.first)
                }.test {
                    assertEquals(it.first ?: true, escapeModelStrings)
                }
            }
        }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null)
        .flatMap { visibleWhen -> listOf(null, "Test".model()).map { model -> visibleWhen to model } }
        .map {
            dynamicTest("use model=${it.second != null}, visibleWhen=${it.first?.obj}") {
            if (it.second == null) {
                ajaxLink(id = COMP_ID, visibleWhen = it.first)
            } else {
                ajaxLink(id = COMP_ID, model = it.second, visibleWhen = it.first)
            }.test {
                tester.assertVisibleValue(COMP_ID, it.first?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null)
        .flatMap { enabledWhen -> listOf(null, "Test".model()).map { model -> enabledWhen to model } }
        .map {
            dynamicTest("use model=${it.second != null}, enabledWhen=${it.first?.obj}") {
                if (it.second == null) {
                    ajaxLink(id = COMP_ID, enabledWhen = it.first)
                } else {
                    ajaxLink(id = COMP_ID, model = it.second, enabledWhen = it.first)
                }.test {
                    tester.assertEnabledValue(COMP_ID, it.first?.obj)
                }
            }
        }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values()
        .flatMap { refType -> listOf(null, "Test".model()).map { model -> refType to model } }
        .map {
            dynamicTest("use model=${it.second != null}, refType=${it.first}") {
            if (it.second == null) {
                ajaxLink(id = COMP_ID, refType = it.first)
            } else {
                ajaxLink(id = COMP_ID, model = it.second, refType = it.first)
            }.test {
                check(it.first, none = true to false)
            }
        }
    }

    @TestFactory
    fun `add Behavior Test`() = listOf("test".model(), null).map {
        dynamicTest("model=${if (it == null) "<not set>" else "<set>"}") {
            val behavior = AttributeAppender("class", "foo")
            if (it != null) {
                ajaxLink(id = COMP_ID, model = it) { add(behavior) }
            } else {
                ajaxLink(id = COMP_ID) { add(behavior) }
            }.test {
                tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
            }
        }
    }

    @TestFactory
    fun `onClick Test`() = listOf("test".model(), null).map {
        dynamicTest("model=${if (it == null) "<not set>" else "<set>"}") {
            var i: Int = 0
            if (it != null) {
                ajaxLink(id = COMP_ID, model = it) { onClick { i++ } }
            } else {
                ajaxLink(id = COMP_ID) { onClick { i++ } }
            }.test {
                tester.clickLink(COMP_ID)
                assertEquals(1, i)
            }
        }
    }

}

