package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException

class BooleanProperty private constructor(name: String, private var value: Boolean) : AbstractProperty(name) {
    private val defaultValue: Boolean

    override val isDefault: Boolean
        get() = value == defaultValue

    override val valueAsString: String
        get() = java.lang.Boolean.toString(value)

    override val defaultValueAsString: String
        get() = java.lang.Boolean.toString(defaultValue)

    init {
        this.defaultValue = value
    }

    fun set(value: Boolean) {
        this.value = value
    }

    fun get(): Boolean {
        return value
    }

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String?) {
        if (input == null) {
            throw PropertyParseException.nullInput()
        }

        set(java.lang.Boolean.parseBoolean(input))
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
