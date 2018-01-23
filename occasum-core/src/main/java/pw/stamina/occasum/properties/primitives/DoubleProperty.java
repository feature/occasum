package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.AbstractProperty;
import pw.stamina.occasum.properties.PropertyParseException;
import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp;
import pw.stamina.occasum.properties.traits.Incremental;

public final class DoubleProperty extends AbstractProperty implements Incremental {
    private double value;
    private final double defaultValue;
    private final DoubleClamp clamp;
    private final double increaseValue;

    DoubleProperty(String name, double value, DoubleClamp clamp, double increaseValue) {
        super(name);

        double clampedValue = clamp.clamp(value);

        this.value = clampedValue;
        this.defaultValue = clampedValue;

        this.clamp = clamp;
        this.increaseValue = increaseValue;
    }

    public void set(double value) throws IllegalArgumentException {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("value is not finite");
        }

        this.value = clamp.clamp(value);
    }

    public double get() {
        return value;
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        try {
            double newValue = Double.parseDouble(input);
            set(newValue);
        } catch (NullPointerException | NumberFormatException e) {
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
        return Double.toString(value);
    }

    @Override
    public String getDefaultValueAsString() {
        return Double.toString(defaultValue);
    }

    @Override
    public void increase() {
        set(value + increaseValue);
    }

    @Override
    public void decrease() {
        set(value - increaseValue);
    }

    public static DoubleProperty from(String name, double value) {
        return builder(name).value(value).build();
    }

    public static DoubleProperty clamped(String name, double value, double min, double max) {
        return builder(name).value(value).range(min, max).build();
    }

    public static DoublePropertyBuilder builder(String name) {
        return new DoublePropertyBuilder(name);
    }
}
