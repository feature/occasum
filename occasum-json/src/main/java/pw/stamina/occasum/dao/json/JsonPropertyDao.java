package pw.stamina.occasum.dao.json;

import com.google.gson.*;
import pw.stamina.occasum.dao.AbstractFlatfilePropertyDao;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;
import pw.stamina.occasum.registry.PropertyLocatorService;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Objects;

public abstract class JsonPropertyDao extends AbstractFlatfilePropertyDao {
    static final Gson PROPERTY_DAO_GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Property.class, new PropertyJsonSerializer())
            .registerTypeHierarchyAdapter(PropertyNode.class, new PropertyNodeJsonSerializer())
            .setPrettyPrinting()
            .create();

    final PropertyLocatorService propertyLocatorService;

    JsonPropertyDao(PropertyLocatorService propertyLocatorService) {
        Objects.requireNonNull(propertyLocatorService, "propertyLocatorService");
        this.propertyLocatorService = propertyLocatorService;
    }

    static JsonElement readSerializeJsonFromLocation(Path location) throws IOException, JsonParseException {
        Reader reader = AbstractFlatfilePropertyDao.readBytesFromLocation(location);
        JsonParser parser = new JsonParser();
        return parser.parse(reader);
    }

    public static JsonPropertyDao distributed(PropertyLocatorService propertyLocatorService, Path rootFolder) {
        return new DistributedJsonPropertyDao(propertyLocatorService, rootFolder);
    }
}
