package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.AbstractProperty

class BooleanProperty constructor(
        name: String,
        override var value: Boolean
) : AbstractProperty<Boolean>(name) {

    override val default = value

    override fun parseAndSet(input: String) {
        value = input.toBoolean()
    }
}
