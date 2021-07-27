package dev.bombinating.bianmen.dsl

import org.apache.wicket.Component

/**
 * A Wicket tag associates a [Component] with the tag.
 */
internal interface WicketTag {
    /**
     * Component to associate with a tag
     */
    val component: Component
}
