package pw.stamina.occasum.node.instructions;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;

final class FolderPropertyNodeInstruction implements PropertyNodeInstruction {
    private final PropertyHandle handle;
    private final String name;

    FolderPropertyNodeInstruction(PropertyHandle handle, String name) {
        this.handle = handle;
        this.name = name;
    }

    @Override
    public void apply(PropertyNode node) {
        if (handle != null) {
            node.folder(handle, name);
        } else {
            node.folder(name);
        }
    }
}
