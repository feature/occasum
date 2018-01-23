package pw.stamina.occasum.node.factory.inspect;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

public interface PropertyNodeFactoryParameterInspector {

    default void inspectRoot(PropertyHandle handle) {}

    default void inspectProperty(PropertyHandle handle,
                                 PropertyNode parent,
                                 Property property) {}

    default void inspectFolder(PropertyHandle handle,
                               PropertyNode parent,
                               String name) {}

    default void inspectNotifyRemoval(PropertyNode node) {}

    static PropertyNodeFactoryParameterInspector nullRejecting() {
        return new NullRejectingPropertyNodeFactoryParameterInspector();
    }

    static PropertyNodeFactoryParameterInspector nameValidating() {
        return new NameValidatingPropertyNodeFactoryParameterInspector();
    }

    static PropertyNodeFactoryParameterInspector duplicatedPropertyDetecting() {
        return new DuplicatedPropertyDetectingPropertyNodeFactoryParameterInspector();
    }

    static void addStandardInspectors(PropertyNodeFactory factory) {
        factory.addInspector(nullRejecting());
        factory.addInspector(nameValidating());
        factory.addInspector(duplicatedPropertyDetecting());
    }
}
