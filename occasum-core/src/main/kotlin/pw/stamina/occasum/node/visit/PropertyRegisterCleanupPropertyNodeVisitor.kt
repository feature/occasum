package pw.stamina.occasum.node.visit

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode

class PropertyRegisterCleanupPropertyNodeVisitor(
        private val handle: PropertyHandle,
        private val notifier: (PropertyNode) -> Unit)
    : PropertyNodeVisitor {

    override fun visitNode(node: PropertyNode) {
        if (node.handle === handle) {
            val parent = node.parent ?: return
            parent.removeChild(node)
            notifier(node)
        }
    }
}
