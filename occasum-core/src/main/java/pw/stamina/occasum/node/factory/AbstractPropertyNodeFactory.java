package pw.stamina.occasum.node.factory;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.inspect.PropertyNodeFactoryParameterInspector;
import pw.stamina.occasum.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPropertyNodeFactory implements PropertyNodeFactory {
    private final List<PropertyNodeFactoryParameterInspector> inspectors;

    protected AbstractPropertyNodeFactory() {
        this.inspectors = new ArrayList<>();
    }

    @Override
    public PropertyNode root(PropertyHandle handle) {
        inspectors.forEach(inspector -> inspector.inspectRoot(handle));
        return createRoot(handle);
    }

    protected abstract PropertyNode createRoot(PropertyHandle handle);

    @Override
    public PropertyNode property(PropertyHandle handle, PropertyNode parent, Property property) {
        inspectors.forEach(inspector -> inspector.inspectProperty(handle, parent, property));
        return createProperty(handle, parent, property);
    }

    protected abstract PropertyNode createProperty(PropertyHandle handle, PropertyNode parent, Property property);

    @Override
    public PropertyNode folder(PropertyHandle handle, PropertyNode parent, String name) {
        inspectors.forEach(inspector -> inspector.inspectFolder(handle, parent, name));
        return createFolder(handle, parent, name);
    }

    protected abstract PropertyNode createFolder(PropertyHandle handle, PropertyNode parent, String name);

    @Override
    public void notifyRemoval(PropertyNode node) {
        inspectors.forEach(inspector -> inspector.inspectNotifyRemoval(node));
    }

    @Override
    public void addInspector(PropertyNodeFactoryParameterInspector inspector) {
        Objects.requireNonNull(inspector, "inspector");
        inspectors.add(inspector);
    }
}
