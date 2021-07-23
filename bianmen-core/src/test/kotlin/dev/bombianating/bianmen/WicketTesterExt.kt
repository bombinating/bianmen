package dev.bombianating.bianmen

import org.apache.wicket.util.tester.WicketTester

object WicketTesterExt {

    fun WicketTester.assertVisibleValue(path: String, visible: Boolean?) =
        if (visible != false) assertVisible(path) else assertInvisible(path)

    fun WicketTester.assertEnabledValue(path: String, enabled: Boolean?) =
        if (enabled != false) assertEnabled(path) else assertDisabled(path)

}
