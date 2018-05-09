package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.node.visit.PropertyRegisterCleanupPropertyNodeVisitor
import pw.stamina.occasum.properties.Property
import java.util.*
import java.util.function.Consumer
import javax.inject.Inject

class StandardPropertyRegistry @Inject
internal constructor(private val factory: PropertyNodeFactory) : PropertyRegistry {
    private val properties: MutableMap<PropertyHandle, PropertyNode>

    init {
        this.properties = IdentityHashMap<PropertyHandle, PropertyNode>()
    }

    override fun register(handle: PropertyHandle, property: Property): PropertyNode {
        val root = findOrCreateRootNode(handle)
        return root.property(property)
    }

    override fun registerAll(handle: PropertyHandle, properties: Iterable<Property>) {
        val root = findOrCreateRootNode(handle)
        properties.forEach(Consumer<Property> { root.property(it) })
    }

    //TODO: Make absolutely sure this works as intended
    //TODO: Walk ALL nodes, to make sure everything is unregistered.
    override fun unregisterAll(handle: PropertyHandle) {
        val removed = properties.remove(handle)

        if (removed !== null) {
            factory.notifyRemoval(removed)
        }

        val nodeRemovingVisitor = PropertyRegisterCleanupPropertyNodeVisitor(handle, { factory.notifyRemoval(it) })

        properties.values.forEach { node -> node.accept(nodeRemovingVisitor) }
    }

    override fun unregister(node: PropertyNode) {
        if (node.hasParent()) {
            val parent = node.parent
            parent.removeChild(node)
        } else {
            //TODO: Why is this illegal?
            //TODO: Property exception message
            throw IllegalStateException()
        }
    }

    override fun unregister(handle: PropertyHandle, path: String): Boolean {
        //TODO: Implement
        return false
    }

    override fun findRootNode(handle: PropertyHandle): PropertyNode? {
        return properties[handle]
    }

    override fun findOrCreateRootNode(handle: PropertyHandle): PropertyNode {
        return properties.computeIfAbsent(handle, factory::root)
    }

    override fun findAllRootNodes(): Map<PropertyHandle, PropertyNode> {
        return Collections.unmodifiableMap(properties)
    }
}
