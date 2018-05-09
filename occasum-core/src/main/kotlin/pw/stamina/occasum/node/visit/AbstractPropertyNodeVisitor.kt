package pw.stamina.occasum.node.visit

import pw.stamina.occasum.node.PropertyNode

abstract class AbstractPropertyNodeVisitor : PropertyNodeVisitor {

    override fun visitChildNode(childNode: PropertyNode): PropertyNodeVisitor? {
        return createChildVisitor(childNode)
    }

    protected abstract fun createChildVisitor(childNode: PropertyNode): PropertyNodeVisitor?
}
