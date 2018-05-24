package pw.stamina.occasum.properties

import pw.stamina.occasum.AbstractNamed

abstract class AbstractProperty<T> protected constructor(name: String) : AbstractNamed(name), Property<T> {

    override fun reset() {
        value = default
    }

    override val isDefault: Boolean
        get() = value == default

    override val valueAsString: String
        get() = valueToString(value)

    override val defaultValueAsString: String
        get() = valueToString(default)

    protected open fun valueToString(value: T) = value.toString()
}
