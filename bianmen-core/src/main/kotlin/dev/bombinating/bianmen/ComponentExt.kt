package dev.bombinating.bianmen

import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.wicketstuff.minis.behavior.EnabledModelBehavior
import org.wicketstuff.minis.behavior.VisibleModelBehavior

/**
 * [Component]-related functionality
 */
public object ComponentExt {

    /**
     * Configures a [Component] based on the parameters
     *
     * @receiver [Component] to configure
     * @return configured [Component]
     * @param T type of the [Component]
     * @param refType how the [Component] may be updated from the server
     * @param visibleWhen model of when the [Component] is visible
     * @param enabledWhen model of when the [Component] is enabled
     * @param renderBodyOnly whether to only render the body of the [Component]
     * @param escapeModelStrings whether to escape the [Component]'s model string
     * @param behaviors list of [Behavior]s to add to the [Component]
     */
    @Suppress("LongParameterList")
    public fun <T: Component> T.config(
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        behaviors: List<Behavior>? = null
    ):T {
        refType?.config(this)
        visibleWhen?.let { add(VisibleModelBehavior(it)) }
        enabledWhen?.let { add(EnabledModelBehavior(it)) }
        renderBodyOnly?.let { this.renderBodyOnly = it }
        escapeModelStrings?.let { this.escapeModelStrings = it }
        @Suppress("SpreadOperator")
        behaviors?.let { add(*it.toTypedArray()) }
        return this
    }

}
