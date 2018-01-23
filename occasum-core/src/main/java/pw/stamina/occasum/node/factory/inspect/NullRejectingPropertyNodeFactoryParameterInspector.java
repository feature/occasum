package pw.stamina.occasum.node.factory.inspect;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;

import static java.util.Objects.requireNonNull;

final class NullRejectingPropertyNodeFactoryParameterInspector
        implements PropertyNodeFactoryParameterInspector {

    @Override
    public void inspectRoot(PropertyHandle handle) {
        requireNonNull(handle, "handle");
    }

    @Override
    public void inspectProperty(PropertyHandle handle, PropertyNode parent, Property property) {
        requireNonNull(handle, "handle");
        requireNonNull(parent, "parent");
        requireNonNull(property, "property");
    }

    @Override
    public void inspectFolder(PropertyHandle handle, PropertyNode parent, String name) {
        requireNonNull(handle, "handle");
        requireNonNull(parent, "parent");
        requireNonNull(name, "name");
    }

    @Override
    public void inspectNotifyRemoval(PropertyNode node) {
        requireNonNull(node, "node");
    }
}
