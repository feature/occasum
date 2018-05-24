package pw.stamina.occasum.properties

interface PropertiesContainer : Iterable<Property<*>> {

    val properties: List<Property<*>>

    override fun iterator(): Iterator<Property<*>> {
        return properties.iterator()
    }
}
