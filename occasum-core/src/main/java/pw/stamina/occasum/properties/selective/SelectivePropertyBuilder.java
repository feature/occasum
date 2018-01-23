package pw.stamina.occasum.properties.selective;

import pw.stamina.occasum.Named;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class SelectivePropertyBuilder<T extends Named> {

    private final String name;
    private final Set<T> options;
    private T selected;

    SelectivePropertyBuilder(String name) {
        this.name = name;
        this.options = new HashSet<>();
    }

    /**
     * Sets the specified {@code selected} as the default value for the
     * {@link SelectiveProperty} when the {@link SelectivePropertyBuilder#build()} method
     * is called.
     *
     * @param selected
     * @return this builder
     * @throws NullPointerException if the {@code selected} value is null
     */
    public SelectivePropertyBuilder<T> selected(T selected) {
        Objects.requireNonNull(selected, "selected");
        this.selected = selected;
        return this;
    }

    /**
     *
     * @param option
     * @return
     * @throws NullPointerException if the specified {@code option} is null
     */
    public SelectivePropertyBuilder<T> option(T option) {
        Objects.requireNonNull(option, "option");
        options.add(option);
        return this;
    }

    /**
     * Creates a new {@link SelectiveProperty} with the configuration
     * specified by the builder. The options specified by the
     * {@link SelectivePropertyBuilder#option(Named)} is added to the property after it
     * has created.
     *
     * @return a new {@link SelectiveProperty} with the specified
     * configuration and options
     * @throws IllegalStateException if the builder isn't satisfied
     */
    public SelectiveProperty<T> build() {
        Objects.requireNonNull(selected, "selected has not been set");

        SelectiveProperty<T> selectiveProperty = new SelectiveProperty<>(name, selected);

        options.forEach(selectiveProperty::addOption);

        return selectiveProperty;
    }
}
