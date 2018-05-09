package pw.stamina.occasum.scan.result

import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.instructions.InstructionsApplyingPropertyNodeVisitor
import pw.stamina.occasum.node.instructions.PropertyNodeInstructionList

//TODO: Exceptions doesn't get propagated
class InstructionBasedPropertyScanResult(
        private val instructionList: PropertyNodeInstructionList,
        override val exceptions: List<Exception>)
    : PropertyScanResult {

    override fun attach(node: PropertyNode) {
        val visitor = InstructionsApplyingPropertyNodeVisitor(instructionList)
        node.accept(visitor)
    }
}
