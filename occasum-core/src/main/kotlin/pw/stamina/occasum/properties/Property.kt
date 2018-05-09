package pw.stamina.occasum.properties

import pw.stamina.occasum.Named

/**
 *
 *
 * This interface is not parameterized to provide better support for
 * primitive types. If you need a standard parameterized implementation,
 * take a look at the [ParameterizedProperty] class.
 *
 * @see pw.stamina.occasum.properties.primitives.IntProperty
 *
 * @see pw.stamina.occasum.properties.primitives.DoubleProperty
 *
 * @see pw.stamina.occasum.properties.primitives.BooleanProperty
 *
 * @see pw.stamina.occasum.properties.enums.EnumProperty
 *
 * @see pw.stamina.occasum.properties.selective.SelectiveProperty
 *
 * @see pw.stamina.occasum.properties.collection.SetProperty
 *
 * @see pw.stamina.occasum.properties.collection.ListProperty
 */
interface Property : Named {

    val isDefault: Boolean

    val valueAsString: String

    val defaultValueAsString: String

    @Throws(PropertyParseException::class)
    fun parseAndSet(input: String)

    fun reset()
}
