package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import javax.inject.Inject

class StandardPropertyLocatorService @Inject internal constructor(
        private val registry: PropertyRegistry
) : PropertyLocatorService {

    override fun findRootNode(handle: PropertyHandle): PropertyNode? {
        return registry.findRootNode(handle)
    }

    override fun findRootNode(handleId: String): PropertyNode? {
        return findHandleById(handleId)?.let { findRootNode(it) }
    }

    override fun findAllRootNodes(): Map<PropertyHandle, PropertyNode> {
        return registry.findAllRootNodes()
    }

    override fun findNode(handle: PropertyHandle, path: String): PropertyNode? {
        val pathSegments = parseAndValidateSearchPath(path)
        val root = findRootNode(handle)

        return root?.let({ findNodeByPath(it, pathSegments) })
    }

    override fun findNode(handleId: String, path: String): PropertyNode? {
        return findHandleById(handleId)?.let { findNode(it, path) }
    }

    private fun findHandleById(id: String): PropertyHandle? {
        return findAllRootNodes().keys.find(doesHandleIdMatch(id))
    }

    private fun doesHandleIdMatch(handleId: String): (PropertyHandle) -> Boolean {
        return { handle -> handle.id.equals(handleId, ignoreCase = true) }
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
            val found = current[pathSegment]

            if (found != null) {
                current = found
            } else {
                return null
            }
        }

        return current
    }
}
