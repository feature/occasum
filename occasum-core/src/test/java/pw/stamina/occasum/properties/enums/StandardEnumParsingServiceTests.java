package pw.stamina.occasum.properties.enums;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import pw.stamina.occasum.properties.PropertyParseException;

import static org.junit.jupiter.api.Assertions.*;

final class StandardEnumParsingServiceTests {

    private static StandardEnumParsingService<DummyEnum> parsingService;

    @BeforeAll
    static void setupStandardParsingService() {
        parsingService = new StandardEnumParsingService<>(DummyEnum.class);
    }

    @ParameterizedTest(name = "[{index}] T = {0}")
    @DisplayName("parse(T.name()) -> T")
    @EnumSource(DummyEnum.class)
    void parse_givenValidEnumNameFromEnumValue_shouldReturnEnumMatchingName(DummyEnum dummy) throws PropertyParseException {
        String name = dummy.name();
        DummyEnum parsed = parsingService.parse(name);

        assertSame(dummy, parsed);
    }

    @ParameterizedTest(name = "[{index}] name = {0}")
    @DisplayName("parse(name) -> T")
    @ValueSource(strings = {"FOO", "BAR", "FOO_BAR"})
    void parse_givenValidEnumStringName_shouldReturnEnumMatchingName(String name) throws PropertyParseException {
        DummyEnum parsed = parsingService.parse(name);

        assertNotNull(parsed);
        assertTrue(parsed.name().equalsIgnoreCase(name));
    }

    @Test
    @DisplayName("parse(null) -> throws NullPointerException")
    void parse_givenNullInput_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> parsingService.parse(null));
    }

    @ParameterizedTest(name = "[{index}] name = {0}")
    @DisplayName("parse(name) -> null")
    @ValueSource(strings = {"foo", "bar", "unrecognized"})
    void parse_givenUnrecognizedInput_shouldThrowIllegalArgumentException(String name) throws PropertyParseException {
        DummyEnum parsed = parsingService.parse(name);
        assertNull(parsed);
    }

    @ParameterizedTest(name = "[{index}] T = {0}")
    @DisplayName("toString(T.name()) -> \"T\"")
    @EnumSource(DummyEnum.class)
    void toString_givenEnumValue_shouldReturnNameOfEnum(DummyEnum dummy) {
        String name = dummy.name();
        String toString = parsingService.toString(dummy);

        assertEquals(name, toString);
    }

    @Test
    @DisplayName("toString(null) -> \"null\"")
    void toString_givenNull_shouldReturnNullString() {
        assertEquals("null", parsingService.toString(null));
    }

    @Test
    @DisplayName("EnumParsingService.standard(null) throws NullPointerException")
    void standard_givenNullEnumClass_shouldThrownNullPointerException() {
        assertThrows(NullPointerException.class, () -> EnumParsingService.standard(null));
    }
}