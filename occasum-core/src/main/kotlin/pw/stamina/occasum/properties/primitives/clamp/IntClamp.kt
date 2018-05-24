package pw.stamina.occasum.properties.primitives.clamp

interface IntClamp {

    fun clamp(value: Int): Int

    companion object {

        fun min(min: Int): IntClamp = MinIntClamp(min)

        fun max(max: Int): IntClamp = MaxIntClamp(max)

        fun range(min: Int, max: Int): IntClamp = RangeIntClamp(min, max)

        fun none(): IntClamp = NoClamp
    }
}
