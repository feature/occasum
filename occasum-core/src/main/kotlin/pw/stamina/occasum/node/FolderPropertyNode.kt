package pw.stamina.occasum.node

import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

internal class FolderPropertyNode(factory: PropertyNodeFactory,
                                  handle: PropertyHandle,
                                  parent: PropertyNode,
                                  name: String) : AbstractParentedPropertyNode(factory, handle, parent) {
    val name: String
    val id: String

    override val property: Property
        get() = throw IllegalStateException("folder nodes cannot have a property")

    init {

        this.name = Named.validateName(name)
        this.id = Named.createIdFromName(name)
    }

    override fun hasProperty(): Boolean {
        return false
    }
}
