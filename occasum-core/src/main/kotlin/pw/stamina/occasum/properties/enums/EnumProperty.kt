package pw.stamina.occasum.properties.enums

import pw.stamina.occasum.properties.ParameterizedProperty
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.traits.Cyclable

import java.util.Objects
import java.util.function.Function

class EnumProperty<T : Enum<T>> internal constructor(
        name: String,
        value: T,
        private val parsingService: EnumParsingService<T>)
    : ParameterizedProperty<T>(name, value), Cyclable {

    private val valueOrdinal: Int
        get() = get()!!.ordinal

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        set(parsingService.parse(input))
    }

    override fun valueToString(value: T): String {
        return parsingService.toString(value)
    }

    override fun cycleForward() {
        //TODO
    }

    override fun cycleBackward() {
        //TODO
    }

    companion object {

        fun <T : Enum<T>> from(name: String, value: T): EnumProperty<T> {
            return from(name, value, Function { EnumParsingService.standard(it) })
        }

        fun <T : Enum<T>> from(name: String, value: T, parsingServiceCreator: Function<Class<T>, EnumParsingService<T>>): EnumProperty<T> {

            val enumClass = value.getDeclaringClass()
            val parsingService = parsingServiceCreator.apply(enumClass)

            return EnumProperty(name, value, parsingService)
        }
    }
}
