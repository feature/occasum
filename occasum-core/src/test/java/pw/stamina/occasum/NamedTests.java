package pw.stamina.occasum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class NamedTests {

    @Test
    @DisplayName("createIdFromName(null) throws NullPointerException")
    void createIdFromName_givenNullInput_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> Named.createIdFromName(null));
    }

    @Test
    @DisplayName("createIdFromName(\"Example Name\") -> \"example_name\"")
    void createIdFromName_givenInputWithSpace_shouldReturnLowercaseAndRReplaceSpaceWithUnderscore() {
        String name = "Example Name";
        String id = Named.createIdFromName(name);

        assertEquals("example_name", id);
    }

    @Test
    @DisplayName("createIdFromName(\"Example _ _ _ Name\") -> \"example_name\"")
    void createIdFromName_givenInputWithAlternatingSpacesAndUnderscores_shouldReturnLowercaseAndReplaceAllSpacesAndUnderscoresWithSingleUnderscore() {
        String name = "Example _ _ _ Name";
        String id = Named.createIdFromName(name);

        assertEquals("example_name", id);
    }

    @Test
    @DisplayName("validateName(null) throws NullPointerException")
    void validateName_givenNullInput_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> Named.validateName(null));
    }

    @Test
    @DisplayName("validateName(\"\") throws IllegalArgumentException")
    void validateName_givenEmptyString_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Named.validateName(""));
    }

    @Test
    @DisplayName("validateName(\".\") throws IllegalArgumentException")
    void validateName_givenInputContainingPeriod_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Named.validateName("."));
    }

    @Test
    @DisplayName("validateName(\"should_pass\") should pass validation")
    void validateName_givenNonNullNonEmptyStringContainingNoPeriods_shouldPassValidation() {
        Named.validateName("should_pass");
    }
}
