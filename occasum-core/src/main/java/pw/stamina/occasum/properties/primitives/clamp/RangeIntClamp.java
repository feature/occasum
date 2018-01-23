package pw.stamina.occasum.properties.primitives.clamp;

final class RangeIntClamp implements IntClamp {
    private final int min, max;

    RangeIntClamp(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int clamp(int value) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
