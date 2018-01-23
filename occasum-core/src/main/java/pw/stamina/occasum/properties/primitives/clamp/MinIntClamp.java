package pw.stamina.occasum.properties.primitives.clamp;

final class MinIntClamp implements IntClamp {
    private final int min;

    MinIntClamp(int min) {
        this.min = min;
    }

    @Override
    public int clamp(int value) {
        if (value < min) {
            return min;
        } else {
            return value;
        }
    }
}
