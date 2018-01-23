package pw.stamina.occasum.properties.collection;

import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class ListPropertyTests<T> extends AbstractCollectionPropertyTests<T> {

    @Spy
    private List<T> elements = new ArrayList<>();

    @Override
    Collection<T> provideElements() {
        return elements;
    }

    @Override
    CollectionProperty<T> provideProperty() {
        return new ListProperty<>("list", elements, adapter);
    }
}
