package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp

class DoublePropertyBuilder internal constructor(private val name: String) {
    private var value: Double = 0.toDouble()
    private var increaseValue: Double = 0.toDouble()
    private var clamp: DoubleClamp? = null

    init {
        this.clamp = DoubleClamp.none()
        this.increaseValue = DEFAULT_INCREASE_VALUE
    }

    fun value(value: Double): DoublePropertyBuilder {
        this.value = value
        return this
    }

    fun min(min: Double): DoublePropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = DoubleClamp.min(min)
        return this
    }

    fun max(max: Double): DoublePropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = DoubleClamp.max(max)
        return this
    }

    fun range(min: Double, max: Double): DoublePropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = DoubleClamp.range(min, max)
        return this
    }

    private fun requireNoClampHasBeenSet() {
        if (clamp !== DoubleClamp.none()) {
            throw IllegalStateException("a range has already been set for this builder")
        }
    }

    fun increase(increaseValue: Double): DoublePropertyBuilder {
        this.increaseValue = increaseValue
        return this
    }

    fun build(): DoubleProperty {
        return DoubleProperty(name, value, clamp!!, increaseValue)
    }

    companion object {
        private val DEFAULT_INCREASE_VALUE = 0.1
    }
}
