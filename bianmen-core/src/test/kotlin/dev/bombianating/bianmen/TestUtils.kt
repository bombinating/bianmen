package dev.bombianating.bianmen

import org.apache.commons.lang3.reflect.FieldUtils

object TestUtils {

    inline operator fun <reified T> T.get(fieldName: String): Any? {
        val field = FieldUtils.getField(T::class.java, fieldName, true)
        return field.get(this)
    }

}
