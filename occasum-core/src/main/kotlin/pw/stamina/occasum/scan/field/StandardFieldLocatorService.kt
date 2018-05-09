package pw.stamina.occasum.scan.field

import java.lang.reflect.Field
import java.util.*

class StandardFieldLocatorService : FieldLocatorService {

    override fun findAllFields(source: Any, ignorePredicate: FieldIgnorePredicate): List<Field> {
        val fields = findAllFields(source.javaClass)

        fields.removeIf(ignorePredicate)
        fields.forEach { this.ensureAccessibility(it) }

        return fields
    }

    private fun findAllFields(type: Class<*>?): MutableList<Field> {
        var type = type
        val fields = ArrayList<Field>()

        while (type != null) {
            fields.addAll(Arrays.asList(*type.declaredFields))
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
