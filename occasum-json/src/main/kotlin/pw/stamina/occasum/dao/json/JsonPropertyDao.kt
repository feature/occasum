package pw.stamina.occasum.dao.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import pw.stamina.occasum.dao.AbstractFlatfilePropertyDao
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.properties.Property
import pw.stamina.occasum.registry.PropertyLocatorService
import java.io.IOException
import java.nio.file.Path

abstract class JsonPropertyDao internal constructor(
        internal val propertyLocatorService: PropertyLocatorService,
        location: Path)
    : AbstractFlatfilePropertyDao(location) {

    protected companion object {
        internal val PROPERTY_DAO_GSON = GsonBuilder()
                .registerTypeHierarchyAdapter(Property::class.java, PropertyJsonSerializer())
                .registerTypeHierarchyAdapter(PropertyNode::class.java, PropertyNodeJsonSerializer())
                .setPrettyPrinting()
                .create()

        @Throws(IOException::class, JsonParseException::class)
        internal fun readSerializeJsonFromLocation(location: Path): JsonElement {
            val reader = AbstractFlatfilePropertyDao.streamBytesFromLocation(location)
            val parser = JsonParser()
            return parser.parse(reader)
        }

        fun distributed(propertyLocatorService: PropertyLocatorService, rootFolder: Path): JsonPropertyDao {
            return DistributedJsonPropertyDao(propertyLocatorService, rootFolder)
        }
    }
}
