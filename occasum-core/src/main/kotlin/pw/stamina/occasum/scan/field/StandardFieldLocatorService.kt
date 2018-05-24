package pw.stamina.occasum.scan.field

import java.lang.reflect.Field
import java.util.*

class StandardFieldLocatorService : FieldLocatorService {

    override fun findAllFields(source: Any, ignorePredicate: FieldIgnorePredicate): Collection<Field> {
        val fields = findAllFields(source.javaClass)

        val filteredFields = fields.filter(ignorePredicate)

        filteredFields.forEach(this::ensureAccessibility)

        return fields
    }

    private fun findAllFields(type: Class<*>): Collection<Field> {
        val fields = ArrayList<Field>()
        var type: Class<*>? = type

        while (type != null) {
            fields.addAll(type.declaredFields.toList())
            type = type.superclass
        }

        return fields
    }

    private fun ensureAccessibility(field: Field) {
        if (!field.isAccessible) {
            field.isAccessible = true
        }
    }
}
