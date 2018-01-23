package pw.stamina.occasum.registry;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;
import pw.stamina.occasum.scan.PropertyScanningStrategy;
import pw.stamina.occasum.scan.result.PropertyScanResult;

import javax.inject.Inject;
import java.util.Objects;

public final class PropertyRegistrationFacade {
    private final PropertyRegistry registry;

    @Inject
    public PropertyRegistrationFacade(PropertyRegistry registry) {
        Objects.requireNonNull(registry, "registry");
        this.registry = registry;
    }

    public PropertyNode register(PropertyHandle handle, Property property) {
        return registry.register(handle, property);
    }

    public void registerAll(PropertyHandle handle, Iterable<Property> properties) {
        registry.registerAll(handle, properties);
    }

    public void registerWith(PropertyHandle handle,
                             Object source,
                             PropertyScanningStrategy scanningStrategy) {
        PropertyScanResult scan = scanningStrategy.scan(handle, source);
        PropertyNode rootNode = registry.findOrCreateRootNode(handle);
        scan.attach(rootNode);

        //registerWithQuery(RegisterWithQuery.builder(handle)
        //        .source(source)
        //        .registerToHandleRoot()
        //        .scanningStrategy(scanningStrategy)
        //        .build());
    }

    public void registerWithQuery(RegisterWithQuery query) {
        PropertyScanResult result = query.getResult();
        PropertyNode node = query.getNodeExtractor().apply(registry);

        result.attach(node);
    }
}
