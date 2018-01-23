package pw.stamina.occasum.node;

import pw.stamina.occasum.Named;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

final class FolderPropertyNode extends AbstractParentedPropertyNode {
    private final String name;
    private final String id;

    FolderPropertyNode(PropertyNodeFactory factory,
                       PropertyHandle handle,
                       PropertyNode parent,
                       String name) {
        super(factory, handle, parent);

        this.name = Named.validateName(name);
        this.id = Named.createIdFromName(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Property getProperty() {
        throw new IllegalStateException("folder nodes cannot have a property");
    }

    @Override
    public boolean hasProperty() {
        return false;
    }
}
