package pw.stamina.occasum.node

import pw.stamina.occasum.Named
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

internal class FolderPropertyNode(factory: PropertyNodeFactory,
                                  handle: PropertyHandle,
                                  parent: PropertyNode,
                                  name: String
) : AbstractParentedPropertyNode(factory, handle, parent) {

    override val name: String = Named.validateName(name)
    override val id: String = Named.createIdFromName(name)

    override val property: Property<*>? = null
}
