package pw.stamina.occasum.properties.primitives.clamp;

final class MaxIntClamp implements IntClamp {
    private final int max;

    MaxIntClamp(int max) {
        this.max = max;
    }

    @Override
    public int clamp(int value) {
        if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
