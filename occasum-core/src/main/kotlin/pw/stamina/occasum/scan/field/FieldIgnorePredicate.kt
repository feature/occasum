package pw.stamina.occasum.scan.field

import pw.stamina.occasum.scan.field.model.Ignore
import java.lang.reflect.Field
import java.lang.reflect.Modifier

typealias FieldIgnorePredicate = (Field) -> Boolean

object FieldIgnorePredicates {

    infix fun FieldIgnorePredicate.or(other: FieldIgnorePredicate): FieldIgnorePredicate {
        return { field -> this(field) || other(field) }
    }

    fun standard(): FieldIgnorePredicate {
        return ifIgnored() or ifStatic() or ifNotFinal()
    }

    fun ifStatic(): FieldIgnorePredicate {
        return { field -> Modifier.isStatic(field.modifiers) }
    }

    fun ifNotFinal(): FieldIgnorePredicate {
        return { field -> !Modifier.isFinal(field.modifiers) }
    }

    fun ifIgnored(): FieldIgnorePredicate {
        return { field -> field.isAnnotationPresent(Ignore::class.java) }
    }
}
