package pw.stamina.occasum.dao

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.properties.Property

/**
 * The `PropertyDao` interface provides a simple and uniform
 * interface to handle saving and loading [Property] values.
 *
 *
 *
 * Implementations of this class can be assumed to be thread safe,
 * unless specified otherwise.
 */
interface PropertyDao {

    /**
     * Saves all the [properties][Property] for the specified `handle`.
     *
     * @param handle the handle
     * @throws Exception if an exception occurred when saving the properties
     */
    @Throws(Exception::class)
    fun save(handle: PropertyHandle)

    /**
     * Saves all [properties][Property] handled by this DAO. An [Iterable]
     * of [Exception]s is returned, containing all the exceptions that caused
     * individual save operations to fail. This method may also throw a [Exception]
     * to indicate it was unable to do the initial setup to start saving individual
     * [properties][Property].
     *
     * @return an [Iterable] of exceptions that caused individual save operations to fail
     * @throws Exception if an exception occurred that caused all save operations to fail
     */
    @Throws(Exception::class)
    fun saveAll(): Iterable<Exception>

    @Throws(Exception::class)
    fun load(handle: PropertyHandle): Iterable<Exception>

    @Throws(Exception::class)
    fun loadAll(): Iterable<Exception>
}
