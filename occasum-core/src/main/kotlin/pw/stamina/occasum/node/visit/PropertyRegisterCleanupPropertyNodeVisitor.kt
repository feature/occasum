package pw.stamina.occasum.node.visit

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import java.util.function.Consumer

class PropertyRegisterCleanupPropertyNodeVisitor(private val handle: PropertyHandle,
                                                 private val notifier: Consumer<PropertyNode>) : PropertyNodeVisitor {

    override fun visitNode(node: PropertyNode) {
        if (node.handle === handle) {
            assert(node.hasParent())
            node.parent.removeChild(node)
            notifier.accept(node)
        }
    }
}
