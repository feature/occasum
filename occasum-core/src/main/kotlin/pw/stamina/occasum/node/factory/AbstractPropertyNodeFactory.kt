package pw.stamina.occasum.node.factory

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.inspect.PropertyNodeFactoryParameterInspector
import pw.stamina.occasum.properties.Property
import java.util.*

abstract class AbstractPropertyNodeFactory protected constructor() : PropertyNodeFactory {
    private val inspectors: MutableList<PropertyNodeFactoryParameterInspector> = ArrayList()

    override fun root(handle: PropertyHandle): PropertyNode {
        inspectors.forEach { inspector -> inspector.inspectRoot(handle) }
        return createRoot(handle)
    }

    override fun property(handle: PropertyHandle, parent: PropertyNode, property: Property): PropertyNode {
        inspectors.forEach { inspector -> inspector.inspectProperty(handle, parent, property) }
        return createProperty(handle, parent, property)
    }

    override fun folder(handle: PropertyHandle, parent: PropertyNode, name: String): PropertyNode {
        inspectors.forEach { inspector -> inspector.inspectFolder(handle, parent, name) }
        return createFolder(handle, parent, name)
    }

    override fun notifyRemoval(node: PropertyNode) {
        inspectors.forEach { inspector -> inspector.inspectNotifyRemoval(node) }
    }

    override fun addInspector(inspector: PropertyNodeFactoryParameterInspector) {
        inspectors.add(inspector)
    }

    protected abstract fun createRoot(handle: PropertyHandle): PropertyNode

    protected abstract fun createProperty(handle: PropertyHandle, parent: PropertyNode, property: Property): PropertyNode

    protected abstract fun createFolder(handle: PropertyHandle, parent: PropertyNode, name: String): PropertyNode
}
