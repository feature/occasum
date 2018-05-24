package pw.stamina.occasum.node.factory

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.StandardPropertyNodeFactory
import pw.stamina.occasum.node.factory.inspect.PropertyNodeFactoryParameterInspector
import pw.stamina.occasum.properties.Property

interface PropertyNodeFactory {

    fun root(handle: PropertyHandle): PropertyNode

    fun property(handle: PropertyHandle,
                 parent: PropertyNode,
                 property: Property<*>): PropertyNode

    fun folder(handle: PropertyHandle,
               parent: PropertyNode,
               name: String): PropertyNode

    fun notifyRemoval(node: PropertyNode)

    fun addInspector(inspector: PropertyNodeFactoryParameterInspector)

    companion object {

        fun standard(): PropertyNodeFactory {
            val factory = StandardPropertyNodeFactory.newInstance()

            PropertyNodeFactoryParameterInspector.addStandardInspectors(factory)

            return factory
        }
    }
}
