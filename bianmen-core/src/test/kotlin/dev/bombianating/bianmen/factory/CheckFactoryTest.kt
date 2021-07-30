package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.listModel
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.CheckFactory.check
import dev.bombinating.bianmen.factory.CheckGroupFactory.checkGroup
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.form.LabeledWebMarkupContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.streams.asSequence
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckFactoryTest : AbstractWicketTest() {

    companion object {
        private const val GROUP_ID = "groupId"
        private fun checkMarkup(groupId: String = GROUP_ID, checkId: String = COMP_ID) =
            """<div wicket:id="$groupId"><input type="checkbox" wicket:id="$checkId"/></div>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), renderBodyOnly = it)).test(markup = checkMarkup()) {
                    assertEquals(it ?: false, streamChildren().asSequence().first().renderBodyOnly)
                }
        }
    }

    @TestFactory
    fun `escapeModelStrings test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), escapeModelStrings = it))
                .test(markup = checkMarkup()) {
                    assertEquals(it ?: true, streamChildren().asSequence().first().escapeModelStrings)
                }
        }
    }

    @TestFactory
    fun `visibleWhen test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("value=${it?.obj}") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), visibleWhen = it))
                .test(markup = checkMarkup()) {
                    tester.assertVisibleValue("$GROUP_ID:$COMP_ID", it?.obj)
                }
        }
    }

    @TestFactory
    fun `enabledWhen test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("value=${it?.obj}") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), enabledWhen = it))
                .test(markup = checkMarkup()) {
                    tester.assertEnabledValue("$GROUP_ID:$COMP_ID", it?.obj)
                }
        }
    }

    @TestFactory
    fun `refType test`() = ComponentReferenceType.values().map {
        dynamicTest("value=$it") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), refType = it))
                .test(markup = checkMarkup()) {
                    streamChildren().asSequence().first().check(it)
                }
        }
    }

    @Test
    fun `add behavior test`() {
        val behavior = AttributeAppender("class", "foo")
        checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
            .add(check(id = COMP_ID, model = "A".model()) { add(behavior) }).test(markup = checkMarkup()) {
                tester.assertBehavior("$GROUP_ID:$COMP_ID", AttributeAppender::class.java)
            }
    }

    @TestFactory
    fun `label test`() = listOf("Test".model(), null).map {
        dynamicTest("value=${it?.obj}") {
            checkGroup(id = GROUP_ID, model = listOf("A", "B").listModel())
                .add(check(id = COMP_ID, model = "A".model(), label = it))
                .test(markup = checkMarkup()) {
                    assertEquals<Any?>(it?.obj, (streamChildren().asSequence().first() as LabeledWebMarkupContainer)
                        .label?.obj)
                }
        }
    }

}

