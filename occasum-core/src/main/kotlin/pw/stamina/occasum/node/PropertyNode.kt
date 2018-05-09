package pw.stamina.occasum.node

import pw.stamina.occasum.Named
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.Property
import java.util.*

interface PropertyNode : Named {

    val handle: PropertyHandle

    /**
     * Returns the [property][Property] associated with this node. If the
     * [PropertyNode.hasProperty] method indicates that this node does
     * not hold a property, an [IllegalStateException] is thrown
     *
     * @return the [Property] this node is hold
     * @throws IllegalStateException if this method is invoked, but this
     * node is not holding any [Property].
     */
    val property: Property

    //TODO: Throw illegal state exception if hasParent returns false
    val parent: PropertyNode

    /**
     * Indicates if this node is holding a [property][Property].
     * The return value of this method should not change.
     *
     * @return <tt>true</tt> if this node holds a [Property]
     */
    fun hasProperty(): Boolean

    fun hasParent(): Boolean

    fun hasChildren(): Boolean

    fun findChildren(): List<PropertyNode>

    fun property(handle: PropertyHandle, property: Property): PropertyNode

    fun property(property: Property): PropertyNode

    fun folder(handle: PropertyHandle, name: String): PropertyNode

    fun folder(name: String): PropertyNode

    fun removeChild(node: PropertyNode)

    fun removeChild(id: String)

    /**
     * Searches the children for a node
     *
     * @param id the id of the node being searched for
     * @return the node case insensitively matching the
     * specified `id` wrapped in an [Optional],
     * or an [Optional.empty] Optional if no node
     * matched the specified id.
     */
    fun find(id: String): PropertyNode?

    /**
     * As part of the visitor pattern this method accepts a
     * [visitor][PropertyNodeVisitor]. The specified
     * visitor visits this node, and creates new individual
     * visitors for all children of this node.
     *
     * @param visitor the visitor used to visit this node
     */
    fun accept(visitor: PropertyNodeVisitor)
}
