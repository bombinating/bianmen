package dev.bombinating.bianmen

import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.model.util.ListModel
import org.wicketstuff.minis.model.LoadableDetachableDependentModel
import java.io.Serializable
import kotlin.reflect.KProperty1

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
    public fun <T : Serializable?> T.model(): IModel<T> = Model.of(this)

    /**
     * Returns an [IModel] for the receiver list.
     *
     * @param T type of the items in the list
     * @receiver list to create the model from
     * @return model of the receiver list
     */
    public fun <T> List<T>.listModel(): IModel<List<T>> = ListModel(this)

    /**
     * Creates an [IModel] with the receiver as the object.
     *
     * @receiver value to use for the model's object
     * @param T type of the model to create
     * @return [IModel] with the receiver as the object
     */
    public operator fun <T : Serializable?> T.unaryPlus(): IModel<T> = model()

    /**
     * Creates a new [IModel] from applying the [lambda] to the receiver.
     *
     * @receiver [IModel] of type [T]
     * @param T type of the receiver model
     * @param R type of the model to create
     * @param lambda lambda for transforming the object of the receiver to an object of type [R]
     * @return [IModel] of type [R] where the object of the model is created by applying the [lambda] to the receiver
     * model object
     */
    public inline operator fun <T, R> IModel<T>.plus(crossinline lambda: (T) -> R): IModel<R> =
        object : LoadableDetachableDependentModel<R, T>(this) {
            override fun load(): R = lambda(dependentModel.obj)
        }

    /**
     * Creates a new [IModel] from applying [prop] to the receiver model.
     *
     * @param T type of the receiver model
     * @param R type of the model to create
     * @param prop property used to get the object in the new model from the receiver model
     * @return [IModel] created by applying [prop] to the receiver model
     */
    public operator fun <T, R> IModel<T>.plus(prop: KProperty1<T, R>): IModel<R> = PropertyModel(this, prop.name)

}
