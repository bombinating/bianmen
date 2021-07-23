package dev.bombinating.bianmen

import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.model.Model
import java.io.Serializable

/**
 * [IModel]-related functionality
 */
public object ModelExt {

    /**
     * Synonym for [IModel.getObject]/[IModel.setObject] that is not a Kotlin keyword
     *
     * @param T type of the model object
     */
    public var <T> IModel<T>.obj: T
        get() = `object`
        set(value: T) {
            `object` = value
        }

    /**
     * Returns a [LoadableDetachableModel] where the [lambda] generates the model object.
     *
     * @param T type of the model object
     * @param lambda lambda that provides the value for the [LoadableDetachableModel]
     * @return [LoadableDetachableModel] created from the [lambda]
     */
    public inline fun <T> ldm(crossinline lambda: () -> T): IModel<T> = object : LoadableDetachableModel<T>() {
        override fun load(): T = lambda()
    }

    /**
     * Returns an [IModel] where the object is the receiver.
     *
     * @param T type of the model object
     * @receiver value to use for the model object
     * @return model where the object is the receiver
     */
    public fun <T: Serializable?> T.model(): IModel<T> = Model.of(this)

}
