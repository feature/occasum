package pw.stamina.occasum.properties.enums;

import java.util.Objects;

public abstract class AbstractEnumParsingService<T extends Enum<T>> implements EnumParsingService<T> {
    protected final Class<T> enumClass;
    private T[] enumConstants;

    protected AbstractEnumParsingService(Class<T> enumClass) {
        Objects.requireNonNull(enumClass, "enumClass");
        this.enumClass = enumClass;
    }

    @Override
    public String toString(T value) {
        return value.name();
    }

    @Override
    public T[] getEnumConstants() {
        if (enumConstants == null) {
            enumConstants = enumClass.getEnumConstants();
        }

        return enumConstants;
    }
}
