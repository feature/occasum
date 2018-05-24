package pw.stamina.occasum.properties

import pw.stamina.occasum.Named

/**
 * @see pw.stamina.occasum.properties.primitives.IntProperty
 * @see pw.stamina.occasum.properties.primitives.DoubleProperty
 * @see pw.stamina.occasum.properties.primitives.BooleanProperty
 * @see pw.stamina.occasum.properties.enums.EnumProperty
 * @see pw.stamina.occasum.properties.selective.SelectiveProperty
 */
interface Property<T> : Named {

    var value: T

    val default: T

    fun reset()

    val isDefault: Boolean

    val valueAsString: String

    val defaultValueAsString: String

    @Throws(PropertyParseException::class)
    fun parseAndSet(input: String)
}
