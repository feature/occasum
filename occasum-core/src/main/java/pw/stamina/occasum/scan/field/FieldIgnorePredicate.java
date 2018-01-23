package pw.stamina.occasum.scan.field;

import pw.stamina.occasum.scan.field.model.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Predicate;

public interface FieldIgnorePredicate extends Predicate<Field> {

    boolean shouldIgnoreField(Field field);

    @Override
    default FieldIgnorePredicate or(Predicate<? super Field> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    @Override
    default boolean test(Field field) {
        return shouldIgnoreField(field);
    }

    static FieldIgnorePredicate standard() {
        return ifIgnored().or(ifStatic()).or(ifNotFinal());
    }

    static FieldIgnorePredicate ifStatic() {
        return field -> Modifier.isStatic(field.getModifiers());
    }

    static FieldIgnorePredicate ifNotFinal() {
        return field -> !Modifier.isFinal(field.getModifiers());
    }

    static FieldIgnorePredicate ifIgnored() {
        return field -> field.isAnnotationPresent(Ignore.class);
    }
}
