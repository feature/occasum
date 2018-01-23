package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.AbstractProperty;
import pw.stamina.occasum.properties.PropertyParseException;

public final class BooleanProperty extends AbstractProperty {
    private boolean value;
    private final boolean defaultValue;

    private BooleanProperty(String name, boolean value) {
        super(name);

        this.value = value;
        this.defaultValue = value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        if (input == null) {
            throw PropertyParseException.nullInput();
        }

        set(Boolean.parseBoolean(input));
    }

    @Override
    public boolean isDefault() {
        return value == defaultValue;
    }

    @Override
    public void reset() {
        value = defaultValue;
    }

    @Override
    public String getValueAsString() {
        return Boolean.toString(value);
    }

    @Override
    public String getDefaultValueAsString() {
        return Boolean.toString(defaultValue);
    }

    public static BooleanProperty from(String name, boolean value) {
        return new BooleanProperty(name, value);
    }
}
