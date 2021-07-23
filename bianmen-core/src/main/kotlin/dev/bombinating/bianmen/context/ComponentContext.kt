package dev.bombinating.bianmen.context

import org.apache.wicket.behavior.Behavior

/**
 * Context for configuring a Wicket [org.apache.wicket.Component].
 */
public interface ComponentContext {
    /**
     * [Behavior]s to add to a [org.apache.wicket.Component]
     * @param behaviors [Behavior]s to add to a [org.apache.wicket.Component]
     */
    public fun add(vararg behaviors: Behavior)
}
