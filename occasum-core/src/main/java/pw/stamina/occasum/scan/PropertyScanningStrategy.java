package pw.stamina.occasum.scan;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.registry.PropertyRegistrationFacade;
import pw.stamina.occasum.scan.field.FieldPropertyScanningStrategy;
import pw.stamina.occasum.scan.result.PropertyScanResult;

/**
 * @see FieldPropertyScanningStrategy
 * @see PropertyRegistrationFacade#registerWith(PropertyHandle, Object, PropertyScanningStrategy)
 */
public interface PropertyScanningStrategy {

    PropertyScanResult scan(PropertyHandle handle, Object source);

    static PropertyScanningStrategy field() {
        return FieldPropertyScanningStrategy.standard();
    }
}
