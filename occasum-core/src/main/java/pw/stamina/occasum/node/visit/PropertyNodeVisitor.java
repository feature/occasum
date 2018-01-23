package pw.stamina.occasum.node.visit;

import pw.stamina.occasum.node.PropertyNode;

import java.util.Optional;

public interface PropertyNodeVisitor {

    void visitNode(PropertyNode node);

    default Optional<PropertyNodeVisitor> visitChildNode(PropertyNode childNode) {
        return Optional.of(this);
    }
}
