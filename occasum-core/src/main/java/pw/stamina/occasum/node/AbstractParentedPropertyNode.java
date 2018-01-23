package pw.stamina.occasum.node;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;

import java.util.Objects;

abstract class AbstractParentedPropertyNode extends AbstractPropertyNode {
    private final PropertyNode parent;

    AbstractParentedPropertyNode(PropertyNodeFactory factory,
                                 PropertyHandle handle,
                                 PropertyNode parent) {
        super(factory, handle);
        Objects.requireNonNull(parent, "parent");
        this.parent = parent;
    }

    @Override
    public final PropertyNode getParent() {
        return parent;
    }

    @Override
    public final boolean hasParent() {
        return true;
    }
}
