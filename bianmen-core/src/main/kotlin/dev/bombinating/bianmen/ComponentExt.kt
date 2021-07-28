package dev.bombinating.bianmen

import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.IValidator
import org.wicketstuff.minis.behavior.EnabledModelBehavior
import org.wicketstuff.minis.behavior.VisibleModelBehavior
import java.util.Base64
import kotlin.random.Random

/**
 * [Component]-related functionality
 */
public object ComponentExt {

    private const val ID_SIZE_BYTES: Int = 9
    internal const val wicketNamespacePrefix: String = "wicket"
    internal const val wicketIdAttr: String = "$wicketNamespacePrefix:id"

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
    public fun <T : Component> T.config(
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        behaviors: List<Behavior>? = null
    ): T {
        refType?.config(this)
        visibleWhen?.let { add(VisibleModelBehavior(it)) }
        enabledWhen?.let { add(EnabledModelBehavior(it)) }
        renderBodyOnly?.let { this.renderBodyOnly = it }
        escapeModelStrings?.let { this.escapeModelStrings = it }
        @Suppress("SpreadOperator")
        behaviors?.let { add(*it.toTypedArray()) }
        return this
    }

    /**
     * Configures a [FormComponent] based on the parameters
     *
     * @receiver [FormComponent] to configure
     * @return configured [FormComponent]
     * @param T type of the [FormComponent]
     * @param refType how the [FormComponent] may be updated from the server
     * @param visibleWhen model of when the [FormComponent] is visible
     * @param enabledWhen model of when the [FormComponent] is enabled
     * @param renderBodyOnly whether to only render the body of the [FormComponent]
     * @param escapeModelStrings whether to escape the [FormComponent]'s model string
     * @param behaviors list of [Behavior]s to add to the [FormComponent]
     * @param required whether the input is required
     * @param label model of the label for the [FormComponent]
     * @param validators list of [IValidator]s to add to the [FormComponent]
     */
    @Suppress("LongParameterList")
    public fun <T : FormComponent<M>, M> T.configFormComponent(
        required: Boolean? = null,
        label: IModel<String>? = null,
        validators: List<IValidator<in M>>? = null
    ): T {
        required?.let { this.setRequired(it) }
        label?.let { this.label = it }
        @Suppress("SpreadOperator")
        validators?.let { add(*it.toTypedArray()) }
        return this
    }

    internal fun idGenerator(): String = Base64.getUrlEncoder().encodeToString(Random.nextBytes(ID_SIZE_BYTES))

}
