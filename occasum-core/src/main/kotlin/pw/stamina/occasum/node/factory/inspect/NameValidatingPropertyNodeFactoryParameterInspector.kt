package pw.stamina.occasum.node.factory.inspect

import pw.stamina.occasum.Named
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.dao.PropertyDao
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.properties.Property

internal class NameValidatingPropertyNodeFactoryParameterInspector : PropertyNodeFactoryParameterInspector {

    override fun inspectRoot(handle: PropertyHandle) {
        validatePropertyNodeName(handle.name)
    }

    override fun inspectProperty(handle: PropertyHandle, parent: PropertyNode, property: Property<*>) {
        validatePropertyNodeName(property.name)
    }

    override fun inspectFolder(handle: PropertyHandle, parent: PropertyNode, name: String) {
        validatePropertyNodeName(name)
    }

    @Throws(IllegalArgumentException::class)
    private fun validatePropertyNodeName(name: String) {
        Named.validateName(name)

        if (PropertyDao.RESERVED_SERIALIZED_VALUE_NAME == name) {
            throw IllegalArgumentException("the name 'value' is reserved for serialization purposes")
        }
    }
}
