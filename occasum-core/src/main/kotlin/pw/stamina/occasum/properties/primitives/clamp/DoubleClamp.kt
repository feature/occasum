package pw.stamina.occasum.properties.primitives.clamp

interface DoubleClamp {

    fun clamp(value: Double): Double

    companion object {

        fun min(min: Double): DoubleClamp {
            return MinDoubleClamp(min)
        }

        fun max(max: Double): DoubleClamp {
            return MaxDoubleClamp(max)
        }

        fun range(min: Double, max: Double): DoubleClamp {
            return RangeDoubleClamp(min, max)
        }

        fun none(): DoubleClamp {
            return NoClamp.INSTANCE
        }
    }
}
