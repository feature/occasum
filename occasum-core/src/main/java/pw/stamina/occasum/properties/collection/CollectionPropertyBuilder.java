package pw.stamina.occasum.properties.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class CollectionPropertyBuilder<T> {
    private final String name;
    private Collection<T> defaultElements;
    private CollectionPropertyAdapter<T> adapter;

    CollectionPropertyBuilder(String name) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        this.defaultElements = new ArrayList<>();
    }

    public CollectionPropertyBuilder<T> add(T element) {
        Objects.requireNonNull(element, "element");
        defaultElements.add(element);
        return this;
    }

    @SafeVarargs
    public final CollectionPropertyBuilder<T> addAll(T... elements) {
        Objects.requireNonNull(elements, "elements");

        if (elements.length == 0) {
            throw new IllegalArgumentException("elements is empty");
        }

        for (T element : elements) {
            add(element);
        }

        return this;
    }

    public CollectionPropertyBuilder<T> addAll(Iterable<T> elements) {
        Objects.requireNonNull(elements, "elements");
        elements.forEach(this::add);
        return this;
    }

    public CollectionPropertyBuilder<T> adapter(CollectionPropertyAdapter<T> adapter) {
        Objects.requireNonNull(adapter, "adapter");
        this.adapter = adapter;
        return this;
    }

    public SetProperty<T> buildSet(Supplier<Set<T>> setSupplier) {
        Objects.requireNonNull(setSupplier, "setSupplier");
        requireAdapter();

        Set<T> elements = setSupplier.get();
        addDefaultElementsToCollection(elements);
        return new SetProperty<>(name, elements, adapter);
    }

    public ListProperty<T> buildList(Supplier<List<T>> listSupplier) {
        Objects.requireNonNull(listSupplier, "listSupplier");
        requireAdapter();

        List<T> elements = listSupplier.get();
        addDefaultElementsToCollection(elements);
        return new ListProperty<>(name, elements, adapter);
    }

    public SetProperty<T> buildHashSet() {
        return buildSet(HashSet::new);
    }

    public SetProperty<T> buildConcurrentHashSet() {
        return buildSet(ConcurrentHashMap::newKeySet);
    }

    public ListProperty<T> buildArrayList() {
        return buildList(ArrayList::new);
    }

    private void requireAdapter() {
        if (adapter == null) {
            throw new IllegalStateException("no adapter has been set");
        }
    }

    private void addDefaultElementsToCollection(Collection<T> elements) {
        if (defaultElements != null) {
            elements.addAll(defaultElements);
        }
    }
}
