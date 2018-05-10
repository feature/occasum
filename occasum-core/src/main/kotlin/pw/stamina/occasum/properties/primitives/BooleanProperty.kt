package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException

class BooleanProperty private constructor(
        name: String,
        private var value: Boolean)
    : AbstractProperty(name) {

    private val defaultValue = value

    override val isDefault: Boolean
        get() = value == defaultValue

    override val valueAsString: String
        get() = value.toString()

    override val defaultValueAsString: String
        get() = defaultValue.toString()

    fun set(value: Boolean) {
        this.value = value
    }

    fun get(): Boolean {
        return value
    }

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        set(input.toBoolean())
    }

    override fun reset() {
        value = defaultValue
    }

    companion object {

        fun from(name: String, value: Boolean): BooleanProperty {
            return BooleanProperty(name, value)
        }
    }
}
