package dev.bombinating.bianmen.context

import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.validation.IValidator

/**
 * Context for configuring a Wicket [org.apache.wicket.markup.html.form.FormComponent].
 */
public interface FormComponentContext<T: FormComponent<M>, M> : ComponentContext {
    public fun validate(vararg validators: IValidator<M>)
}
