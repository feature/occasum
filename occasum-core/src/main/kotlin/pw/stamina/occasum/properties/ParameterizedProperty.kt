package pw.stamina.occasum.properties

abstract class ParameterizedProperty<T> protected constructor(name: String, private var value: T) : AbstractProperty(name) {
    val default: T = value

    override val isDefault: Boolean
        get() = value == default

    override val valueAsString: String
        get() = valueToString(value)

    override val defaultValueAsString: String
        get() = valueToString(default)

    fun get(): T? {
        return value
    }

    open fun set(value: T) {
        this.value = value
    }

    override fun reset() {
        set(default)
    }

    protected open fun valueToString(value: T): String {
        return value.toString()
    }
}
