package pw.stamina.occasum.properties.enums

import pw.stamina.occasum.properties.PropertyParseException

internal class StandardEnumParsingService<T : Enum<T>>(enumClass: Class<T>) : AbstractEnumParsingService<T>(enumClass) {

    @Throws(PropertyParseException::class)
    override fun parse(input: String): T {
        try {
            return Enum.valueOf<T>(enumClass, input)
        } catch (e: NullPointerException) {
            throw PropertyParseException(e, input)
        } catch (e: IllegalArgumentException) {
            throw PropertyParseException(e, input)
        }

    }
}
