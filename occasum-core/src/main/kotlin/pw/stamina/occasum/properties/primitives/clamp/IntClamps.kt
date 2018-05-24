package pw.stamina.occasum.properties.primitives.clamp

internal class RangeIntClamp(private val min: Int, private val max: Int) : IntClamp {

    override fun clamp(value: Int) = when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

internal class MinIntClamp(private val min: Int) : IntClamp {

    override fun clamp(value: Int) = Math.max(min, value)
}

internal class MaxIntClamp(private val max: Int) : IntClamp {

    override fun clamp(value: Int) = Math.min(max, value)
}
