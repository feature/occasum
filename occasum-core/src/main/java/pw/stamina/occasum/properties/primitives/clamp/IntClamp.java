package pw.stamina.occasum.properties.primitives.clamp;

public interface IntClamp {

    int clamp(int value);

    static IntClamp min(int min) {
        return new MinIntClamp(min);
    }

    static IntClamp max(int max) {
        return new MaxIntClamp(max);
    }

    static IntClamp range(int min, int max) {
        return new RangeIntClamp(min, max);
    }

    static IntClamp none() {
        return NoClamp.INSTANCE;
    }
}
