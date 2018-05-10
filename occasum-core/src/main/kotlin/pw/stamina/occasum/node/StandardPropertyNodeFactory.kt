package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.AbstractPropertyNodeFactory
import pw.stamina.occasum.properties.Property

import javax.inject.Inject

class StandardPropertyNodeFactory @Inject internal constructor() : AbstractPropertyNodeFactory() {

    override fun createRoot(handle: PropertyHandle): PropertyNode {
        return RootPropertyNode(this, handle)
    }

    override fun createProperty(handle: PropertyHandle, parent: PropertyNode, property: Property): PropertyNode {
        return SimplePropertyNode(this, handle, parent, property)
    }

    override fun createFolder(handle: PropertyHandle, parent: PropertyNode, name: String): PropertyNode {
        return FolderPropertyNode(this, handle, parent, name)
    }

    companion object {

        fun newInstance(): StandardPropertyNodeFactory {
            return StandardPropertyNodeFactory()
        }
    }
}
