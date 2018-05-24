package pw.stamina.occasum.properties.primitives.clamp

internal object NoClamp : IntClamp, DoubleClamp {
    override fun clamp(value: Int) = value
    override fun clamp(value: Double) = value
}
