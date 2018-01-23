package pw.stamina.occasum.node.instructions;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.properties.Property;

import java.util.*;

final class StandardPropertyNodeInstructionList implements PropertyNodeInstructionList {
    private final List<PropertyNodeInstruction> instructions;
    private final Map<String, PropertyNodeInstructionList> nestedInstructions;

    StandardPropertyNodeInstructionList() {
        this.instructions = new LinkedList<>();
        this.nestedInstructions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public Optional<PropertyNodeInstructionList> findNestedInstructions(String name) {
        return Optional.ofNullable(nestedInstructions.get(name));
    }

    @Override
    public void property(PropertyHandle handle, Property property) {
        //Null handle is permitted
        instructions.add(new PropertyPropertyNodeInstruction(handle, property));
    }

    @Override
    public void property(Property property) {
        property(null, property);
    }

    @Override
    public PropertyNodeInstructionList folder(PropertyHandle handle, String name) {
        if (nestedInstructions.containsKey(name)) {
            instructions.add(new FolderPropertyNodeInstruction(handle, name));
        }

        return nestedInstructions.computeIfAbsent(name, s -> new StandardPropertyNodeInstructionList());
    }

    @Override
    public PropertyNodeInstructionList folder(String name) {
        return folder(null, name);
    }

    @Override
    public Iterator<PropertyNodeInstruction> iterator() {
        return instructions.iterator();
    }
}
