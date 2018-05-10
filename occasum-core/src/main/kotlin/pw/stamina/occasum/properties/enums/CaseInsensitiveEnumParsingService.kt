package pw.stamina.occasum.properties.enums

import pw.stamina.occasum.properties.PropertyParseException

internal class CaseInsensitiveEnumParsingService<T : Enum<T>>(enumClass: Class<T>)
    : AbstractEnumParsingService<T>(enumClass) {

    @Throws(PropertyParseException::class)
    override fun parse(input: String): T {
        for (constant in enumConstants) {
            if (constant.name.equals(input, ignoreCase = true)) {
                return constant
            }
        }

        throw PropertyParseException("could not parse input to an enum value", input)
    }
}