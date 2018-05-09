package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.Property

import java.util.*
import java.util.function.Predicate

abstract class AbstractPropertyNode protected constructor(private val factory: PropertyNodeFactory,
                                                          override val handle: PropertyHandle) : PropertyNode {
    private var children: MutableList<PropertyNode>? = null

    override fun hasChildren(): Boolean {
        return children != null
    }

    override fun findChildren(): List<PropertyNode> {
        return if (hasChildren()) {
            Collections.unmodifiableList(children!!)
        } else emptyList()

    }

    override fun property(handle: PropertyHandle, property: Property): PropertyNode {
        val propertyId = property.id

        if (find(propertyId).isPresent) {
            throw IllegalArgumentException(
                    "another node with the same id has " +
                            "already been registered. Id: " + propertyId)
        }

        return add(factory.property(handle, this, property))
    }

    override fun property(property: Property): PropertyNode {
        return property(handle, property)
    }

    override fun folder(handle: PropertyHandle, name: String): PropertyNode {
        return find(name).orElseGet { add(factory.folder(handle, this, name)) }
    }

    override fun folder(name: String): PropertyNode {
        return folder(handle, name)
    }

    private fun add(node: PropertyNode): PropertyNode {
        createChildrenIfAbsent()

        children!!.add(node)
        return node
    }

    private fun createChildrenIfAbsent() {
        if (!hasChildren()) {
            children = ArrayList()
        }
    }

    override fun removeChild(node: PropertyNode) {
        if (hasChildren() && children!!.remove(node)) {
            factory.notifyRemoval(node)
        }
    }

    override fun removeChild(id: String) {
        find(id).ifPresent { this.removeChild(it) }
    }

    override fun find(id: String): PropertyNode? {
        return if (!hasChildren()) {
            null
        } else children!!.stream()
                .filter(doesIdMatchIgnoreCase(id))
                .findAny()

    }

    private fun doesIdMatchIgnoreCase(id: String): Predicate<PropertyNode> {
        return Predicate { node -> node.id.equals(id, true) }
    }

    override fun accept(visitor: PropertyNodeVisitor) {
        visitor.visitNode(this)

        if (hasChildren()) {
            visitChildren(visitor)
        }
    }

    private fun visitChildren(visitor: PropertyNodeVisitor) {
        children!!.forEach{ childNode -> visitor.visitChildNode(childNode)
                .ifPresent { childNode.accept(it) } }
    }

    override fun toString(): String {
        return "PropertyNode{id=$id}"
    }
}
