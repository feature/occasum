package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.PropertyTests
import java.util.*

internal class DoublePropertyTests : PropertyTests<DoubleProperty>() {

    override fun provideProperty(): DoubleProperty {
        return DoubleProperty("test", 10.0)
    }

    override fun shufflePropertyValue(property: DoubleProperty, random: Random) {
        property.value = random.nextDouble()
    }
}