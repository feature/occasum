package pw.stamina.occasum.scan.result

import pw.stamina.occasum.node.PropertyNode

interface PropertyScanResult {

    val exceptions: Iterable<Exception>

    fun attach(node: PropertyNode)
}
