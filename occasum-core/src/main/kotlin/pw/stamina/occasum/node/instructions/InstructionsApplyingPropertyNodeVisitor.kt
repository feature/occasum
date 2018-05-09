package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import java.util.*

class InstructionsApplyingPropertyNodeVisitor(private val instructions: PropertyNodeInstructionList) : PropertyNodeVisitor {

    override fun visitNode(node: PropertyNode) {
        instructions.forEach { instruction -> instruction.apply(node) }
    }

    override fun visitChildNode(childNode: PropertyNode): PropertyNodeVisitor {
        val name = childNode.name
        return instructions.findNestedInstructions(name)
                .map { InstructionsApplyingPropertyNodeVisitor(it) }
    }
}
