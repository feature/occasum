package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

interface PropertyRegistry {

    fun register(handle: PropertyHandle, property: Property<*>): PropertyNode

    fun registerAll(handle: PropertyHandle, properties: Iterable<Property<*>>)

    fun unregisterAll(handle: PropertyHandle)

    fun unregister(node: PropertyNode)

    fun unregister(handle: PropertyHandle, path: String): Boolean

    fun findRootNode(handle: PropertyHandle): PropertyNode?

    fun findOrCreateRootNode(handle: PropertyHandle): PropertyNode

    //TODO: Implement
    //PropertyNode findNodeOrCreateFolder(PropertyHandle handle, String path);

    fun findAllRootNodes(): Map<PropertyHandle, PropertyNode>

    companion object {

        fun standard(factory: PropertyNodeFactory): PropertyRegistry {
            return StandardPropertyRegistry(factory)
        }
    }
}
