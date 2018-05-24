package pw.stamina.occasum.node.factory.inspect

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

interface PropertyNodeFactoryParameterInspector {

    fun inspectRoot(handle: PropertyHandle) {}

    fun inspectProperty(handle: PropertyHandle,
                        parent: PropertyNode,
                        property: Property<*>) {
    }

    fun inspectFolder(handle: PropertyHandle,
                      parent: PropertyNode,
                      name: String) {
    }

    fun inspectNotifyRemoval(node: PropertyNode) {}

    companion object {

        fun validateName(): PropertyNodeFactoryParameterInspector {
            return NameValidatingPropertyNodeFactoryParameterInspector()
        }

        fun detectDuplicateProperties(): PropertyNodeFactoryParameterInspector {
            return DuplicatePropertyDetectingPropertyNodeFactoryParameterInspector()
        }

        fun addStandardInspectors(factory: PropertyNodeFactory) {
            factory.addInspector(validateName())
            factory.addInspector(detectDuplicateProperties())
        }
    }
}
