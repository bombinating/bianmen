package dev.bombinating.bianmen.factory

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.LambdaChoiceRenderer

/**
 * Factory for creating [IChoiceRenderer]s.
 */
public object ChoiceRendererFactory {
    /**
     * Returns an [IChoiceRenderer] where the display and id values are provided by the lambda parameters.
     *
     * @param T type of the [IChoiceRenderer] to return
     * @param display lambda to use to create a display value
     * @param id lambda to use to create an id value
     * @return [IChoiceRenderer] built from the lambda parameters
     */
    public fun <T> choiceRenderer(
        display: ((T) -> Any?)? = null,
        id: ((T) -> Any?)? = null
    ): IChoiceRenderer<T> = LambdaChoiceRenderer(display, id)
}
