package pw.stamina.occasum.properties.primitives.clamp;

final class MaxDoubleClamp implements DoubleClamp {
    private final double max;

    MaxDoubleClamp(double max) {
        this.max = max;
    }

    @Override
    public double clamp(double value) {
        if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
