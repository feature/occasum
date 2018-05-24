package pw.stamina.occasum.properties.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pw.stamina.occasum.properties.PropertyParseException
import pw.stamina.occasum.properties.PropertyTests
import java.util.*

internal class BooleanPropertyTests : PropertyTests<BooleanProperty>() {


    @ParameterizedTest
    @CsvSource("true, true", "false, false", "foo, false", "bar, false")
    @Throws(PropertyParseException::class)
    fun parseAndSet_validStringInput_shouldParseAndSetExpectedValue(input: String, expected: Boolean) {
        property.parseAndSet(input)
        val parsedValue = property.value

        assertEquals(expected, parsedValue)
    }

    override fun provideProperty(): BooleanProperty {
        return BooleanProperty("dummy", false)
    }

    override fun shufflePropertyValue(property: BooleanProperty, random: Random) {
        property.value = random.nextBoolean()
    }
}