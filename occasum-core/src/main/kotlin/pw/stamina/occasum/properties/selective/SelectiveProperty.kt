package pw.stamina.occasum.properties.selective

import pw.stamina.occasum.Named
import pw.stamina.occasum.properties.AbstractProperty
import pw.stamina.occasum.properties.PropertyParseException
import java.util.*

/**
 * A [ParameterizedProperty] that with a set of selectable
 * [named][Named] options. Options names are case insensitive.
 *
 * @param <T> the type of [Named] options
</T> */
//TODO: Choose between using Named#getName and Named#getId
class SelectiveProperty<T : Named> (
        name: String,
        value: T,
        additionalOptions: Iterable<T> = emptyList()
) : AbstractProperty<T>(name) {

    override var value: T = value
        set(value) {
            if (!options.containsValue(value)) {
                throw IllegalArgumentException("the specified value is not a registered option")
            }

            field = value
        }

    override val default: T = value

    private val options = createNewOptionsMap<T>()

    init {
        addOption(value)
        addAll(additionalOptions)
    }


    /* TODO: Documentation for the set method
     * Changes the currently selected value for this property to the specified
     * `value`. If the specified `value` is not null and not a
     * registered option, an [IllegalArgumentException] is thrown.
     *
     * @param value the new value to set for this property
     * @throws IllegalArgumentException if the specified `value` is not
     * null and is not a registered option
     */

    @Throws(PropertyParseException::class)
    override fun parseAndSet(input: String) {
        val found = options[input]

        if (found != null) {
            value = found
            return
        }

        throw PropertyParseException("could not find an option matching the specified input", input)
    }

    override fun valueToString(value: T): String {
        return value.name
    }

    /**
     * Adds the specified `option` to the set of available `options`
     * for this property. This affects the objects that can be accepted by the
     * [set][SelectiveProperty.set] method, and the options that can
     * be parsed by the [parseAndSet][SelectiveProperty.parseAndSet]
     * method. If the name pf the specified `option` is already registered
     * to another option, an [IllegalArgumentException] is thrown.
     *
     * @param option the option to add to this property
     * @throws IllegalArgumentException if another option with the same
     * name as the specified `option` is already registered
     */
    @Throws(IllegalArgumentException::class)
    fun addOption(option: T) {
        val key = option.name

        if (options.containsKey(key)) {
            throw IllegalArgumentException(
                    "an option with the specified name is already registered. Name: $key")
        }

        options[key] = option
    }

    /**
     * Convenience method for adding multiple options at once. This method may
     * throw an [IllegalArgumentException] as described by the
     * [SelectiveProperty.addOption] method.
     *
     *
     *
     * This method is implemented as if:
     * <pre> `options.forEach(this::addOption);
    `</pre> *
     *
     *
     * @param options the options to add to this property
     * @throws IllegalArgumentException if the [SelectiveProperty.addOption]
     * throws an exception while adding an option from the specified `options`
     */
    @Throws(IllegalArgumentException::class)
    fun addAll(options: Iterable<T>) = options.forEach(this::addOption)

    @Throws(IllegalArgumentException::class)
    fun removeOption(option: T) {
        require(option !== default) { "the default value specified for this property cannot be removed as an option" }
        removeOptionAndResetIfSelectedWasRemoved(option)
    }

    private fun removeOptionAndResetIfSelectedWasRemoved(option: T) {
        val removed = options.values.remove(option)

        //TODO: Fix this
        if (removed && wasSelectedRemoved(option)) {
            return
        }
    }

    private fun wasSelectedRemoved(removed: T): Boolean {
        return value === removed
    }

    fun getOptions(): Collection<T> {
        return Collections.unmodifiableCollection(options.values)
    }

    companion object {

        private fun <T : Named> createNewOptionsMap(): NavigableMap<String, T> {
            return TreeMap(String.CASE_INSENSITIVE_ORDER)
        }
    }
}
