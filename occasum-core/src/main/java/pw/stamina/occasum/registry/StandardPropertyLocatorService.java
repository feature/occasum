package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public final class StandardPropertyLocatorService implements PropertyLocatorService {
    private final PropertyRegistry registry;

    @Inject
    StandardPropertyLocatorService(PropertyRegistry registry) {
        Objects.requireNonNull(registry, "registry");
        this.registry = registry;
    }

    @Override
    public Optional<PropertyNode> findRootNode(PropertyHandle handle) {
        Objects.requireNonNull(handle, "handle");
        return registry.findRootNode(handle);
    }

    @Override
    public Optional<PropertyNode> findRootNode(String handleId) {
        Objects.requireNonNull(handleId, "handleId");
        return findHandleById(handleId).flatMap(this::findRootNode);
    }

    @Override
    public Map<PropertyHandle, PropertyNode> findAllRootNodes() {
        return registry.findAllRootNodes();
    }

    @Override
    public Optional<PropertyNode> findNode(PropertyHandle handle, String path) {
        String[] pathSegments = parseAndValidateSearchPath(path);
        Optional<PropertyNode> root = findRootNode(handle);
        return root.map(node -> findNodeByPath(node, pathSegments));
    }

    @Override
    public Optional<PropertyNode> findNode(String handleId, String path) {
        return findHandleById(handleId).flatMap(handle -> findNode(handle, path));
    }

    private Optional<PropertyHandle> findHandleById(String id) {
        return findAllRootNodes().entrySet().stream()
                .filter(doesEntryHandleIdMatch(id))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private static Predicate<Map.Entry<PropertyHandle, ?>> doesEntryHandleIdMatch(String handleId) {
        return entry -> entry.getKey().getId().equalsIgnoreCase(handleId);
    }

    private static String[] parseAndValidateSearchPath(String path) {
        Objects.requireNonNull(path, "path");
        String[] pathSegments = path.split("\\.");

        if (pathSegments.length == 0) {
            throw new IllegalArgumentException("split path length is 0");
        }

        return pathSegments;
    }

    private PropertyNode findNodeByPath(PropertyNode root, String[] pathSegments) {
        PropertyNode current = root;

        for (String pathSegment : pathSegments) {
            Optional<PropertyNode> found = current.find(pathSegment);

            if (found.isPresent()) {
                current = found.get();
            } else {
                return null;
            }
        }

        return current;
    }
}
