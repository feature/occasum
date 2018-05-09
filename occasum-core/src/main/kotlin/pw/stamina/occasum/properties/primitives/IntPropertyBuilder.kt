package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.primitives.clamp.IntClamp

class IntPropertyBuilder internal constructor(private val name: String) {
    private var value: Int = 0
    private var clamp: IntClamp? = null
    private var increaseValue: Int = 0

    init {
        this.clamp = IntClamp.none()
        this.increaseValue = DEFAULT_INCREASE_VALUE
    }

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
        if (clamp !== IntClamp.none()) {
            throw IllegalStateException("a clamp has already been set for this builder")
        }
    }

    fun increase(increaseValue: Int): IntPropertyBuilder {
        this.increaseValue = increaseValue
        return this
    }

    fun build(): IntProperty {
        return IntProperty(name, value, clamp!!, increaseValue)
    }

    companion object {
        private val DEFAULT_INCREASE_VALUE = 1
    }
}
