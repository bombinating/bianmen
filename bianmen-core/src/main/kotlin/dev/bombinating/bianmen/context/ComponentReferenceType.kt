package dev.bombinating.bianmen.context

import org.apache.wicket.Component

/**
 * The ways in which a [Component] on the client can be reference from the server.
 */
public enum class ComponentReferenceType(
    private val outputMarkupId: Boolean,
    private val outputMarkupPlaceholderTag: Boolean
) {
    /**
     * The [Component] cannot be updated on the client from the server.
     * This corresponds to setting both the [Component.setOutputMarkupId] and [Component.setOutputMarkupPlaceholderTag]
     * properties of a [Component] to false.
     */
    None(outputMarkupId = false, outputMarkupPlaceholderTag = false),

    /**
     * The [Component] can be updated on the client as long as it is visible.
     * This corresponds to setting the [Component.setOutputMarkupId] to true.
     */
    Visible(outputMarkupId = true, outputMarkupPlaceholderTag = false),

    /**
     * The [Component] can always be updated on the client, regardless of whether it is visible.
     * This corresponds to setting the [Component.setOutputMarkupPlaceholderTag] to true.
     */
    Always(outputMarkupId = true, outputMarkupPlaceholderTag = true)
    ;

    /**
     * Sets the [Component.setOutputMarkupId] and [Component.setOutputMarkupPlaceholderTag] flags to the appropriate
     * values for the given [Component].
     *
     * @param component [Component] on which to set the [Component.setOutputMarkupId] and
     * [Component.setOutputMarkupPlaceholderTag] values, based on the enum
     */
    public fun config(component: Component) {
        component.outputMarkupId = outputMarkupId
        component.outputMarkupPlaceholderTag = outputMarkupPlaceholderTag
    }

}
