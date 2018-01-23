package pw.stamina.occasum.node.instructions;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;

final class PropertyPropertyNodeInstruction implements PropertyNodeInstruction {
    private final PropertyHandle handle;
    private final Property property;

    PropertyPropertyNodeInstruction(PropertyHandle handle, Property property) {
        this.handle = handle;
        this.property = property;
    }

    @Override
    public void apply(PropertyNode node) {
        if (handle != null) {
            node.property(handle, property);
        } else {
            node.property(property);
        }
    }
}
