package pw.stamina.occasum.properties.primitives.clamp

interface DoubleClamp {

    fun clamp(value: Double): Double

    companion object {

        fun min(min: Double): DoubleClamp = MinDoubleClamp(min)

        fun max(max: Double): DoubleClamp = MaxDoubleClamp(max)

        fun range(min: Double, max: Double): DoubleClamp = RangeDoubleClamp(min, max)

        fun none(): DoubleClamp = NoClamp
    }
}
