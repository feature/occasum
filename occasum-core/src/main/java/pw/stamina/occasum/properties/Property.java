package pw.stamina.occasum.properties;

import pw.stamina.occasum.Named;

/**
 *
 *
 * This interface is not parameterized to provide better support for
 * primitive types. If you need a standard parameterized implementation,
 * take a look at the {@link ParameterizedProperty} class.
 *
 * @see pw.stamina.occasum.properties.primitives.IntProperty
 * @see pw.stamina.occasum.properties.primitives.DoubleProperty
 * @see pw.stamina.occasum.properties.primitives.BooleanProperty
 * @see pw.stamina.occasum.properties.enums.EnumProperty
 * @see pw.stamina.occasum.properties.selective.SelectiveProperty
 * @see pw.stamina.occasum.properties.collection.SetProperty
 * @see pw.stamina.occasum.properties.collection.ListProperty
 */
public interface Property extends Named {

    void parseAndSet(String input) throws PropertyParseException;

    boolean isDefault();

    void reset();

    String getValueAsString();

    String getDefaultValueAsString();
}
