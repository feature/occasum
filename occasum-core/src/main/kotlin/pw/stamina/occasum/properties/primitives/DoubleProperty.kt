package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp
import pw.stamina.occasum.properties.traits.Incremental

class DoubleProperty internal constructor(name: String, value: Double, private val clamp: DoubleClamp, private val increaseValue: Double) : AbstractProperty(name), Incremental {
    private var value: Double = 0.toDouble()
    private val defaultValue: Double

    override val isDefault: Boolean
        get() = value == defaultValue

    override val valueAsString: String
        get() = java.lang.Double.toString(value)

    override val defaultValueAsString: String
        get() = java.lang.Double.toString(defaultValue)

    init {

        val clampedValue = clamp.clamp(value)

        this.value = clampedValue
        this.defaultValue = clampedValue
    }

    @Throws(IllegalArgumentException::class)
    fun set(value: Double) {
        if (!java.lang.Double.isFinite(value)) {
            throw IllegalArgumentException("value is not finite")
        }

        this.value = clamp.clamp(value)
    }

    fun get(): Double {
        return value
    }

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        try {
            val newValue = java.lang.Double.parseDouble(input)
            set(newValue)
        } catch (e: NullPointerException) {
            throw PropertyParseException(e, input)
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

        fun from(name: String, value: Double): DoubleProperty {
            return builder(name).value(value).build()
        }

        fun clamped(name: String, value: Double, min: Double, max: Double): DoubleProperty {
            return builder(name).value(value).range(min, max).build()
        }

        fun builder(name: String): DoublePropertyBuilder {
            return DoublePropertyBuilder(name)
        }
    }
}