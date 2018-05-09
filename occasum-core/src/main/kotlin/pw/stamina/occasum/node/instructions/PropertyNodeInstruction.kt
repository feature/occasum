package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.node.PropertyNode

interface PropertyNodeInstruction {

    fun apply(node: PropertyNode)
}
