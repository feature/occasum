package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

import java.util.Objects

internal class SimplePropertyNode(factory: PropertyNodeFactory,
                                  handle: PropertyHandle,
                                  parent: PropertyNode,
                                  override val property: Property) : AbstractParentedPropertyNode(factory, handle, parent) {

    val name: String
        get() = property.name

    val id: String
        get() = property.id

    override fun hasProperty(): Boolean {
        return true
    }
}
