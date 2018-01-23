package pw.stamina.occasum.node.visit;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;

import java.util.Objects;
import java.util.function.Consumer;

public final class PropertyRegisterCleanupPropertyNodeVisitor implements PropertyNodeVisitor {
    private final PropertyHandle handle;
    private final Consumer<PropertyNode> notifier;

    public PropertyRegisterCleanupPropertyNodeVisitor(PropertyHandle handle,
                                                      Consumer<PropertyNode> notifier) {
        Objects.requireNonNull(handle, "handle");
        Objects.requireNonNull(notifier, "notifier");

        this.handle = handle;
        this.notifier = notifier;
    }

    @Override
    public void visitNode(PropertyNode node) {
        if (node.getHandle() == handle) {
            assert node.hasParent();
            node.getParent().removeChild(node);
            notifier.accept(node);
        }
    }
}
