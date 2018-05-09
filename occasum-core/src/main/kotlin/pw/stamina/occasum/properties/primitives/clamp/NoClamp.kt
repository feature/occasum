package pw.stamina.occasum.properties.primitives.clamp

internal enum class NoClamp : IntClamp, DoubleClamp {
    INSTANCE;

    override fun clamp(value: Int): Int {
        return value
    }

    override fun clamp(value: Double): Double {
        return value
    }
}
