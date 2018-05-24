package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

internal class SimplePropertyNode(
        factory: PropertyNodeFactory,
        handle: PropertyHandle,
        parent: PropertyNode,
        override val property: Property<*>
) : AbstractParentedPropertyNode(factory, handle, parent) {

    override val name: String = property.name

    override val id: String = property.id
}
