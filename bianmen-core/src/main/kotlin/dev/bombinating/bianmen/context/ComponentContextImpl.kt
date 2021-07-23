package dev.bombinating.bianmen.context

import org.apache.wicket.behavior.Behavior

/**
 * Implementation of [ComponentContext] that exposes the list of [Behavior]s to add to the [org.apache.wicket.Component]
 */
internal open class ComponentContextImpl : ComponentContext {
    /**
     * Private, mutable list of [Behavior]s to add to a [org.apache.wicket.Component]
     */
    private val _behaviors: MutableList<Behavior> = mutableListOf()

    /**
     * Read-only list of [Behavior]s to add to a [org.apache.wicket.Component]
     */
    internal val behaviors: List<Behavior>
        get() = _behaviors

    override fun add(vararg behaviors: Behavior) {
        _behaviors.addAll(behaviors)
    }
}
