package pw.stamina.occasum.node.instructions

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode

internal class FolderPropertyNodeInstruction(
        private val handle: PropertyHandle?,
        private val name: String)
    : PropertyNodeInstruction {

    override fun apply(node: PropertyNode) {
        if (handle != null) {
            node.folder(handle, name)
        } else {
            node.folder(name)
        }
    }
}
