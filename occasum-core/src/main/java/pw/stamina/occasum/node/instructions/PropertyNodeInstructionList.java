package pw.stamina.occasum.node.instructions;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.properties.Property;

import java.util.Optional;

public interface PropertyNodeInstructionList extends Iterable<PropertyNodeInstruction> {

    Optional<PropertyNodeInstructionList> findNestedInstructions(String name);

    void property(PropertyHandle handle, Property property);

    void property(Property property);

    PropertyNodeInstructionList folder(PropertyHandle handle, String name);

    PropertyNodeInstructionList folder(String name);

    static PropertyNodeInstructionList newStandard() {
        return new StandardPropertyNodeInstructionList();
    }
}
