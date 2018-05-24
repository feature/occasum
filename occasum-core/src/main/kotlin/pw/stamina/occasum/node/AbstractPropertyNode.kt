package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.Property

abstract class AbstractPropertyNode protected constructor(
        private val factory: PropertyNodeFactory,
        override val handle: PropertyHandle
) : PropertyNode {

    override val children: MutableList<PropertyNode> by lazy(LazyThreadSafetyMode.NONE) {
        ArrayList<PropertyNode>()
    }

    override fun property(handle: PropertyHandle, property: Property<*>): PropertyNode {
        val propertyId = property.id

        require(this[propertyId] == null) {
            "another node with the same id ('$propertyId') has already been registered"
        }

        return add(factory.property(handle, this, property))
    }

    override fun property(property: Property<*>): PropertyNode {
        return property(handle, property)
    }

    override fun folder(handle: PropertyHandle, name: String): PropertyNode {
        return this[name] ?: add(factory.folder(handle, this, name))
    }

    override fun folder(name: String): PropertyNode {
        return folder(handle, name)
    }

    private fun add(node: PropertyNode): PropertyNode {
        children.add(node)
        return node
    }

    override fun removeChild(node: PropertyNode) {
        if (children.remove(node)) {
            factory.notifyRemoval(node)
        }
    }

    override fun removeChild(id: String) {
        val child = this[id] ?: return
        removeChild(child)
    }

    override operator fun get(id: String): PropertyNode? {
        return children.find(doesIdMatchIgnoreCase(id))
    }

    private fun doesIdMatchIgnoreCase(id: String): (PropertyNode) -> Boolean {
        return { node -> node.id.equals(id, true) }
    }

    override fun accept(visitor: PropertyNodeVisitor) {
        visitor.visitNode(this)
        visitChildren(visitor)
    }

    private fun visitChildren(visitor: PropertyNodeVisitor) {
        children.forEach { childNode ->
            visitor.visitChildNode(childNode)?.let { childNode.accept(it) }
        }
    }

    override fun toString(): String {
        return "PropertyNode{id=$id}"
    }
}
