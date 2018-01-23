package pw.stamina.occasum.properties;

import java.util.Iterator;
import java.util.List;

public interface PropertiesContainer extends Iterable<Property> {

    List<Property> getProperties();

    @Override
    default Iterator<Property> iterator() {
        return getProperties().iterator();
    }
}
