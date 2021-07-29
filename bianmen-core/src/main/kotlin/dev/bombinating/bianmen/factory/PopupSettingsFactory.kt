package dev.bombinating.bianmen.factory

import org.apache.wicket.markup.html.link.PopupSettings

/**
 * Factory methods for creating [PopupSettings]s.
 */
public object PopupSettingsFactory {
    /**
     * Creates a new [PopupSettings] based on the parameters.
     *
     * @param height popup window height
     * @param left left position of the popup window
     * @param target target of the link
     * @param top top position of the popup window
     * @param width popup window width
     * @param windowName popup window name
     * @return [PopupSettings] configured from the parameters
     */
    @Suppress("LongParameterList")
    public fun popupSettings(
        height: Int? = null,
        left: Int? = null,
        target: String? = null,
        top: Int? = null,
        width: Int? = null,
        windowName: String? = null,
        locationBar: Boolean = false,
        menuBar: Boolean = false,
        resizable: Boolean = false,
        scrollbars: Boolean = false,
        statusBar: Boolean = false,
        toolbar: Boolean = false
    ): PopupSettings {
        val flags = (if (locationBar) PopupSettings.LOCATION_BAR else 0) or
                (if (menuBar) PopupSettings.MENU_BAR else 0) or
                (if (resizable) PopupSettings.RESIZABLE else 0) or
                (if (scrollbars) PopupSettings.SCROLLBARS else 0) or
                (if (statusBar) PopupSettings.STATUS_BAR else 0) or
                (if (toolbar) PopupSettings.TOOL_BAR else 0)
        return PopupSettings(flags).apply {
            height?.let { setHeight(it) }
            left?.let { setLeft(it) }
            target?.let { setTarget(it) }
            top?.let { setTop(it) }
            width?.let { setWidth(it) }
            windowName?.let { setWindowName(it) }
        }
    }

}
