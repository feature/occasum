package pw.stamina.occasum.properties.primitives.clamp

internal class MaxIntClamp(private val max: Int) : IntClamp {

    override fun clamp(value: Int): Int {
        return if (value > max) {
            max
        } else {
            value
        }
    }
}
