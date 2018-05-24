package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.properties.Property

import java.util.*

internal class StandardPropertyNodeInstructionList : PropertyNodeInstructionList {

    private val instructions: MutableList<PropertyNodeInstruction>
    private val nestedInstructions: MutableMap<String, PropertyNodeInstructionList>

    init {
        this.instructions = LinkedList()
        this.nestedInstructions = TreeMap(String.CASE_INSENSITIVE_ORDER)
    }

    override fun findNestedInstructions(name: String): PropertyNodeInstructionList? {
        return nestedInstructions[name]
    }

    override fun property(handle: PropertyHandle, property: Property<*>) {
        instructions.add(PropertyPropertyNodeInstruction(handle, property))
    }

    override fun property(property: Property<*>) {
        instructions.add(PropertyPropertyNodeInstruction(null, property))
    }

    override fun folder(handle: PropertyHandle, name: String): PropertyNodeInstructionList {
        return addFolderInstruction(handle, name)
    }

    override fun folder(name: String): PropertyNodeInstructionList {
        return addFolderInstruction(null, name)
    }

    private fun addFolderInstruction(handle: PropertyHandle?, name: String): PropertyNodeInstructionList {
        if (nestedInstructions.containsKey(name)) {
            instructions.add(FolderPropertyNodeInstruction(handle, name))
        }

        return nestedInstructions.computeIfAbsent(name) { StandardPropertyNodeInstructionList() }
    }

    override fun iterator(): Iterator<PropertyNodeInstruction> {
        return instructions.iterator()
    }
}
