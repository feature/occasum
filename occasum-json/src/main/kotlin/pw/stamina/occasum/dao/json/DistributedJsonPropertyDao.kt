package pw.stamina.occasum.dao.json

import com.google.gson.JsonObject
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
    : JsonPropertyDao(propertyLocatorService, rootDirectory) {

    override fun resolveHandlePath(handle: PropertyHandle) = resolveRelativeJsonPath(handle.id)

    @Throws(Exception::class)
    override fun save(handle: PropertyHandle, destination: Path) {
        val rootNode = propertyLocatorService.findRootNode(handle)
        rootNode ?: throw createPropertiesNotFoundForHandleException(handle)

        save(rootNode, destination)
    }

    @Throws(Exception::class)
    private fun save(rootNode: PropertyNode, destination: Path) {
        val children = rootNode.children ?: return

        Files.createDirectories(rootDirectory)

        val serializedPropertiesAsBytes = serializePropertiesToBytes(children)
        writeBytesToLocation(serializedPropertiesAsBytes, destination)
    }

    override fun saveAll(destination: Path): Iterable<Exception> {
        val exceptions = ArrayList<Exception>()

        propertyLocatorService.findAllRootNodes().keys
                .forEach { handle ->
                    try {
                        save(handle)
                    } catch (e: IOException) {
                        exceptions.add(e)
                    }
                }

        return exceptions
    }

    @Throws(Exception::class)
    override fun load(handle: PropertyHandle, destination: Path) {
        val rootNode = propertyLocatorService.findRootNode(handle)
        rootNode ?: throw createPropertiesNotFoundForHandleException(handle)

        return loadFromPath(rootNode, destination)
    }

    override fun loadAll(source: Path): Iterable<Exception> {
        if (!Files.exists(source)) {
            return emptyList()
        }

        val exceptions = ArrayList<Exception>()

        try {
            Files.newDirectoryStream(source, "*.json").use { directory ->
                directory.forEach { path ->
                    val handleId = getHandleIdFromJsonFilePath(path)
                    val rootNode = propertyLocatorService.findRootNode(handleId)

                    rootNode ?: return@forEach

                    try {
                        loadFromPath(rootNode, path)
                    } catch (e: Exception) {
                        exceptions.add(e)
                    }
                }
            }
        } catch (e: DirectoryIteratorException) {
            return listOf(e)
        }

        return exceptions
    }

    @Throws(Exception::class)
    private fun loadFromPath(rootNode: PropertyNode, sourcePath: Path) {
        rootNode.children ?: return

        if (Files.notExists(sourcePath)) {
            return
        }

        val serialized = JsonPropertyDao.readSerializeJsonFromLocation(sourcePath)

        if (!serialized.isJsonObject) {
            return
        }

        val loadingVisitor = JsonLoadingPropertyNodeVisitor(serialized)
        rootNode.accept(loadingVisitor)
    }

    private fun resolveRelativeJsonPath(path: String): Path {
        return rootDirectory.resolve(path + JSON_FILE_EXTENSION)
    }

    companion object {
        private const val JSON_FILE_EXTENSION = ".json"
        private const val JSON_FILE_EXTENSION_LENGTH = JSON_FILE_EXTENSION.length

        private fun serializePropertiesToBytes(properties: List<PropertyNode>): ByteArray {
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
