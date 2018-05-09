package pw.stamina.occasum.node.visit

import pw.stamina.occasum.node.PropertyNode

interface PropertyNodeVisitor {

    fun visitNode(node: PropertyNode)

    fun visitChildNode(childNode: PropertyNode): PropertyNodeVisitor? {
        return this
    }
}
