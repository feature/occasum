package pw.stamina.occasum.dao;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.properties.Property;

/**
 * The {@code PropertyDao} interface provides a simple and uniform
 * interface to handle saving and loading {@link Property} values.
 * <p>
 *
 * Implementations of this class can be assumed to be thread safe,
 * unless specified otherwise.
 */
public interface PropertyDao {

    /**
     * Saves all the {@link Property properties} for the specified {@code handle}.
     *
     * @param handle the handle
     * @throws Exception if an exception occurred when saving the properties
     */
    void save(PropertyHandle handle) throws Exception;

    /**
     * Saves all {@link Property properties} handled by this DAO. An {@link Iterable}
     * of {@link Exception}s is returned, containing all the exceptions that caused
     * individual save operations to fail. This method may also throw a {@link Exception}
     * to indicate it was unable to do the initial setup to start saving individual
     * {@link Property properties}.
     *
     * @return an {@link Iterable} of exceptions that caused individual save operations to fail
     * @throws Exception if an exception occurred that caused all save operations to fail
     */
    Iterable<Exception> saveAll() throws Exception;

    Iterable<Exception> load(PropertyHandle handle) throws Exception;

    Iterable<Exception> loadAll() throws Exception;
}
