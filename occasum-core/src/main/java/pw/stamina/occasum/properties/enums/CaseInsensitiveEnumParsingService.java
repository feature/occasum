package pw.stamina.occasum.properties.enums;

import pw.stamina.occasum.properties.PropertyParseException;

final class CaseInsensitiveEnumParsingService<T extends Enum<T>>
        extends AbstractEnumParsingService<T> {

    CaseInsensitiveEnumParsingService(Class<T> enumClass) {
        super(enumClass);
    }

    @Override
    public T parse(String input) throws PropertyParseException {
        if (input == null) {
            throw PropertyParseException.nullInput();
        }

        T[] constants = enumClass.getEnumConstants();

        for (T constant : constants) {
            if (constant.name().equalsIgnoreCase(input)) {
                return constant;
            }
        }

        throw new PropertyParseException("could not parse input to an enum value", input);
    }
}