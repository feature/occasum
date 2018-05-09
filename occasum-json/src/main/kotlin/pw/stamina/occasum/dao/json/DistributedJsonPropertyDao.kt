package pw.stamina.occasum.dao.json

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.registry.PropertyLocatorService
import java.io.IOException
import java.nio.file.DirectoryIteratorException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.Collections.emptyList
import javax.inject.Inject
import javax.inject.Named

/**
 * A JSON flatfile based property DAO implementation.
 *
 *
 *
 * This DAO serializes property values and writes them to
 * individual JSON files, separated by the root handle name.
 */
class DistributedJsonPropertyDao @Inject internal constructor(
        propertyLocatorService: PropertyLocatorService,
        @Named("property.dao") private val rootDirectory: Path)
    : JsonPropertyDao(propertyLocatorService) {

    @Throws(IOException::class)
    override fun saveAll(): Iterable<Exception> {
        Files.createDirectories(rootDirectory)
        val exceptions = ArrayList<Exception>()

        propertyLocatorService.findAllRootNodes()
                .forEach { handle, rootNode ->
                    try {
                        save(handle, rootNode)
                    } catch (e: IOException) {
                        exceptions.add(e)
                    }
                }

        return exceptions
    }

    @Throws(IOException::class)
    override fun save(handle: PropertyHandle) {
        val rootNode = propertyLocatorService.findRootNode(handle)

        rootNode ?: throw createPropertiesNotFoundForHandleException(handle)

        save(handle, rootNode)
    }

    @Throws(IOException::class)
    private fun save(handle: PropertyHandle, rootNode: PropertyNode) {
        if (!rootNode.hasChildren()) {
            return
        }

        Files.createDirectories(rootDirectory)

        val serializedPropertiesBytes = serializePropertiesToBytes(rootNode)
        val destination = resolveRelativeJsonPath(handle.id)

        writeBytesToLocation(serializedPropertiesBytes, destination)
    }

    @Throws(IOException::class)
    override fun loadAll(): Iterable<Exception> {
        if (!Files.exists(rootDirectory)) {
            return emptyList()
        }

        val exceptions = ArrayList<Exception>()

        try {
            Files.newDirectoryStream(rootDirectory, "*.json").use { directory ->
                directory.forEach { path ->
                    val handleId = getHandleIdFromJsonFilePath(path)
                    val rootNode = propertyLocatorService.findRootNode(handleId)

                    rootNode ?: return@forEach

                    loadFromPath(rootNode, path).forEach { exceptions.add(it) }
                }
            }
        } catch (e: DirectoryIteratorException) {
            exceptions.add(e)
        }

        return exceptions
    }

    override fun load(handle: PropertyHandle): Iterable<Exception> {
        val rootNode = propertyLocatorService.findRootNode(handle)

        rootNode ?: throw createPropertiesNotFoundForHandleException(handle)

        val sourcePath = resolveRelativeJsonPath(handle.id)
        return loadFromPath(rootNode, sourcePath)
    }

    private fun loadFromPath(rootNode: PropertyNode, sourcePath: Path): Iterable<Exception> {
        if (!rootNode.hasChildren()) {
            return emptyList()
        }

        if (Files.notExists(sourcePath)) {
            return emptyList()
        }

        try {
            val serialized = JsonPropertyDao.readSerializeJsonFromLocation(sourcePath)

            if (!serialized.isJsonObject) {
                return emptyList()
            }

            val loadingVisitor = JsonLoadingPropertyNodeVisitor(serialized)
            rootNode.accept(loadingVisitor)

            return loadingVisitor.exceptions
        } catch (e: IOException) {
            return setOf<Exception>(e)
        } catch (e: JsonParseException) {
            return setOf<Exception>(e)
        }

    }

    private fun resolveRelativeJsonPath(path: String): Path {
        return rootDirectory.resolve(path + JSON_FILE_EXTENSION)
    }

    companion object {
        private const val JSON_FILE_EXTENSION = ".json"
        private const val JSON_FILE_EXTENSION_LENGTH = JSON_FILE_EXTENSION.length

        private fun serializePropertiesToBytes(rootNode: PropertyNode): ByteArray {
            val properties = rootNode.findChildren()
            val serializedProperties = serializeProperties(properties)
            return serializedProperties.toByteArray()
        }

        private fun serializeProperties(nodes: List<PropertyNode>): String {
            val serialized = JsonObject()

            nodes.forEach { node -> serialized.add(node.id, JsonPropertyDao.PROPERTY_DAO_GSON.toJsonTree(node)) }
            return JsonPropertyDao.PROPERTY_DAO_GSON.toJson(serialized)
        }

        private fun createPropertiesNotFoundForHandleException(handle: PropertyHandle): IllegalArgumentException {
            return IllegalArgumentException(
                    "the specified handle does not have any properties registered to it. Handle id: ${handle.id}")
        }

        private fun getHandleIdFromJsonFilePath(path: Path): String {
            val fileName = path.fileName.toString()
            val endIndex = fileName.length - JSON_FILE_EXTENSION_LENGTH
            return fileName.substring(0, endIndex)
        }
    }
}
