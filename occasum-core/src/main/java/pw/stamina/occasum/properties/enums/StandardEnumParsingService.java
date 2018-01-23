package pw.stamina.occasum.properties.enums;

import pw.stamina.occasum.properties.PropertyParseException;

final class StandardEnumParsingService<T extends Enum<T>> extends AbstractEnumParsingService<T> {

    StandardEnumParsingService(Class<T> enumClass) {
        super(enumClass);
    }

    @Override
    public T parse(String input) throws PropertyParseException {
        try {
            return Enum.valueOf(enumClass, input);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new PropertyParseException(e, input);
        }
    }
}
