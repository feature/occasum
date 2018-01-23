package pw.stamina.occasum.node;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.AbstractPropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

import javax.inject.Inject;

public final class StandardPropertyNodeFactory extends AbstractPropertyNodeFactory {

    @Inject
    StandardPropertyNodeFactory() {}

    @Override
    protected PropertyNode createRoot(PropertyHandle handle) {
        return new RootPropertyNode(this, handle);
    }

    @Override
    protected PropertyNode createProperty(PropertyHandle handle, PropertyNode parent, Property property) {
        return new SimplePropertyNode(this, handle, parent, property);
    }

    @Override
    protected PropertyNode createFolder(PropertyHandle handle, PropertyNode parent, String name) {
        return new FolderPropertyNode(this, handle, parent, name);
    }

    public static StandardPropertyNodeFactory newInstance() {
        return new StandardPropertyNodeFactory();
    }
}
