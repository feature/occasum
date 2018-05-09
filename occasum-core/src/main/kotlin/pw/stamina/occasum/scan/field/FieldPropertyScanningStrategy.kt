package pw.stamina.occasum.scan.field

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.instructions.PropertyNodeInstructionList
import pw.stamina.occasum.properties.PropertiesContainer
import pw.stamina.occasum.properties.Property
import pw.stamina.occasum.scan.PropertyScanningStrategy
import pw.stamina.occasum.scan.field.model.Parent
import pw.stamina.occasum.scan.field.model.Properties
import pw.stamina.occasum.scan.result.InstructionBasedPropertyScanResult
import pw.stamina.occasum.scan.result.PropertyScanResult
import java.util.function.Consumer

//TODO: @Extract - pulls properties out of a nested scan
//TODO: @Export - exports properties to the

//TODO: Clean this up
//TODO: Make this this works after Kotlin port !!
class FieldPropertyScanningStrategy private constructor(
        private val fieldLocatorService: FieldLocatorService,
        private val ignorePredicate: FieldIgnorePredicate)
    : PropertyScanningStrategy {

    override fun scan(handle: PropertyHandle, source: Any): PropertyScanResult {
        val instructions = PropertyNodeInstructionList.newStandard()

        scanAndPopulate(source, instructions)

        return InstructionBasedPropertyScanResult(instructions, emptyList())
    }

    private fun scanAndPopulate(source: Any,
                                instructions: PropertyNodeInstructionList) {
        val fields = fieldLocatorService.findAllFields(source, ignorePredicate)

        fields.forEach { field ->
            try {
                val value = field.get(source) ?: return@forEach

                if (Property::class.java.isAssignableFrom(field.type)) {
                    val property = value as Property
                    var registeringTo = instructions

                    if (field.isAnnotationPresent(Parent::class.java)) {
                        val parent = field.getAnnotation(Parent::class.java)
                        registeringTo = findOrCreateFolder(registeringTo, parent.value)
                    }

                    registeringTo.property(property)
                }

                if (field.isAnnotationPresent(Properties::class.java)) {
                    val properties = field.getAnnotation(Properties::class.java)
                    var registeringTo = instructions

                    val path = properties.value
                    if (!path.isEmpty()) {
                        registeringTo = findOrCreateFolder(registeringTo, path)
                    }

                    if (value is PropertiesContainer) {
                        value.forEach(Consumer<Property> { registeringTo.property(it) })
                    } else {
                        scanAndPopulate(value, registeringTo)
                    }
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun findOrCreateFolder(instructions: PropertyNodeInstructionList, path: String): PropertyNodeInstructionList {
        var instructions = instructions
        val pathSegments = path.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (pathSegment in pathSegments) {
            instructions = instructions.folder(pathSegment)
        }

        return instructions
    }

    companion object {
        private val STANDARD = FieldPropertyScanningStrategy(
                FieldLocatorService.standard(),
                FieldIgnorePredicate.standard())

        fun standard(): PropertyScanningStrategy {
            return STANDARD
        }
    }
}
