package pw.stamina.occasum.properties.enums

import java.util.Objects

abstract class AbstractEnumParsingService<T : Enum<T>> protected constructor(protected val enumClass: Class<T>) : EnumParsingService<T> {

    private lateinit var enumConstants: Array<T>

    override fun toString(value: T): String {
        return value.name
    }

    override fun getEnumConstants(): Array<T> {
        if (enumConstants == null) {
            enumConstants = enumClass.enumConstants
        }

        return enumConstants
    }
}
