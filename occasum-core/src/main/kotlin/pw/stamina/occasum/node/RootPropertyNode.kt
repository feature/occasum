package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

internal class RootPropertyNode(
        factory: PropertyNodeFactory,
        handle: PropertyHandle
) : AbstractPropertyNode(factory, handle) {

    override val name: String = handle.name

    override val id: String = handle.name

    override val property: Property<*>? = null

    override val parent: PropertyNode? = null
}
