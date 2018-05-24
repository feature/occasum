package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.properties.Property

internal class PropertyPropertyNodeInstruction(
        private val handle: PropertyHandle?,
        private val property: Property<*>
) : PropertyNodeInstruction {

    override fun apply(node: PropertyNode) {
        if (handle != null) {
            node.property(handle, property)
        } else {
            node.property(property)
        }
    }
}
