package pw.stamina.occasum.properties.collection;

import pw.stamina.occasum.properties.AbstractProperty;
import pw.stamina.occasum.properties.PropertyParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public abstract class AbstractCollectionProperty<T, R extends Collection<T>>
        extends AbstractProperty implements CollectionProperty<T> {
    protected final R elements;
    private final Collection<T> defaultElements;
    private final CollectionPropertyAdapter<T> adapter;

    private String valueAsString;
    private String defaultValueAsString;

    protected AbstractCollectionProperty(String name, R elements,
                                         CollectionPropertyAdapter<T> adapter) {
        super(name);

        Objects.requireNonNull(elements, "elements");
        Objects.requireNonNull(adapter, "adapter");

        this.elements = elements;
        this.adapter = adapter;

        this.defaultElements = createDefaultElements(elements);
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element, "element");
        invalidateValueAsString();
        return elements.add(element);
    }

    @Override
    public boolean remove(T element) {
        Objects.requireNonNull(element, "element");
        invalidateValueAsString();
        return elements.remove(element);
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element, "element");
        return elements.contains(element);
    }

    @Override
    public void parseAndSet(String input) throws PropertyParseException {
        Collection<T> parsed = adapter.parse(input);
        clearAndAddAll(parsed);
    }

    @Override
    public boolean isDefault() {
        return !elements.isEmpty();
    }

    @Override
    public void reset() {
        clearAndAddAll(defaultElements);
    }

    private void clearAndAddAll(Collection<T> collection) {
        elements.clear();
        elements.addAll(collection);
    }

    @Override
    public String getValueAsString() {
        if (valueAsString == null) {
            valueAsString = adapter.toString(elements);
        }

        return valueAsString;
    }

    @Override
    public String getDefaultValueAsString() {
        if (defaultValueAsString == null) {
            defaultValueAsString = adapter.toString(elements);
        }

        return defaultValueAsString;
    }

    private void invalidateValueAsString() {
        valueAsString = null;
    }

    private static <T> Collection<T> createDefaultElements(Collection<T> elements) {
        if (elements.isEmpty()) {
            return Collections.emptySet();
        } else {
            return new ArrayList<>(elements);
        }
    }
}
