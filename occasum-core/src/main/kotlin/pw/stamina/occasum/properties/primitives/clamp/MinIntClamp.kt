package pw.stamina.occasum.properties.primitives.clamp

internal class MinIntClamp(private val min: Int) : IntClamp {

    override fun clamp(value: Int): Int {
        return if (value < min) {
            min
        } else {
            value
        }
    }
}
