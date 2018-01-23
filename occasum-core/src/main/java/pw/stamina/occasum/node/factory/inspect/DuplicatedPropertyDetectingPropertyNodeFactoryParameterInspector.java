package pw.stamina.occasum.node.factory.inspect;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.properties.Property;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

final class DuplicatedPropertyDetectingPropertyNodeFactoryParameterInspector
        implements PropertyNodeFactoryParameterInspector {
    private final Set<Property> registeredProperties;

    DuplicatedPropertyDetectingPropertyNodeFactoryParameterInspector() {
        this.registeredProperties = createRegisteredPropertiesSet();
    }

    @Override
    public void inspectProperty(PropertyHandle handle, PropertyNode parent, Property property) {
        if (registeredProperties.contains(property)) {
            throw new IllegalArgumentException(
                    "that property has already been registered to another node");
        } else {
            registeredProperties.add(property);
        }
    }

    @Override
    public void inspectNotifyRemoval(PropertyNode node) {
        PropertyNodeVisitor visitor = new NotifyRemovalCleanupPropertyNodeVisitor();
        node.accept(visitor);
    }

    private static Set<Property> createRegisteredPropertiesSet() {
        return Collections.newSetFromMap(new IdentityHashMap<>());
    }

    final class NotifyRemovalCleanupPropertyNodeVisitor implements PropertyNodeVisitor {

        @Override
        public void visitNode(PropertyNode node) {
            if (node.hasProperty()) {
                Property property = node.getProperty();
                registeredProperties.remove(property);
            }
        }
    }
}
