package pw.stamina.occasum.node.visit;

import pw.stamina.occasum.node.PropertyNode;

import java.util.Optional;

public abstract class AbstractPropertyNodeVisitor implements PropertyNodeVisitor {

    @Override
    public final Optional<PropertyNodeVisitor> visitChildNode(PropertyNode childNode) {
        return Optional.ofNullable(createChildVisitor(childNode));
    }

    protected abstract PropertyNodeVisitor createChildVisitor(PropertyNode childNode);
}
