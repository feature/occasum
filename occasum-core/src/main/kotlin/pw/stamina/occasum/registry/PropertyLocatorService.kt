package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import java.util.*

/**
 * Provides encapsulated and extended functionality to locate
 * `PropertyNode`s from a `PropertyRegistry`.
 * Instances of this interface can be shared, and can
 * generally be considered thread safe, if the backing
 * `registry` is thread safe.
 *
 *
 * Implementations of this interface should only retrieve
 * data from the backing registry, and never try to attempt
 * to create missing data.
 */
interface PropertyLocatorService {

    /**
     * Tries to find the root <tt>PropertyNode</tt> registered
     * to the specified `handle`.
     *
     * @param handle the handle finding its root property node for
     * @return the root [PropertyNode] associated with the specified
     * `handle` wrapped in an [Optional], or [ empty][Optional.empty] if none was found.
     */
    fun findRootNode(handle: PropertyHandle): PropertyNode?

    fun findRootNode(handleId: String): PropertyNode?

    /**
     * Returns a `Map` of all the registered
     * `PropertyHandle`s and their root `PropertyNode`
     *
     * @return all registered `PropertyHandle`s and
     * their root `PropertyNode`
     */
    fun findAllRootNodes(): Map<PropertyHandle, PropertyNode>

    fun findNode(handle: PropertyHandle, path: String): PropertyNode?

    fun findNode(handleId: String, path: String): PropertyNode?

    companion object {

        /**
         * Returns a new standard implementation of this interface,
         * backed with specified `registry`
         *
         * @param registry
         * @return
         */
        fun standard(registry: PropertyRegistry): PropertyLocatorService {
            return StandardPropertyLocatorService(registry)
        }
    }
}
