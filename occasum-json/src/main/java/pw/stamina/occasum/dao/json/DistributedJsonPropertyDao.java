package pw.stamina.occasum.dao.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.registry.PropertyLocatorService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A JSON flatfile based property DAO implementation.
 * <p>
 *
 * This DAO serializes property values and writes them to
 * individual JSON files, separated by the root handle name.
 */
public final class DistributedJsonPropertyDao extends JsonPropertyDao {
    private static final String JSON_FILE_EXTENSION = ".json";
    private static final int JSON_FILE_EXTENSION_LENGTH = JSON_FILE_EXTENSION.length();

    private final Path rootDirectory;

    @Inject
    DistributedJsonPropertyDao(PropertyLocatorService propertyLocatorService,
                               @Named("property.dao") Path rootDirectory) {
        super(propertyLocatorService);
        this.rootDirectory = rootDirectory;
    }

    @Override
    public Iterable<Exception> saveAll() throws IOException {
        Files.createDirectories(rootDirectory);
        List<Exception> exceptions = new ArrayList<>();

        propertyLocatorService.findAllRootNodes()
                .forEach((handle, rootNode) -> {
                    try {
                        save(handle, rootNode);
                    } catch (IOException e) {
                        exceptions.add(e);
                    }
                });

        return exceptions;
    }

    @Override
    public void save(PropertyHandle handle) throws IOException {
        PropertyNode rootNode = propertyLocatorService.findRootNode(handle)
                .orElseThrow(createPropertiesNotFoundForHandleException(handle));

        save(handle, rootNode);
    }

    private void save(PropertyHandle handle, PropertyNode rootNode) throws IOException {
        if (!rootNode.hasChildren()) {
            return;
        }

        Files.createDirectories(rootDirectory);

        byte[] serializedPropertiesBytes = serializePropertiesToBytes(rootNode);
        Path destination = resolveRelativeJsonPath(handle.getId());

        writeBytesToLocation(serializedPropertiesBytes, destination);
    }

    private static byte[] serializePropertiesToBytes(PropertyNode rootNode) {
        List<PropertyNode> properties = rootNode.findChildren();
        String serializedProperties = serializeProperties(properties);
        return serializedProperties.getBytes();
    }

    private static String serializeProperties(List<PropertyNode> nodes) {
        JsonObject serialized = new JsonObject();

        nodes.forEach(node -> serialized.add(node.getId(), PROPERTY_DAO_GSON.toJsonTree(node)));
        return PROPERTY_DAO_GSON.toJson(serialized);
    }

    @Override
    public Iterable<Exception> loadAll() throws IOException {
        if (!Files.exists(rootDirectory)) {
            return Collections.emptyList();
        }

        List<Exception> exceptions = new ArrayList<>();

        try (DirectoryStream<Path> directory = Files.newDirectoryStream(rootDirectory, "*.json")) {
            directory.forEach(path -> {
                String handleId = getHandleIdFromJsonFilePath(path);
                Optional<PropertyNode> rootNode = propertyLocatorService.findRootNode(handleId);

                rootNode.ifPresent(node -> loadFromPath(node, path).forEach(exceptions::add));
            });
        } catch (DirectoryIteratorException e) {
            exceptions.add(e);
        }

        return exceptions;
    }

    @Override
    public Iterable<Exception> load(PropertyHandle handle) {
        PropertyNode rootNode = propertyLocatorService.findRootNode(handle)
                .orElseThrow(createPropertiesNotFoundForHandleException(handle));

        Path sourcePath = resolveRelativeJsonPath(handle.getId());
        return loadFromPath(rootNode, sourcePath);
    }

    private Iterable<Exception> loadFromPath(PropertyNode rootNode, Path sourcePath) {
        if (!rootNode.hasChildren()) {
            return Collections.emptyList();
        }

        if (Files.notExists(sourcePath)) {
            return Collections.emptyList();
        }

        try {
            JsonElement serialized = readSerializeJsonFromLocation(sourcePath);

            if (!serialized.isJsonObject()) {
                return Collections.emptyList();
            }

            JsonLoadingPropertyNodeVisitor loadingVisitor = new JsonLoadingPropertyNodeVisitor(serialized);
            rootNode.accept(loadingVisitor);

            return loadingVisitor.getExceptions();
        } catch (IOException | JsonParseException e) {
            return Collections.singleton(e);
        }
    }

    private Path resolveRelativeJsonPath(String path) {
        return rootDirectory.resolve(path + JSON_FILE_EXTENSION);
    }

    private static Supplier<IllegalArgumentException> createPropertiesNotFoundForHandleException(PropertyHandle handle) {
        return () -> new IllegalArgumentException("the specified handle does not have " +
                "any properties registered to it. Handle id: " + handle.getId());
    }

    private static String getHandleIdFromJsonFilePath(Path path) {
        String fileName = path.getFileName().toString();
        int endIndex = fileName.length() - JSON_FILE_EXTENSION_LENGTH;
        return fileName.substring(0, endIndex);
    }
}
