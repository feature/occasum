package pw.stamina.occasum.properties.primitives.clamp

internal class RangeDoubleClamp(private val min: Double, private val max: Double) : DoubleClamp {

    override fun clamp(value: Double): Double {
        return if (value < min) {
            min
        } else if (value > max) {
            max
        } else {
            value
        }
    }
}
