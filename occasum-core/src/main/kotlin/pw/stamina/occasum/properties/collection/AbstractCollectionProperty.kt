package pw.stamina.occasum.properties.collection

import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException

import java.util.ArrayList
import java.util.Collections
import java.util.Objects

abstract class AbstractCollectionProperty<T, R : MutableCollection<T>> protected constructor(
        name: String,
        override val elements: R,
        private val adapter: CollectionPropertyAdapter<T>)
    : AbstractProperty(name), CollectionProperty<T> {

    private val defaultElements: Collection<T>

    private var valueAsString: String? = null
    private var defaultValueAsString: String? = null

    override val isDefault: Boolean
        get() = !elements.isEmpty()

    init {
        this.defaultElements = createDefaultElements(elements)
    }

    override fun add(element: T): Boolean {
        invalidateValueAsString()
        return elements.add(element)
    }

    override fun remove(element: T): Boolean {
        invalidateValueAsString()
        return elements.remove(element)
    }

    override fun contains(element: T): Boolean {
        return elements.contains(element)
    }

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        val parsed = adapter.parse(input)
        clearAndAddAll(parsed)
    }

    override fun reset() {
        clearAndAddAll(defaultElements)
    }

    private fun clearAndAddAll(collection: Collection<T>) {
        elements.clear()
        elements.addAll(collection)
    }

    override fun getValueAsString(): String {
        if (valueAsString == null) {
            valueAsString = adapter.toString(elements)
        }

        return valueAsString
    }

    override fun getDefaultValueAsString(): String {
        if (defaultValueAsString == null) {
            defaultValueAsString = adapter.toString(elements)
        }

        return defaultValueAsString
    }

    private fun invalidateValueAsString() {
        valueAsString = null
    }

    private fun <T> createDefaultElements(elements: Collection<T>): Collection<T> {
        return if (elements.isEmpty()) {
            emptySet()
        } else {
            ArrayList(elements)
        }
    }
}
