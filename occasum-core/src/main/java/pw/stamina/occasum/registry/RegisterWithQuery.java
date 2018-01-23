package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.scan.PropertyScanningStrategy;
import pw.stamina.occasum.scan.result.PropertyScanResult;

import java.util.function.Function;

public interface RegisterWithQuery {

    PropertyScanResult getResult();

    Function<PropertyRegistry, PropertyNode> getNodeExtractor();

    static Builder builder(PropertyHandle handle) {
        return null;
    }

    interface Builder {

        Builder handle(PropertyHandle handle);

        Builder source(Object source);

        Builder handleAsSource();

        Builder registerToHandleRoot();

        Builder registerToRootOf(PropertyHandle handle);

        Builder registerToNode(PropertyNode node);

        Builder scanningStrategy(PropertyScanningStrategy scanningStrategy);

        RegisterWithQuery build();
    }
}
