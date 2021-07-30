package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.WicketTesterExt.assertEnabledValue
import dev.bombianating.bianmen.WicketTesterExt.assertVisibleValue
import dev.bombinating.bianmen.ModelExt.model
import dev.bombinating.bianmen.ModelExt.obj
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.factory.RadioFactory.radio
import dev.bombinating.bianmen.factory.RadioGroupFactory.radioGroup
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.markup.html.form.Radio
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.streams.asSequence
import kotlin.test.Test
import kotlin.test.assertEquals

class RadioFactoryTest : AbstractWicketTest() {

    companion object {
        private const val GROUP_ID = "groupId"
        private fun radioMarkup(groupId: String = GROUP_ID, radioId: String = COMP_ID) =
            """<div wicket:id="$groupId"><input type="radio" wicket:id="$radioId"/></div>"""
    }

    @TestFactory
    fun `renderBodyOnly Test`() = listOf(true, false, null).map {
        dynamicTest("renderBodyOnly=$it") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), renderBodyOnly = it)).test(markup = radioMarkup()) {
                    assertEquals(it ?: false, streamChildren().asSequence().first().renderBodyOnly)
                }
        }
    }

    @TestFactory
    fun `escapeModelStrings test`() = listOf(true, false, null).map {
        dynamicTest("value=$it") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), escapeModelStrings = it))
                .test(markup = radioMarkup()) {
                    assertEquals(it ?: true, streamChildren().asSequence().first().escapeModelStrings)
                }
        }
    }

    @TestFactory
    fun `visibleWhen test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("value=${it?.obj}") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), visibleWhen = it))
                .test(markup = radioMarkup()) {
                    tester.assertVisibleValue("$GROUP_ID:$COMP_ID", it?.obj)
                }
        }
    }

    @TestFactory
    fun `enabledWhen test`() = listOf(true.model(), false.model(), null).map {
        dynamicTest("value=${it?.obj}") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), enabledWhen = it))
                .test(markup = radioMarkup()) {
                    tester.assertEnabledValue("$GROUP_ID:$COMP_ID", it?.obj)
                }
        }
    }

    @TestFactory
    fun `refType test`() = ComponentReferenceType.values().map {
        dynamicTest("value=$it") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), refType = it))
                .test(markup = radioMarkup()) {
                    streamChildren().asSequence().first().check(it)
                }
        }
    }

    @Test
    fun `add behavior test`() {
        val behavior = AttributeAppender("class", "foo")
        radioGroup(id = GROUP_ID, model = "Test".model())
            .add(radio(id = COMP_ID, model = "Test2".model()) { add(behavior) }).test(markup = radioMarkup()) {
                tester.assertBehavior("$GROUP_ID:$COMP_ID", AttributeAppender::class.java)
            }
    }

    @TestFactory
    fun `label test`() = listOf("Test".model(), null).map {
        dynamicTest("value=${it?.obj}") {
            radioGroup(id = GROUP_ID, model = "Test".model())
                .add(radio(id = COMP_ID, model = "Test2".model(), label = it))
                .test(markup = radioMarkup()) {
                    assertEquals<Any?>(it?.obj, (streamChildren().asSequence().first() as Radio<*>).label?.obj)
                }
        }
    }

}

