package pw.stamina.occasum.properties.collection

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

class CollectionPropertyBuilder<T> internal constructor(private val name: String) {
    private val defaultElements: MutableCollection<T>?
    private var adapter: CollectionPropertyAdapter<T>? = null

    init {
        this.defaultElements = ArrayList()
    }

    fun add(element: T): CollectionPropertyBuilder<T> {
        defaultElements!!.add(element)
        return this
    }

    @SafeVarargs
    fun addAll(vararg elements: T): CollectionPropertyBuilder<T> {
        if (elements.isEmpty()) {
            throw IllegalArgumentException("elements is empty")
        }

        for (element in elements) {
            add(element)
        }

        return this
    }

    fun addAll(elements: Iterable<T>): CollectionPropertyBuilder<T> {
        elements.forEach { this.add(it) }
        return this
    }

    fun adapter(adapter: CollectionPropertyAdapter<T>): CollectionPropertyBuilder<T> {
        this.adapter = adapter
        return this
    }

    fun buildSet(setSupplier: Supplier<Set<T>>): SetProperty<T> {
        requireAdapter()

        val elements = setSupplier.get()
        addDefaultElementsToCollection(elements)
        return SetProperty(name, elements, adapter)
    }

    fun buildList(listSupplier: Supplier<List<T>>): ListProperty<T> {
        requireAdapter()

        val elements = listSupplier.get()
        addDefaultElementsToCollection(elements)
        return ListProperty(name, elements, adapter)
    }

    fun buildHashSet(): SetProperty<T> {
        return buildSet(Supplier { HashSet() })
    }

    fun buildConcurrentHashSet(): SetProperty<T> {
        return buildSet(Supplier { ConcurrentHashMap.newKeySet() })
    }

    fun buildArrayList(): ListProperty<T> {
        return buildList(Supplier { ArrayList() })
    }

    private fun requireAdapter() {
        if (adapter == null) {
            throw IllegalStateException("no adapter has been set")
        }
    }

    private fun addDefaultElementsToCollection(elements: MutableCollection<T>) {
        if (defaultElements != null) {
            elements.addAll(defaultElements)
        }
    }
}
