package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.primitives.clamp.DoubleClamp
import pw.stamina.occasum.properties.traits.Incremental

class DoubleProperty constructor(
        name: String,
        value: Double,
        private val clamp: DoubleClamp = DoubleClamp.none(),
        private val increaseValue: Double = DEFAULT_INCREASE_VALUE
) : AbstractProperty<Double>(name), Incremental {

    override var value = clamp.clamp(value)
        set(value) {
            require(value.isFinite()) { "value is not finite" }
            field = clamp.clamp(value)
        }

    override val default = value

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        val newValue = input.toDoubleOrNull()

        //TODO Error message
        newValue ?: throw PropertyParseException("", input)

        value = newValue
    }

    override fun increase() {
        value += increaseValue
    }

    override fun decrease() {
        value -= increaseValue
    }

    companion object {
        private const val DEFAULT_INCREASE_VALUE = 0.1
    }
}
