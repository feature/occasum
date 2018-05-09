package pw.stamina.occasum.properties.selective

import java.util.HashSet
import java.util.Objects

class SelectivePropertyBuilder<T : Named> internal constructor(private val name: String) {
    private val options: MutableSet<T>
    private var selected: T? = null

    init {
        this.options = HashSet()
    }

    /**
     * Sets the specified `selected` as the default value for the
     * [SelectiveProperty] when the [SelectivePropertyBuilder.build] method
     * is called.
     *
     * @param selected
     * @return this builder
     * @throws NullPointerException if the `selected` value is null
     */
    fun selected(selected: T): SelectivePropertyBuilder<T> {
        this.selected = selected
        return this
    }

    /**
     *
     * @param option
     * @return
     * @throws NullPointerException if the specified `option` is null
     */
    fun option(option: T): SelectivePropertyBuilder<T> {
        options.add(option)
        return this
    }

    /**
     * Creates a new [SelectiveProperty] with the configuration
     * specified by the builder. The options specified by the
     * [SelectivePropertyBuilder.option] is added to the property after it
     * has created.
     *
     * @return a new [SelectiveProperty] with the specified
     * configuration and options
     * @throws IllegalStateException if the builder isn't satisfied
     */
    fun build(): SelectiveProperty<T> {
        val selectiveProperty = SelectiveProperty<T>(name, selected)

        options.forEach(Consumer<T> { selectiveProperty.addOption(it) })

        return selectiveProperty
    }
}
