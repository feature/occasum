package pw.stamina.occasum

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class NamedTests {

    @Test
    @DisplayName("createIdFromName(\"Example Name\") -> \"example_name\"")
    fun createIdFromName_givenInputWithSpace_shouldReturnLowercaseAndRReplaceSpaceWithUnderscore() {
        val name = "Example Name"
        val id = Named.createIdFromName(name)

        assertEquals("example_name", id)
    }

    @Test
    @DisplayName("createIdFromName(\"Example _ _ _ Name\") -> \"example_name\"")
    fun createIdFromName_givenInputWithAlternatingSpacesAndUnderscores_shouldReturnLowercaseAndReplaceAllSpacesAndUnderscoresWithSingleUnderscore() {
        val name = "Example _ _ _ Name"
        val id = Named.createIdFromName(name)

        assertEquals("example_name", id)
    }

    @Test
    @DisplayName("validateName(\"\") throws IllegalArgumentException")
    fun validateName_givenEmptyString_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException::class.java) { Named.validateName("") }
    }

    @Test
    @DisplayName("validateName(\".\") throws IllegalArgumentException")
    fun validateName_givenInputContainingPeriod_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException::class.java) { Named.validateName(".") }
    }

    @Test
    @DisplayName("validateName(\"should_pass\") should pass validation")
    fun validateName_givenNonNullNonEmptyStringContainingNoPeriods_shouldPassValidation() {
        Named.validateName("should_pass")
    }
}
