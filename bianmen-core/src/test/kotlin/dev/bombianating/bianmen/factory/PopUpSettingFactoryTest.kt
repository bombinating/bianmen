package dev.bombianating.bianmen.factory

import dev.bombianating.bianmen.AbstractWicketTest
import dev.bombianating.bianmen.TestUtils.get
import dev.bombinating.bianmen.factory.PopupSettingsFactory.popupSettings
import org.apache.wicket.markup.html.link.PopupSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class PopUpSettingFactoryTest : AbstractWicketTest() {

    @Test
    fun `height test`() {
        val height = 100
        val ps = popupSettings(height = height)
        assertEquals(height, ps["height"])
    }

    @Test
    fun `left test`() {
        val left = 130
        val ps = popupSettings(left = left)
        assertEquals(left, ps["left"]);
    }

    @Test
    fun `target test`() {
        val target = "test"
        val ps = popupSettings(target = target)
        assertEquals(target, ps["target"]);
    }

    @Test
    fun `top test`() {
        val top = 150
        val ps = popupSettings(top = top)
        assertEquals(top, ps["top"]);
    }

    @Test
    fun `width test`() {
        val width = 190
        val ps = popupSettings(width = width)
        assertEquals(width, ps["width"]);
    }

    @Test
    fun `window name test`() {
        val windowName = "myName"
        val ps = popupSettings(windowName = windowName)
        assertEquals(windowName, ps["windowName"]);
    }

    @Test
    fun `location bar test`() {
        val ps = popupSettings(locationBar = true)
        assertEquals(PopupSettings.LOCATION_BAR, ps["displayFlags"]);
    }

    @Test
    fun `menu bar test`() {
        val ps = popupSettings(menuBar = true)
        assertEquals(PopupSettings.MENU_BAR, ps["displayFlags"]);
    }

    @Test
    fun `resizable test`() {
        val ps = popupSettings(resizable = true)
        assertEquals(PopupSettings.RESIZABLE, ps["displayFlags"]);
    }

    @Test
    fun `scrollbars test`() {
        val ps = popupSettings(scrollbars = true)
        assertEquals(PopupSettings.SCROLLBARS, ps["displayFlags"]);
    }

    @Test
    fun `status bar test`() {
        val ps = popupSettings(statusBar = true)
        assertEquals(PopupSettings.STATUS_BAR, ps["displayFlags"]);
    }

    @Test
    fun `toolbar test`() {
        val ps = popupSettings(toolbar = true)
        assertEquals(PopupSettings.TOOL_BAR, ps["displayFlags"]);
    }

    @Test
    fun `toolbar and status bar test`() {
        val ps = popupSettings(toolbar = true, statusBar = true)
        assertEquals(PopupSettings.TOOL_BAR or PopupSettings.STATUS_BAR, ps["displayFlags"]);
    }

}
