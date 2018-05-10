package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp

class DoublePropertyBuilder internal constructor(private val name: String) {
    private var value = DEFAULT_VALUE
    private var increaseValue = DEFAULT_INCREASE_VALUE

    private var clamp = DoubleClamp.none()

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
        require(clamp === DoubleClamp.none()) { "a range has already been set for this builder" }
    }

    fun increase(increaseValue: Double): DoublePropertyBuilder {
        this.increaseValue = increaseValue
        return this
    }

    fun build() = DoubleProperty(name, value, clamp, increaseValue)

    companion object {
        private const val DEFAULT_VALUE = 0.0
        private const val DEFAULT_INCREASE_VALUE = 0.1
    }
}
