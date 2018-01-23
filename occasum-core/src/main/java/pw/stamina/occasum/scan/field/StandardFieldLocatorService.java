package pw.stamina.occasum.scan.field;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StandardFieldLocatorService implements FieldLocatorService {

    @Override
    public List<Field> findAllFields(Object source, FieldIgnorePredicate ignorePredicate) {
        List<Field> fields = findAllFields(source.getClass());

        fields.removeIf(ignorePredicate);
        fields.forEach(this::ensureAccessibility);

        return fields;
    }

    private List<Field> findAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();

        for (; type != null; type = type.getSuperclass()) {
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
        }

        return fields;
    }

    private void ensureAccessibility(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }
}
