package pw.stamina.occasum.properties.enums

abstract class AbstractEnumParsingService<T : Enum<T>> protected constructor(protected val enumClass: Class<T>) : EnumParsingService<T> {

    override val enumConstants: Array<out T>
        get() = enumClass.enumConstants

    override fun toString(value: T): String {
        return value.name
    }
}
