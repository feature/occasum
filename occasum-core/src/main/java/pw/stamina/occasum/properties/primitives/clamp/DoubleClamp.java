package pw.stamina.occasum.properties.primitives.clamp;

public interface DoubleClamp {

    double clamp(double value);

    static DoubleClamp min(double min) {
        return new MinDoubleClamp(min);
    }

    static DoubleClamp max(double max) {
        return new MaxDoubleClamp(max);
    }

    static DoubleClamp range(double min, double max) {
        return new RangeDoubleClamp(min, max);
    }
    
    static DoubleClamp none() {
        return NoClamp.INSTANCE;
    }
}
