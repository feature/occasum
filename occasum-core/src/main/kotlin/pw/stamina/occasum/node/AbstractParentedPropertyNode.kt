package pw.stamina.occasum.node

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory

internal abstract class AbstractParentedPropertyNode(
        factory: PropertyNodeFactory,
        handle: PropertyHandle,
        override val parent: PropertyNode
) : AbstractPropertyNode(factory, handle)
