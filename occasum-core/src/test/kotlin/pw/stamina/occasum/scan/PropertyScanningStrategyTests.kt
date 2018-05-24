package pw.stamina.occasum.scan

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import pw.stamina.occasum.scan.field.FieldPropertyScanningStrategy

internal class PropertyScanningStrategyTests {

    @Test
    @DisplayName("FieldPropertyScanningStrategy.standard() == PropertyScanningStrategy.field()")
    fun field_shouldReturnStandardFieldPropertyScanningStrategy() {
        assertSame(FieldPropertyScanningStrategy.standard(), PropertyScanningStrategy.field())
    }
}
