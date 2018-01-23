package pw.stamina.occasum.properties.collection;

import org.mockito.Spy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

final class SetPropertyTests<T> extends AbstractCollectionPropertyTests<T> {

    @Spy
    private Set<T> elements = new HashSet<>();

    @Override
    Collection<T> provideElements() {
        return elements;
    }

    @Override
    CollectionProperty<T> provideProperty() {
        return new SetProperty<>("list", elements, adapter);
    }
}
