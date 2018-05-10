package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.primitives.clamp.IntClamp

class IntPropertyBuilder internal constructor(private val name: String) {
    private var value = DEFAULT_VALUE
    private var increaseValue = DEFAULT_INCREASE_VALUE

    private var clamp = IntClamp.none()

    fun value(value: Int): IntPropertyBuilder {
        this.value = value
        return this
    }

    fun min(min: Int): IntPropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = IntClamp.min(min)
        return this
    }

    fun max(max: Int): IntPropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = IntClamp.max(max)
        return this
    }

    fun range(min: Int, max: Int): IntPropertyBuilder {
        requireNoClampHasBeenSet()
        this.clamp = IntClamp.range(min, max)
        return this
    }

    private fun requireNoClampHasBeenSet() {
        require(clamp === IntClamp.none()) { "a range has already been set for this builder" }
    }

    fun increase(increaseValue: Int): IntPropertyBuilder {
        this.increaseValue = increaseValue
        return this
    }

    fun build() = IntProperty(name, value, clamp, increaseValue)

    companion object {
        private const val DEFAULT_VALUE = 0
        private const val DEFAULT_INCREASE_VALUE = 1
    }
}
