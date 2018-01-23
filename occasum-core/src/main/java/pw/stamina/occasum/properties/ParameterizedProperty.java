package pw.stamina.occasum.properties;

import java.util.Objects;

public abstract class ParameterizedProperty<T> extends AbstractProperty {
    private T value;
    protected final T defaultValue;

    protected ParameterizedProperty(String name, T value) {
        super(name);

        Objects.requireNonNull(value, "value");
        this.value = value;
        this.defaultValue = value;
    }

    public final T get() {
        return value;
    }

    public void set(T value) {
        Objects.requireNonNull(value, "value");
        this.value = value;
    }

    @Override
    public final boolean isDefault() {
        return value.equals(defaultValue);
    }

    public final T getDefault() {
        return defaultValue;
    }

    @Override
    public final void reset() {
        set(defaultValue);
    }

    @Override
    public final String getValueAsString() {
        return valueToString(value);
    }

    @Override
    public final String getDefaultValueAsString() {
        return valueToString(defaultValue);
    }

    protected String valueToString(T value) {
        return String.valueOf(value);
    }
}
