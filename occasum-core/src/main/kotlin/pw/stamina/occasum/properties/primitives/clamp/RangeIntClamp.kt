package pw.stamina.occasum.properties.primitives.clamp

internal class RangeIntClamp(private val min: Int, private val max: Int) : IntClamp {

    override fun clamp(value: Int): Int {
        return if (value < min) {
            min
        } else if (value > max) {
            max
        } else {
            value
        }
    }
}
