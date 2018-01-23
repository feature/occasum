package pw.stamina.occasum.node;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

final class RootPropertyNode extends AbstractPropertyNode {

    RootPropertyNode(PropertyNodeFactory factory, PropertyHandle handle) {
        super(factory, handle);
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public String getId() {
        return handle.getId();
    }

    @Override
    public Property getProperty() {
        throw new IllegalStateException("root nodes cannot have properties");
    }

    @Override
    public boolean hasProperty() {
        return false;
    }

    @Override
    public PropertyNode getParent() {
        throw new IllegalStateException("root nodes cannot have parents");
    }

    @Override
    public boolean hasParent() {
        return false;
    }
}
