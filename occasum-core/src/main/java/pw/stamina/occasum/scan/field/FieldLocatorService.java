package pw.stamina.occasum.scan.field;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldLocatorService {

    List<Field> findAllFields(Object source, FieldIgnorePredicate ignorePredicate);

    static FieldLocatorService standard() {
        return new StandardFieldLocatorService();
    }
}
