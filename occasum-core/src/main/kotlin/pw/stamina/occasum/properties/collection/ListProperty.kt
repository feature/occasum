package pw.stamina.occasum.properties.collection

import java.util.*

class ListProperty<T> internal constructor(
        name: String,
        elements: List<T>,
        adapter: CollectionPropertyAdapter<T>)
    : AbstractCollectionProperty<T, List<T>>(name, elements, adapter) {

    override val elements = elements
        get() = Collections.unmodifiableList(field)

    companion object {

        fun <T> array(name: String, adapter: CollectionPropertyAdapter<T>): ListProperty<T> {
            return ListProperty(name, ArrayList(), adapter)
        }
    }
}
