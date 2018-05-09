package pw.stamina.occasum.properties.collection

import pw.stamina.occasum.properties.Property

interface CollectionProperty<T> : Property {

    val elements: Collection<T>

    //TODO: String based add and remove methods

    fun add(element: T): Boolean

    fun remove(element: T): Boolean

    operator fun contains(element: T): Boolean

    companion object {

        fun <T> builder(name: String): CollectionPropertyBuilder<T> {
            return CollectionPropertyBuilder(name)
        }
    }
}
