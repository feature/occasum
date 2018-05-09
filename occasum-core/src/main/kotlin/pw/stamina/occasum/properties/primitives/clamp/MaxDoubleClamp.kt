package pw.stamina.occasum.properties.primitives.clamp

internal class MaxDoubleClamp(private val max: Double) : DoubleClamp {

    override fun clamp(value: Double): Double {
        return if (value > max) {
            max
        } else {
            value
        }
    }
}
