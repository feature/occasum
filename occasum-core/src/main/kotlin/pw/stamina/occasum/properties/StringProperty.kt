package pw.stamina.occasum.properties

class StringProperty (
        name: String,
        override var value: String
) : AbstractProperty<String>(name) {

    override val default = value

    override fun parseAndSet(input: String) {
        value = input
    }
}
