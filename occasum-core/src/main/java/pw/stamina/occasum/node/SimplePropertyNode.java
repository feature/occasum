package pw.stamina.occasum.node;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

import java.util.Objects;

final class SimplePropertyNode extends AbstractParentedPropertyNode {
    private final Property property;

    SimplePropertyNode(PropertyNodeFactory factory,
                       PropertyHandle handle,
                       PropertyNode parent,
                       Property property) {
        super(factory, handle, parent);
        Objects.requireNonNull(property, "property");
        this.property = property;
    }

    @Override
    public String getName() {
        return property.getName();
    }

    @Override
    public String getId() {
        return property.getId();
    }

    @Override
    public Property getProperty() {
        return property;
    }

    @Override
    public boolean hasProperty() {
        return true;
    }
}
