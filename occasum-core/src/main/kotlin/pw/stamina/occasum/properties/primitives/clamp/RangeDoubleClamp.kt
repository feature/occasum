package pw.stamina.occasum.properties.primitives.clamp

internal class RangeDoubleClamp(private val min: Double, private val max: Double) : DoubleClamp {

    override fun clamp(value: Double): Double {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }
}
