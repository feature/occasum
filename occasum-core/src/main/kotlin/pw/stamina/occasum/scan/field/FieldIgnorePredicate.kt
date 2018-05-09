package pw.stamina.occasum.scan.field

import pw.stamina.occasum.scan.field.model.Ignore
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.function.Predicate

interface FieldIgnorePredicate : Predicate<Field> {

    fun shouldIgnoreField(field: Field): Boolean

    override infix fun or(other: Predicate<in Field>): FieldIgnorePredicate {
        return { test(it) || other.test(it) }
    }

    override fun test(field: Field): Boolean {
        return shouldIgnoreField(field)
    }

    companion object {

        fun standard(): FieldIgnorePredicate {
            return ifIgnored() or ifStatic() or ifNotFinal()
        }

        fun ifStatic(): Predicate<Field> {
            return { field -> Modifier.isStatic(field.getModifiers()) }
        }

        fun ifNotFinal(): FieldIgnorePredicate {
            return { field -> !Modifier.isFinal(field.getModifiers()) }
        }

        fun ifIgnored(): FieldIgnorePredicate {
            return { field -> field.isAnnotationPresent(Ignore::class.java) }
        }
    }
}
