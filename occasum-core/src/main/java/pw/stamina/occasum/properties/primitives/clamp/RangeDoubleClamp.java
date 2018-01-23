package pw.stamina.occasum.properties.primitives.clamp;

final class RangeDoubleClamp implements DoubleClamp {
    private final double min, max;

    RangeDoubleClamp(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double clamp(double value) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
