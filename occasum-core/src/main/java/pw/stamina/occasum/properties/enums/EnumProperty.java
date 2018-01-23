package pw.stamina.occasum.properties.enums;

import pw.stamina.occasum.properties.ParameterizedProperty;
import pw.stamina.occasum.properties.PropertyParseException;
import pw.stamina.occasum.properties.traits.Cyclable;

import java.util.Objects;
import java.util.function.Function;

public final class EnumProperty<T extends Enum<T>>
        extends ParameterizedProperty<T> implements Cyclable {

    private final EnumParsingService<T> parsingService;

    EnumProperty(String name, T value, EnumParsingService<T> parsingService) {
        super(name, value);
        this.parsingService = parsingService;
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        set(parsingService.parse(input));
    }

    @Override
    protected String valueToString(T value) {
        return parsingService.toString(value);
    }

    @Override
    public void cycleForward() {
        //TODO
    }

    @Override
    public void cycleBackward() {
        //TODO
    }

    private int getValueOrdinal() {
        return get().ordinal();
    }

    public static <T extends Enum<T>> EnumProperty<T> from(String name, T value) {
        return from(name, value, EnumParsingService::standard);
    }

    public static <T extends Enum<T>> EnumProperty<T> from(
            String name, T value, Function<Class<T>, EnumParsingService<T>> parsingServiceCreator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(parsingServiceCreator, "parsingServiceCreator");

        Class<T> enumClass = value.getDeclaringClass();
        EnumParsingService<T> parsingService = parsingServiceCreator.apply(enumClass);

        return new EnumProperty<>(name, value, parsingService);
    }
}
