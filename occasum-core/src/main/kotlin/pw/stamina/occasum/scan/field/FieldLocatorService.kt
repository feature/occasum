package pw.stamina.occasum.scan.field

import java.lang.reflect.Field

interface FieldLocatorService {

    fun findAllFields(source: Any, ignorePredicate: FieldIgnorePredicate): List<Field>

    companion object {

        fun standard(): FieldLocatorService {
            return StandardFieldLocatorService()
        }
    }
}
