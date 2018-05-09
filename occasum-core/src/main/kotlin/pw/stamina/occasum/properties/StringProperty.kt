package pw.stamina.occasum.properties

class StringProperty protected constructor(name: String, value: String) : ParameterizedProperty<String>(name, value) {

    override fun set(value: String) {

        // TODO: Process String

        super.set(value)
    }

    override fun parseAndSet(input: String) {
        set(input)
    }
}
