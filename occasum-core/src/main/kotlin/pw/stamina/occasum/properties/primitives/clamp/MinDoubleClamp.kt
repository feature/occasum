package pw.stamina.occasum.properties.primitives.clamp

internal class MinDoubleClamp(private val min: Double) : DoubleClamp {

    override fun clamp(value: Double): Double {
        return if (value < min) {
            min
        } else {
            value
        }
    }
}
