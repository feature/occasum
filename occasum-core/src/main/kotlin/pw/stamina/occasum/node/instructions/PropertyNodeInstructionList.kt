package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.properties.Property

interface PropertyNodeInstructionList : Iterable<PropertyNodeInstruction> {

    fun findNestedInstructions(name: String): PropertyNodeInstructionList?

    fun property(handle: PropertyHandle, property: Property<*>)

    fun property(property: Property<*>)

    fun folder(handle: PropertyHandle, name: String): PropertyNodeInstructionList

    fun folder(name: String): PropertyNodeInstructionList

    companion object {

        fun newStandard(): PropertyNodeInstructionList {
            return StandardPropertyNodeInstructionList()
        }
    }
}
