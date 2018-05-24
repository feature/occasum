package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.primitives.clamp.IntClamp
import pw.stamina.occasum.properties.traits.Incremental

class IntProperty (
        name: String,
        value: Int,
        private val clamp: IntClamp = IntClamp.none(),
        private val increaseValue: Int = DEFAULT_INCREASE_VALUE
) : AbstractProperty<Int>(name), Incremental {

    override var value: Int = clamp.clamp(value)
        set(value) {
            field = clamp.clamp(value)
        }

    override val default = value

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        val newValue = input.toIntOrNull()

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
        private const val DEFAULT_INCREASE_VALUE = 1
    }
}
