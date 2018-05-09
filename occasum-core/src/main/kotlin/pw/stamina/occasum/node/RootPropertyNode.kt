package pw.stamina.occasum.node

import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

internal class RootPropertyNode(factory: PropertyNodeFactory, handle: PropertyHandle) : AbstractPropertyNode(factory, handle) {

    val name: String
        get() = handle.getName()

    val id: String
        get() = handle.getId()

    override val property: Property
        get() = throw IllegalStateException("root nodes cannot have properties")

    override val parent: PropertyNode
        get() = throw IllegalStateException("root nodes cannot have parents")

    override fun hasProperty(): Boolean {
        return false
    }

    override fun hasParent(): Boolean {
        return false
    }
}
