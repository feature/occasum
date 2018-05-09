package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import javax.inject.Inject

class StandardPropertyLocatorService @Inject
internal constructor(private val registry: PropertyRegistry) : PropertyLocatorService {

    override fun findRootNode(handle: PropertyHandle): PropertyNode? {
        return registry.findRootNode(handle)
    }

    override fun findRootNode(handleId: String): PropertyNode? {
        return findHandleById(handleId).flatMap { this.findRootNode(it) }
    }

    override fun findAllRootNodes(): Map<PropertyHandle, PropertyNode> {
        return registry.findAllRootNodes()
    }

    override fun findNode(handle: PropertyHandle, path: String): PropertyNode? {
        val pathSegments = parseAndValidateSearchPath(path)
        val root = findRootNode(handle)

        return root.map({ node -> findNodeByPath(node, pathSegments) })
    }

    override fun findNode(handleId: String, path: String): PropertyNode? {
        return findHandleById(handleId).flatMap { handle -> findNode(handle, path) }
    }

    private fun findHandleById(id: String): PropertyHandle? {
        return findAllRootNodes().entries.stream()
                .filter(doesEntryHandleIdMatch(id))
                .map<PropertyHandle> { it.key }
                .findFirst()
    }

    private fun doesEntryHandleIdMatch(handleId: String): (Entry<PropertyHandle, *>) -> Boolean {
        return { entry -> entry.key.getId().equalsIgnoreCase(handleId) }
    }

    private fun parseAndValidateSearchPath(path: String): Array<String> {
        val pathSegments = path.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (pathSegments.isEmpty()) {
            throw IllegalArgumentException("split path length is 0")
        }

        return pathSegments
    }

    private fun findNodeByPath(root: PropertyNode, pathSegments: Array<String>): PropertyNode? {
        var current = root

        for (pathSegment in pathSegments) {
            val found = current.find(pathSegment)

            if (found.isPresent) {
                current = found.get()
            } else {
                return null
            }
        }

        return current
    }
}
