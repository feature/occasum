package pw.stamina.occasum.scan.result;

import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.instructions.PropertyNodeInstructionList;
import pw.stamina.occasum.node.instructions.InstructionsApplyingPropertyNodeVisitor;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;

import java.util.List;

//TODO: Exceptions doesn't get propagated
public final class InstructionBasedPropertyScanResult implements PropertyScanResult {
    private final PropertyNodeInstructionList instructionList;
    private final List<Exception> exceptions;

    //TODO: SFM and robustness
    public InstructionBasedPropertyScanResult(PropertyNodeInstructionList instructionList,
                                              List<Exception> exceptions) {
        this.instructionList = instructionList;
        this.exceptions = exceptions;
    }

    @Override
    public void attach(PropertyNode node) {
        PropertyNodeVisitor visitor = new InstructionsApplyingPropertyNodeVisitor(instructionList);
        node.accept(visitor);
    }

    @Override
    public Iterable<Exception> getExceptions() {
        return exceptions;
    }
}
