package pw.stamina.occasum.properties.collection;

import pw.stamina.occasum.properties.PropertyParseException;

import java.util.Collection;

public interface CollectionPropertyAdapter<T> {

    Collection<T> parse(String input) throws PropertyParseException;

    String toString(Collection<T> elements);
}
