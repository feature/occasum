package pw.stamina.occasum.scan;

import org.junit.jupiter.api.Test;
import pw.stamina.occasum.scan.field.FieldPropertyScanningStrategy;

import static org.junit.jupiter.api.Assertions.assertSame;

final class PropertyScanningStrategyTests {

    @Test
    void field_shouldReturnStandardFieldPropertyScanningStrategy() {
        assertSame(FieldPropertyScanningStrategy.standard(), PropertyScanningStrategy.field());
    }
}
