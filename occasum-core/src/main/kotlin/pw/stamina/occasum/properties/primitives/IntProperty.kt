package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.primitives.clamp.IntClamp
import pw.stamina.occasum.properties.traits.Incremental
import pw.stamina.occasum.properties.PropertyParseException

class IntProperty internal constructor(name: String, value: Int, private val clamp: IntClamp, private val increaseValue: Int) : AbstractProperty(name), Incremental {
    private var value: Int = 0
    private val defaultValue: Int

    override val isDefault: Boolean
        get() = value == defaultValue

    override val valueAsString: String
        get() = Integer.toString(value)

    override val defaultValueAsString: String
        get() = Integer.toString(defaultValue)

    init {

        val clampedValue = clamp.clamp(value)

        this.value = clampedValue
        this.defaultValue = clampedValue
    }

    fun set(value: Int) {
        this.value = clamp.clamp(value)
    }

    fun get(): Int {
        return value
    }

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        try {
            val newValue = Integer.parseInt(input)
            set(newValue)
        } catch (e: NumberFormatException) {
            throw PropertyParseException(e, input)
        }

    }

    override fun reset() {
        value = defaultValue
    }

    override fun increase() {
        set(value + increaseValue)
    }

    override fun decrease() {
        set(value - increaseValue)
    }

    companion object {

        fun from(name: String, value: Int): IntProperty {
            return builder(name).value(value).build()
        }

        fun clamped(name: String, value: Int, min: Int, max: Int): IntProperty {
            return builder(name).value(value).range(min, max).build()
        }

        fun builder(name: String): IntPropertyBuilder {
            return IntPropertyBuilder(name)
        }
    }
}
