package pw.stamina.occasum.properties.primitives.clamp

interface IntClamp {

    fun clamp(value: Int): Int

    companion object {

        fun min(min: Int): IntClamp {
            return MinIntClamp(min)
        }

        fun max(max: Int): IntClamp {
            return MaxIntClamp(max)
        }

        fun range(min: Int, max: Int): IntClamp {
            return RangeIntClamp(min, max)
        }

        fun none(): IntClamp {
            return NoClamp.INSTANCE
        }
    }
}
