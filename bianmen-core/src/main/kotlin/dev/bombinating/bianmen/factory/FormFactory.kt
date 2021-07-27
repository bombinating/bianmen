package dev.bombinating.bianmen.factory

import dev.bombinating.bianmen.ComponentExt.config
import dev.bombinating.bianmen.ComponentExt.idGenerator
import dev.bombinating.bianmen.context.ComponentReferenceType
import dev.bombinating.bianmen.context.FormContext
import dev.bombinating.bianmen.context.FormContextImpl
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.IFormSubmittingComponent
import org.apache.wicket.model.IModel
import org.apache.wicket.util.lang.Bytes

/**
 * Factory methods for creating [Form]s.
 */
public object FormFactory {
    /**
     * Creates a new [Form] based on the parameters.
     *
     * @param T type of the [Form]
     * @param id Wicket id of the [Form]
     * @param model the model for the [Form]
     * @param refType how the [Form] may be updated from the server
     * @param visibleWhen model of when the [Form] is visible
     * @param enabledWhen model of when the [Form] is enabled
     * @param renderBodyOnly whether to only render the body of the [Form]
     * @param escapeModelStrings whether to escape the [Form]'s model string
     * @param config lambda for configuring the [Form]
     */
    @Suppress("LongParameterList")
    public fun <T> form(
        id: String = idGenerator(),
        model: IModel<T>? = null,
        refType: ComponentReferenceType? = null,
        visibleWhen: IModel<Boolean>? = null,
        enabledWhen: IModel<Boolean>? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        multiPart: Boolean? = null,
        maxSize: Bytes? = null,
        fileMaxSize: Bytes? = null,
        defaultButton: IFormSubmittingComponent? = null,
        wantSubmitOnNestedFormSubmit: Boolean? = null,
        config: (FormContext.() -> Unit)? = null
    ): Form<T> {
        val context = FormContextImpl()
        config?.let { it(context) }
        return if (wantSubmitOnNestedFormSubmit == null && !context.requiresSubclass) {
            Form(id, model)
        } else {
            val errorHandler = context.onError
            val submitHandler = context.onSubmit
            object : Form<T>(id, model) {
                override fun wantSubmitOnNestedFormSubmit(): Boolean {
                    return wantSubmitOnNestedFormSubmit ?: false
                }
                override fun onError() {
                    errorHandler?.invoke()
                }
                override fun onSubmit() {
                    submitHandler?.invoke()
                }
            }
        }
            .config(
                refType = refType,
                visibleWhen = visibleWhen,
                enabledWhen = enabledWhen,
                renderBodyOnly = renderBodyOnly,
                escapeModelStrings = escapeModelStrings,
                behaviors = context.behaviors
            ).apply {
                multiPart?.let { this.isMultiPart = multiPart }
                maxSize?.let { this.maxSize = it }
                fileMaxSize?.let { this.fileMaxSize = it }
                defaultButton?.let { this.defaultButton = defaultButton }
                context.validators.forEach { add(it) }
            }
    }
}
