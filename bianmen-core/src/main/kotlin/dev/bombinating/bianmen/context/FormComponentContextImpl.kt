package dev.bombinating.bianmen.context

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.validation.IValidator

/**
 * Implementation of [FormComponentContext] that exposes the list of [IValidator]s to add to the [FormComponent].
 */
internal open class FormComponentContextImpl<T : FormComponent<M>, M> : ComponentContextImpl(),
    FormComponentContext<T, M> {
    /**
     * Private, mutable list of [Behavior]s to add to a [org.apache.wicket.Component]
     */
    private val _validators: MutableList<IValidator<M>> = mutableListOf()

    /**
     * Read-only list of [Behavior]s to add to a [org.apache.wicket.Component]
     */
    internal val validators: List<IValidator<M>>
        get() = _validators

    override fun validate(vararg validators: IValidator<M>) {
        _validators.addAll(validators)
    }
}
