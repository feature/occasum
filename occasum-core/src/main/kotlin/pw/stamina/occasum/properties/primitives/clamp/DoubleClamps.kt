package pw.stamina.occasum.properties.primitives.clamp

internal class RangeDoubleClamp(private val min: Double, private val max: Double) : DoubleClamp {

    override fun clamp(value: Double) = when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

internal class MinDoubleClamp(private val min: Double) : DoubleClamp {

    override fun clamp(value: Double) = Math.max(min, value)
}

internal class MaxDoubleClamp(private val max: Double) : DoubleClamp {

    override fun clamp(value: Double) = Math.min(max, value)
}
