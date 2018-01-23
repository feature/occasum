package pw.stamina.occasum.properties.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class SetProperty<T> extends AbstractCollectionProperty<T, Set<T>> {

    SetProperty(String name, Set<T> elements, CollectionPropertyAdapter<T> adapter) {
        super(name, elements, adapter);
    }

    @Override
    public Set<T> getElements() {
        return Collections.unmodifiableSet(elements);
    }

    public static <T> SetProperty<T> hash(String name, CollectionPropertyAdapter<T> adapter) {
        return new SetProperty<>(name, new HashSet<>(), adapter);
    }

    public static <T> SetProperty<T> concurrentHash(String name, CollectionPropertyAdapter<T> adapter) {
        return new SetProperty<>(name, ConcurrentHashMap.newKeySet(), adapter);
    }
}
