package pw.stamina.occasum.node.instructions;

import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;

import java.util.Objects;
import java.util.Optional;

public final class InstructionsApplyingPropertyNodeVisitor implements PropertyNodeVisitor {
    private final PropertyNodeInstructionList instructions;

    public InstructionsApplyingPropertyNodeVisitor(PropertyNodeInstructionList instructions) {
        Objects.requireNonNull(instructions, "instructions");
        this.instructions = instructions;
    }

    @Override
    public void visitNode(PropertyNode node) {
        instructions.forEach(instruction -> instruction.apply(node));
    }

    @Override
    public Optional<PropertyNodeVisitor> visitChildNode(PropertyNode childNode) {
        String name = childNode.getName();
        return instructions.findNestedInstructions(name).map(InstructionsApplyingPropertyNodeVisitor::new);
    }
}
