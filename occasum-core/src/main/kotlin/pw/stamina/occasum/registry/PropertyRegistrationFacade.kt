package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.properties.Property
import pw.stamina.occasum.scan.PropertyScanningStrategy
import javax.inject.Inject

class PropertyRegistrationFacade @Inject
constructor(private val registry: PropertyRegistry) {

    fun register(handle: PropertyHandle, property: Property<*>): PropertyNode {
        return registry.register(handle, property)
    }

    fun registerAll(handle: PropertyHandle, properties: Iterable<Property<*>>) {
        registry.registerAll(handle, properties)
    }

    fun registerWith(handle: PropertyHandle,
                     source: Any,
                     scanningStrategy: PropertyScanningStrategy) {
        val scan = scanningStrategy.scan(handle, source)
        val rootNode = registry.findOrCreateRootNode(handle)
        scan.attach(rootNode)

        //registerWithQuery(RegisterWithQuery.builder(handle)
        //        .source(source)
        //        .registerToHandleRoot()
        //        .scanningStrategy(scanningStrategy)
        //        .build());
    }

    fun registerWithQuery(query: RegisterWithQuery) {
        val result = query.result
        val node = query.nodeExtractor(registry)

        result.attach(node)
    }
}
