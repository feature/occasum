package pw.stamina.occasum.properties.primitives.clamp;

enum NoClamp implements IntClamp, DoubleClamp {
    INSTANCE;

    @Override
    public int clamp(int value) {
        return value;
    }

    @Override
    public double clamp(double value) {
        return value;
    }
}
