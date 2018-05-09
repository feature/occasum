package pw.stamina.occasum.properties.collection

import pw.stamina.occasum.properties.PropertyParseException

interface CollectionPropertyAdapter<T> {

    @Throws(PropertyParseException::class)
    fun parse(input: String): Collection<T>

    fun toString(elements: Collection<T>): String
}
