package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.AbstractProperty;
import pw.stamina.occasum.properties.primitives.clamp.IntClamp;
import pw.stamina.occasum.properties.traits.Incremental;
import pw.stamina.occasum.properties.PropertyParseException;

public final class IntProperty extends AbstractProperty implements Incremental {
    private int value;
    private final int defaultValue;
    private final IntClamp clamp;
    private final int increaseValue;

    IntProperty(String name, int value, IntClamp clamp, int increaseValue) {
        super(name);

        int clampedValue = clamp.clamp(value);

        this.value = clampedValue;
        this.defaultValue = clampedValue;

        this.clamp = clamp;
        this.increaseValue = increaseValue;
    }

    public void set(int value) {
        this.value = clamp.clamp(value);
    }

    public int get() {
        return value;
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        try {
            int newValue = Integer.parseInt(input);
            set(newValue);
        } catch (NumberFormatException e) {
            throw new PropertyParseException(e, input);
        }
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
        return Integer.toString(value);
    }

    @Override
    public String getDefaultValueAsString() {
        return Integer.toString(defaultValue);
    }

    @Override
    public void increase() {
        set(value + increaseValue);
    }

    @Override
    public void decrease() {
        set(value - increaseValue);
    }

    public static IntProperty from(String name, int value) {
        return builder(name).value(value).build();
    }

    public static IntProperty clamped(String name, int value, int min, int max) {
        return builder(name).value(value).range(min, max).build();
    }

    public static IntPropertyBuilder builder(String name) {
        return new IntPropertyBuilder(name);
    }
}
