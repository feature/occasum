package pw.stamina.occasum.properties.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import pw.stamina.occasum.properties.PropertyParseException

internal class StandardEnumParsingServiceTests {

    @ParameterizedTest(name = "[{index}] T = {0}")
    @DisplayName("parse(T.name) -> T")
    @EnumSource(DummyEnum::class)
    @Throws(PropertyParseException::class)
    fun parse_givenValidEnumNameFromEnumValue_shouldReturnEnumMatchingName(dummy: DummyEnum) {
        val name = dummy.name
        val parsed = parsingService.parse(name)

        assertSame(dummy, parsed)
    }

    @ParameterizedTest(name = "[{index}] name = {0}")
    @DisplayName("parse(name) -> T")
    @ValueSource(strings = ["FOO", "BAR", "FOO_BAR"])
    fun parse_givenValidEnumStringName_shouldReturnEnumMatchingName(name: String) {
        val parsed = parsingService.parse(name)

        assertNotNull(parsed)
        assertTrue(parsed.name.equals(name, ignoreCase = true))
    }

    @ParameterizedTest(name = "[{index}] name = {0}")
    @DisplayName("parse(name) -> null")
    @ValueSource(strings = ["foo", "bar", "unrecognized"])
    fun parse_givenUnrecognizedInput_shouldThrowIllegalArgumentException(name: String) {
        val parsed = parsingService.parse(name)
        assertNull(parsed)
    }

    @ParameterizedTest(name = "[{index}] T = {0}")
    @DisplayName("toString(T.name()) -> \"T\"")
    @EnumSource(DummyEnum::class)
    fun toString_givenEnumValue_shouldReturnNameOfEnum(dummy: DummyEnum) {
        val name = dummy.name
        val toString = parsingService.toString(dummy)

        assertEquals(name, toString)
    }

    companion object {

        private lateinit var parsingService: StandardEnumParsingService<DummyEnum>

        @BeforeAll
        fun setupStandardParsingService() {
            parsingService = StandardEnumParsingService(DummyEnum::class.java)
        }
    }
}