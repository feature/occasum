package pw.stamina.occasum.scan

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.registry.PropertyRegistrationFacade
import pw.stamina.occasum.scan.field.FieldPropertyScanningStrategy
import pw.stamina.occasum.scan.result.PropertyScanResult

/**
 * @see FieldPropertyScanningStrategy
 *
 * @see PropertyRegistrationFacade.registerWith
 */
interface PropertyScanningStrategy {

    fun scan(handle: PropertyHandle, source: Any): PropertyScanResult

    companion object {

        fun field(): PropertyScanningStrategy {
            return FieldPropertyScanningStrategy.standard()
        }
    }
}
