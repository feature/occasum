package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp;

public final class DoublePropertyBuilder {
    private static final double DEFAULT_INCREASE_VALUE = 0.1;

    private final String name;
    private double value;
    private double increaseValue;
    private DoubleClamp clamp;

    DoublePropertyBuilder(String name) {
        this.name = name;
        this.clamp = DoubleClamp.none();
        this.increaseValue = DEFAULT_INCREASE_VALUE;
    }

    public DoublePropertyBuilder value(double value) {
        this.value = value;
        return this;
    }

    public DoublePropertyBuilder min(double min) {
        requireNoClampHasBeenSet();
        this.clamp = DoubleClamp.min(min);
        return this;
    }

    public DoublePropertyBuilder max(double max) {
        requireNoClampHasBeenSet();
        this.clamp = DoubleClamp.max(max);
        return this;
    }

    public DoublePropertyBuilder range(double min, double max) {
        requireNoClampHasBeenSet();
        this.clamp = DoubleClamp.range(min, max);
        return this;
    }

    private void requireNoClampHasBeenSet() {
        if (clamp != DoubleClamp.none()) {
            throw new IllegalStateException("a range has already been set for this builder");
        }
    }

    public DoublePropertyBuilder increase(double increaseValue) {
        this.increaseValue = increaseValue;
        return this;
    }

    public DoubleProperty build() {
        return new DoubleProperty(name, value, clamp, increaseValue);
    }
}
