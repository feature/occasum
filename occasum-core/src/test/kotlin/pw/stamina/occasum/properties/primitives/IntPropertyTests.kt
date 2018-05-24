package pw.stamina.occasum.properties.primitives

import pw.stamina.occasum.properties.PropertyTests
import java.util.*

internal class IntPropertyTests : PropertyTests<IntProperty>() {

    override fun provideProperty(): IntProperty {
        return IntProperty("test", 5)
    }

    override fun shufflePropertyValue(property: IntProperty, random: Random) {
        property.value = random.nextInt(100)
    }
}