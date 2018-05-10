package pw.stamina.occasum.properties.enums

import pw.stamina.occasum.properties.PropertyParseException

interface EnumParsingService<T : Enum<T>> {

    val enumConstants: Array<out T>

    @Throws(PropertyParseException::class)
    fun parse(input: String): T

    fun toString(value: T): String

    companion object {

        fun <T : Enum<T>> standard(enumClass: Class<T>): EnumParsingService<T> {
            return StandardEnumParsingService(enumClass)
        }

        fun <T : Enum<T>> caseInsensitive(enumClass: Class<T>): EnumParsingService<T> {
            return CaseInsensitiveEnumParsingService(enumClass)
        }
    }
}
