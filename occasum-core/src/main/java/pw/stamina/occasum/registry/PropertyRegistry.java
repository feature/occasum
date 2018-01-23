package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

import java.util.Map;
import java.util.Optional;

public interface PropertyRegistry {

    PropertyNode register(PropertyHandle handle, Property property);

    void registerAll(PropertyHandle handle, Iterable<Property> properties);

    void unregisterAll(PropertyHandle handle);

    void unregister(PropertyNode node);

    boolean unregister(PropertyHandle handle, String path);

    Optional<PropertyNode> findRootNode(PropertyHandle handle);

    PropertyNode findOrCreateRootNode(PropertyHandle handle);

    //TODO: Implement
    //PropertyNode findNodeOrCreateFolder(PropertyHandle handle, String path);

    Map<PropertyHandle, PropertyNode> findAllRootNodes();

    static PropertyRegistry standard(PropertyNodeFactory factory) {
        return new StandardPropertyRegistry(factory);
    }

    static PropertyRegistry decorateNullRejecting(PropertyRegistry registry) {
        return new NullRejectingPropertyRegistryDecorator(registry);
    }
}
