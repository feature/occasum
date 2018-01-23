package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.primitives.clamp.IntClamp;

public final class IntPropertyBuilder {
    private static final int DEFAULT_INCREASE_VALUE = 1;

    private final String name;
    private int value;
    private IntClamp clamp;
    private int increaseValue;

    IntPropertyBuilder(String name) {
        this.name = name;
        this.clamp = IntClamp.none();
        this.increaseValue = DEFAULT_INCREASE_VALUE;
    }

    public IntPropertyBuilder value(int value) {
        this.value = value;
        return this;
    }

    public IntPropertyBuilder min(int min) {
        requireNoClampHasBeenSet();
        this.clamp = IntClamp.min(min);
        return this;
    }

    public IntPropertyBuilder max(int max) {
        requireNoClampHasBeenSet();
        this.clamp = IntClamp.max(max);
        return this;
    }

    public IntPropertyBuilder range(int min, int max) {
        requireNoClampHasBeenSet();
        this.clamp = IntClamp.range(min, max);
        return this;
    }

    private void requireNoClampHasBeenSet() {
        if (clamp != IntClamp.none()) {
            throw new IllegalStateException("a clamp has already been set for this builder");
        }
    }

    public IntPropertyBuilder increase(int increaseValue) {
        this.increaseValue = increaseValue;
        return this;
    }

    public IntProperty build() {
        return new IntProperty(name, value, clamp, increaseValue);
    }
}
