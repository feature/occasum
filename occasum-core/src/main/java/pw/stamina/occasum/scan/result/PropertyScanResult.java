package pw.stamina.occasum.scan.result;

import pw.stamina.occasum.node.PropertyNode;

public interface PropertyScanResult {

    void attach(PropertyNode node);

    Iterable<Exception> getExceptions();
}
