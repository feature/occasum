package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.node.visit.PropertyRegisterCleanupPropertyNodeVisitor;
import pw.stamina.occasum.properties.Property;

import javax.inject.Inject;
import java.util.*;

public final class StandardPropertyRegistry implements PropertyRegistry {
    private final Map<PropertyHandle, PropertyNode> properties;
    private final PropertyNodeFactory factory;

    @Inject
    StandardPropertyRegistry(PropertyNodeFactory factory) {
        Objects.requireNonNull(factory, "factory");
        this.factory = factory;
        this.properties = new IdentityHashMap<>();
    }

    @Override
    public PropertyNode register(PropertyHandle handle, Property property) {
        PropertyNode root = findOrCreateRootNode(handle);
        return root.property(property);
    }

    @Override
    public void registerAll(PropertyHandle handle, Iterable<Property> properties) {
        PropertyNode root = findOrCreateRootNode(handle);
        properties.forEach(root::property);
    }

    //TODO: Make absolutely sure this works as intended
    //TODO: Walk ALL nodes, to make sure everything is unregistered.
    @Override
    public void unregisterAll(PropertyHandle handle) {
        PropertyNode removed = properties.remove(handle);

        if (removed != null) {
            factory.notifyRemoval(removed);
        }

        PropertyNodeVisitor nodeRemovingVisitor =
                new PropertyRegisterCleanupPropertyNodeVisitor(handle, factory::notifyRemoval);

        properties.values().forEach(node -> node.accept(nodeRemovingVisitor));
    }

    @Override
    public void unregister(PropertyNode node) {
        if (node.hasParent()) {
            PropertyNode parent = node.getParent();
            parent.removeChild(node);
        } else {
            //TODO: Why is this illegal?
            //TODO: Property exception message
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean unregister(PropertyHandle handle, String path) {
        //TODO: Implement
        return false;
    }

    @Override
    public Optional<PropertyNode> findRootNode(PropertyHandle handle) {
        return Optional.ofNullable(properties.get(handle));
    }

    @Override
    public PropertyNode findOrCreateRootNode(PropertyHandle handle) {
        return properties.computeIfAbsent(handle, factory::root);
    }

    @Override
    public Map<PropertyHandle, PropertyNode> findAllRootNodes() {
        return Collections.unmodifiableMap(properties);
    }
}
