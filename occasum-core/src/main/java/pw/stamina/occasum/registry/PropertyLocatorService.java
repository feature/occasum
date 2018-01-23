package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;

import java.util.Map;
import java.util.Optional;

/**
 * Provides encapsulated and extended functionality to locate
 * {@code PropertyNode}s from a {@code PropertyRegistry}.
 * Instances of this interface can be shared, and can
 * generally be considered thread safe, if the backing
 * {@code registry} is thread safe.
 *
 * <p>Implementations of this interface should only retrieve
 * data from the backing registry, and never try to attempt
 * to create missing data.
 */
public interface PropertyLocatorService {

    /**
     * Tries to find the root <tt>PropertyNode</tt> registered
     * to the specified {@code handle}.
     *
     * @param handle the handle finding its root property node for
     * @return the root {@link PropertyNode} associated with the specified
     * {@code handle} wrapped in an {@link Optional}, or {@link Optional#empty()
     * empty} if none was found.
     */
    Optional<PropertyNode> findRootNode(PropertyHandle handle);

    Optional<PropertyNode> findRootNode(String handleId);

    /**
     * Returns a {@code Map} of all the registered
     * {@code PropertyHandle}s and their root {@code PropertyNode}
     *
     * @return all registered {@code PropertyHandle}s and
     *         their root {@code PropertyNode}
     */
    Map<PropertyHandle, PropertyNode> findAllRootNodes();

    Optional<PropertyNode> findNode(PropertyHandle handle, String path);

    Optional<PropertyNode> findNode(String handleId, String path);

    /**
     * Returns a new standard implementation of this interface,
     * backed with specified {@code registry}
     *
     * @param registry
     * @return
     */
    static PropertyLocatorService standard(PropertyRegistry registry) {
        return new StandardPropertyLocatorService(registry);
    }
}
