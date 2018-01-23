package pw.stamina.occasum.properties.collection;

import pw.stamina.occasum.properties.Property;

import java.util.Collection;

public interface CollectionProperty<T> extends Property {

    //TODO: String based add and remove methods

    boolean add(T element);

    boolean remove(T element);

    boolean contains(T element);

    Collection<T> getElements();

    static <T> CollectionPropertyBuilder<T> builder(String name) {
        return new CollectionPropertyBuilder<>(name);
    }
}
