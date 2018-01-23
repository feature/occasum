package pw.stamina.occasum.properties.selective;

import pw.stamina.occasum.Named;
import pw.stamina.occasum.properties.ParameterizedProperty;
import pw.stamina.occasum.properties.PropertyParseException;

import java.util.*;

/**
 * A {@link ParameterizedProperty} that with a set of selectable
 * {@link Named named} options. Options names are case insensitive.
 *
 * @param <T> the type of {@link Named} options
 *
 * @see SelectiveProperty#builder(String)
 */
//TODO: Choose between using Named#getName and Named#getId
public final class SelectiveProperty<T extends Named> extends ParameterizedProperty<T> {
    private final NavigableMap<String, T> options = createNewOptionsMap();;

    SelectiveProperty(String name, T value) {
        super(name, value);
        addOption(value);
    }

    /**
     * Changes the currently selected value for this property to the specified
     * {@code value}. If the specified {@code value} is not null and not a
     * registered option, an {@link IllegalArgumentException} is thrown.
     *
     * @param value the new value to set for this property
     * @throws IllegalArgumentException if the specified {@code value} is not
     * null and is not a registered option
     */
    @Override
    public void set(T value) throws IllegalArgumentException {
        if (!options.containsValue(value)) {
            throw new IllegalArgumentException("the specified value is not a registered option");
        }

        super.set(value);
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        Objects.requireNonNull(input, "input");

        T found = options.get(input);

        if (found != null) {
            set(found);
            return;
        }

        throw new PropertyParseException("could not find an option matching the specified input", input);
    }

    @Override
    protected String valueToString(T value) {
        return value.getName();
    }

    /**
     * Adds the specified {@code option} to the set of available {@code options}
     * for this property. This affects the objects that can be accepted by the
     * {@link SelectiveProperty#set(Named) set} method, and the options that can
     * be parsed by the {@link SelectiveProperty#parseAndSet(String) parseAndSet}
     * method. If the name pf the specified {@code option} is already registered
     * to another option, an {@link IllegalArgumentException} is thrown.
     *
     * @param option the option to add to this property
     * @throws IllegalArgumentException if another option with the same
     * name as the specified {@code option} is already registered
     */
    public void addOption(T option) throws IllegalArgumentException {
        Objects.requireNonNull(option, "option");

        String key = option.getName();

        if (options.containsKey(key)) {
            throw new IllegalArgumentException(
                    "an option with the specified name is already registered. Name: " + key);
        }

        options.put(key, option);
    }

    /**
     * Convenience method for adding multiple options at once. This method may
     * throw an {@link IllegalArgumentException} as described by the
     * {@link SelectiveProperty#addOption(Named)} method.<p>
     *
     * This method is implemented as if:
     * <pre> {@code
     * options.forEach(this::addOption);
     * }</pre>
     *
     *
     * @param options the options to add to this property
     * @throws IllegalArgumentException if the {@link SelectiveProperty#addOption(Named)}
     * throws an exception while adding an option from the specified {@code options}
     */
    public void addAll(Iterable<T> options) throws IllegalArgumentException {
        Objects.requireNonNull(options, "options");
        options.forEach(this::addOption);
    }

    public void removeOption(T option) throws IllegalArgumentException {
        Objects.requireNonNull(option, "option");

        if (option == defaultValue) {
            throw new IllegalArgumentException(
                    "the default value specified for this property cannot be removed as an option");
        }

        removeOptionAndResetIfSelectedWasRemoved(option);
    }

    private void removeOptionAndResetIfSelectedWasRemoved(T option) {
        boolean removed = options.values().remove(option);

        //TODO: Fix this
        if (removed && wasSelectedRemoved(option)) {
            return;
        }
    }

    private boolean wasSelectedRemoved(T removed) {
        T selected = get();
        return selected == removed;
    }

    private static <T extends Named> NavigableMap<String, T> createNewOptionsMap() {
        return new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public Collection<T> getOptions() {
        return Collections.unmodifiableCollection(options.values());
    }

    public static <T extends Named> SelectiveProperty<T> from(String name, T selected) {
        return SelectiveProperty.<T>builder(name).selected(selected).build();
    }

    public static <T extends Named> SelectivePropertyBuilder<T> builder(String name) {
        return new SelectivePropertyBuilder<>(name);
    }
}
