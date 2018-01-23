package pw.stamina.occasum.properties.primitives.clamp;

final class MinDoubleClamp implements DoubleClamp {
    private final double min;

    MinDoubleClamp(double min) {
        this.min = min;
    }

    @Override
    public double clamp(double value) {
        if (value < min) {
            return min;
        } else {
            return value;
        }
    }
}
