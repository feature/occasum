package pw.stamina.occasum.properties.collection

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SetProperty<T> internal constructor(
        name: String,
        elements: Set<T>,
        adapter: CollectionPropertyAdapter<T>)
    : AbstractCollectionProperty<T, Set<T>>(name, elements, adapter) {

    override val elements = elements
        get() = Collections.unmodifiableSet(field)

    companion object {

        fun <T> hash(name: String, adapter: CollectionPropertyAdapter<T>): SetProperty<T> {
            return SetProperty(name, HashSet(), adapter)
        }

        fun <T> concurrentHash(name: String, adapter: CollectionPropertyAdapter<T>): SetProperty<T> {
            return SetProperty(name, ConcurrentHashMap.newKeySet(), adapter)
        }
    }
}
