package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class NullRejectingPropertyRegistryDecorator implements PropertyRegistry {
    private final PropertyRegistry registry;

    @Inject
    NullRejectingPropertyRegistryDecorator(PropertyRegistry registry) {
        Objects.requireNonNull(registry, "registry");
        this.registry = registry;
    }

    @Override
    public PropertyNode register(PropertyHandle handle, Property property) {
        Objects.requireNonNull(handle, "handle");
        Objects.requireNonNull(property, "property");
        return registry.register(handle, property);
    }

    @Override
    public void registerAll(PropertyHandle handle, Iterable<Property> properties) {
        Objects.requireNonNull(handle, "handle");
        Objects.requireNonNull(properties, "properties");
        registry.registerAll(handle, properties);
    }

    @Override
    public void unregisterAll(PropertyHandle handle) {
        Objects.requireNonNull(handle, "handle");
        registry.unregisterAll(handle);
    }

    @Override
    public void unregister(PropertyNode node) {
        Objects.requireNonNull(node, "node");
        registry.unregister(node);
    }

    @Override
    public boolean unregister(PropertyHandle handle, String path) {
        Objects.requireNonNull(handle, "handle");
        Objects.requireNonNull(path, "path");
        return registry.unregister(handle, path);
    }

    @Override
    public Optional<PropertyNode> findRootNode(PropertyHandle handle) {
        Objects.requireNonNull(handle, "handle");
        return registry.findRootNode(handle);
    }

    @Override
    public PropertyNode findOrCreateRootNode(PropertyHandle handle) {
        Objects.requireNonNull(handle, "handle");
        return registry.findOrCreateRootNode(handle);
    }

    @Override
    public Map<PropertyHandle, PropertyNode> findAllRootNodes() {
        return registry.findAllRootNodes();
    }
}
