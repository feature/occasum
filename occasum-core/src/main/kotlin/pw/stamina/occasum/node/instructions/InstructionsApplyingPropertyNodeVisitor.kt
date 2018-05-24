package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.visit.PropertyNodeVisitor

class InstructionsApplyingPropertyNodeVisitor(private val instructions: PropertyNodeInstructionList) : PropertyNodeVisitor {

    override fun visitNode(node: PropertyNode) {
        instructions.forEach { instruction -> instruction.apply(node) }
    }

    override fun visitChildNode(childNode: PropertyNode): PropertyNodeVisitor? {
        val name = childNode.name

        //TODO
        return instructions.findNestedInstructions(name)?.let { InstructionsApplyingPropertyNodeVisitor(it) }
    }
}
