package pw.stamina.occasum.properties.enums

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.traits.Cyclable

class EnumProperty<T : Enum<T>> internal constructor(
        name: String,
        override var value: T,
        private val parsingService: EnumParsingService<T>)
    : AbstractProperty<T>(name), Cyclable {

    override val default: T = value

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        value = parsingService.parse(input)
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
            return from(name, value, EnumParsingService.Companion::standard)
        }

        fun <T : Enum<T>> from(name: String, value: T,
                               parsingServiceCreator: (Class<T>) -> EnumParsingService<T>): EnumProperty<T> {

            val enumClass = value.declaringClass
            val parsingService = parsingServiceCreator(enumClass)

            return EnumProperty(name, value, parsingService)
        }
    }
}
