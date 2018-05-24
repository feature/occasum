package pw.stamina.occasum.node.factory.inspect

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.Property
import java.util.*

internal class DuplicatePropertyDetectingPropertyNodeFactoryParameterInspector : PropertyNodeFactoryParameterInspector {

    private val registeredProperties: MutableSet<Property<*>> = createRegisteredPropertiesSet()

    override fun inspectProperty(handle: PropertyHandle, parent: PropertyNode, property: Property<*>) {
        if (registeredProperties.contains(property)) {
            //TODO: Better error message
            throw IllegalArgumentException("that property has already been registered to another node")
        } else {
            registeredProperties.add(property)
        }
    }

    override fun inspectNotifyRemoval(node: PropertyNode) {
        val visitor = NotifyRemovalCleanupPropertyNodeVisitor()
        node.accept(visitor)
    }

    private fun createRegisteredPropertiesSet(): MutableSet<Property<*>> {
        return Collections.newSetFromMap(IdentityHashMap())
    }

    private inner class NotifyRemovalCleanupPropertyNodeVisitor : PropertyNodeVisitor {

        override fun visitNode(node: PropertyNode) {
            val property = node.property ?: return
            registeredProperties.remove(property)
        }
    }
}
