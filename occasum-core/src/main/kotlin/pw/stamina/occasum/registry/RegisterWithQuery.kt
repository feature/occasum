package pw.stamina.occasum.registry

import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.scan.PropertyScanningStrategy
import pw.stamina.occasum.scan.result.PropertyScanResult

import java.util.function.Function

interface RegisterWithQuery {

    val result: PropertyScanResult

    val nodeExtractor: Function<PropertyRegistry, PropertyNode>

    interface Builder {

        fun handle(handle: PropertyHandle): Builder

        fun source(source: Any): Builder

        fun handleAsSource(): Builder

        fun registerToHandleRoot(): Builder

        fun registerToRootOf(handle: PropertyHandle): Builder

        fun registerToNode(node: PropertyNode): Builder

        fun scanningStrategy(scanningStrategy: PropertyScanningStrategy): Builder

        fun build(): RegisterWithQuery
    }

    companion object {

        fun builder(handle: PropertyHandle): Builder? {
            return null
        }
    }
}
