package pw.stamina.occasum.properties.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListProperty<T> extends AbstractCollectionProperty<T, List<T>> {

    ListProperty(String name, List<T> elements, CollectionPropertyAdapter<T> adapter) {
        super(name, elements, adapter);
    }

    @Override
    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public static <T> ListProperty<T> array(String name, CollectionPropertyAdapter<T> adapter) {
        return new ListProperty<>(name, new ArrayList<>(), adapter);
    }
}
