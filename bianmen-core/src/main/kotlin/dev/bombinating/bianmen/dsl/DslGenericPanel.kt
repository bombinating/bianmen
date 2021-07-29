package dev.bombinating.bianmen.dsl

import org.apache.wicket.IGenericComponent
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel

/**
 * [Panel] where the HTML is defined using a DSL, and the [org.apache.wicket.Component]s associated with the markup
 * are defined in the DSL.
 *
 * @param id Wicket id for the panel
 * @param model [IModel] for the panel
 */
public abstract class DslGenericPanel<T>(id: String, model: IModel<T>? = null) : DslPanel(id, model),
    IGenericComponent<T, GenericPanel<T>>
