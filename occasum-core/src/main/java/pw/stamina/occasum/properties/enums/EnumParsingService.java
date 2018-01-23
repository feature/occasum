package pw.stamina.occasum.properties.enums;

import pw.stamina.occasum.properties.PropertyParseException;

public interface EnumParsingService<T extends Enum<T>> {

    T parse(String input) throws PropertyParseException;

    String toString(T value);

    T[] getEnumConstants();

    static <T extends Enum<T>> EnumParsingService<T> standard(Class<T> enumClass) {
        return new StandardEnumParsingService<>(enumClass);
    }

    static <T extends Enum<T>> EnumParsingService<T> caseInsensitive(Class<T> enumClass) {
        return new CaseInsensitiveEnumParsingService<>(enumClass);
    }
}
