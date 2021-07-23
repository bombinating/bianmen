package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.LabelFactory.label
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class LabelFactoryTest : AbstractWicketTest() {

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null)
        .flatMap { renderBodyOnly -> listOf("Test", "Test".model()).map { model -> renderBodyOnly to model } }
        .map {
            dynamicTest("use model=${it.second is IModel<*>}, renderBodyOnly=${it.first}") {
                if (it.second is String) {
                    label(id = COMP_ID, label = it.second, renderBodyOnly = it.first)
                } else {
                    label(id = COMP_ID, model = it.second as IModel<*>, renderBodyOnly = it.first)
                }.test {
                    assertEquals(it.first ?: false, renderBodyOnly)
                }
            }
        }

    @TestFactory
    fun `escapeModelStrings Test`() = listOf(true, false, null)
        .flatMap { escapeModelStrings -> listOf("Test", "Test".model()).map { model -> escapeModelStrings to model } }
        .map {
            dynamicTest("use model=${it.second is IModel<*>}, escapeModelStrings=${it.first}") {
                if (it.second is String) {
                    label(id = COMP_ID, label = it.second, escapeModelStrings = it.first)
                } else {
                    label(id = COMP_ID, model = it.second as IModel<*>, escapeModelStrings = it.first)
                }.test {
                    assertEquals(it.first ?: true, escapeModelStrings)
                }
            }
        }

    @TestFactory
    fun `visibleWhen Test`() = listOf(Model.of(true), Model.of(false), null)
        .flatMap { visibleWhen -> listOf("Test", "Test".model()).map { model -> visibleWhen to model } }
        .map {
            dynamicTest("use model=${it.second is IModel<*>}, visibleWhen=${it.first?.obj}") {
                if (it.second is String) {
                    label(id = COMP_ID, label = it.second, visibleWhen = it.first)
                } else {
                    label(id = COMP_ID, model = it.second as IModel<*>, visibleWhen = it.first)
            }.test {
                tester.assertVisibleValue(COMP_ID, it.first?.obj)
            }
        }
    }

    @TestFactory
    fun `enabledWhen Test`() = listOf(Model.of(true), Model.of(false), null)
        .flatMap { enabledWhen -> listOf("Test", "Test".model()).map { model -> enabledWhen to model } }
        .map {
            dynamicTest("use model=${it.second is IModel<*>}, enabledWhen=${it.first?.obj}") {
                if (it.second is String) {
                    label(id = COMP_ID, label = it.second, enabledWhen = it.first)
                } else {
                    label(id = COMP_ID, model = it.second as IModel<*>, enabledWhen = it.first)
                }.test {
                    tester.assertEnabledValue(COMP_ID, it.first?.obj)
                }
            }
        }

    @TestFactory
    fun `refType Test`() = ComponentReferenceType.values()
        .flatMap { refType -> listOf("Test", "Test".model()).map { model -> refType to model } }
        .map {
            dynamicTest("use model=${it.second is IModel<*>}, refType=${it.first}") {
                if (it.second is String) {
                    label(id = COMP_ID, label = it.second, refType = it.first)
                } else {
                    label(id = COMP_ID, model = it.second as IModel<*>, refType = it.first)
            }.test {
                check(it.first)
            }
        }
    }

    @TestFactory
    fun `add Behavior Test`() = listOf("test".model(), "test").map {
        dynamicTest("use model=${it is IModel<*>}") {
            val behavior = AttributeAppender("class", "foo")
            if (it is IModel<*>) {
                label(id = COMP_ID, model = it) { add(behavior) }
            } else {
                label(id = COMP_ID, label = it) { add(behavior) }
            }.test {
                tester.assertBehavior(COMP_ID, AttributeAppender::class.java)
            }
        }
    }

}

