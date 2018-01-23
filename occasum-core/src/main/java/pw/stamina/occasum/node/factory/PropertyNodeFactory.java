package pw.stamina.occasum.node.factory;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.StandardPropertyNodeFactory;
import pw.stamina.occasum.node.factory.inspect.PropertyNodeFactoryParameterInspector;
import pw.stamina.occasum.properties.Property;

public interface PropertyNodeFactory {

    PropertyNode root(PropertyHandle handle);

    PropertyNode property(PropertyHandle handle,
                          PropertyNode parent,
                          Property property);

    PropertyNode folder(PropertyHandle handle,
                        PropertyNode parent,
                        String name);

    void notifyRemoval(PropertyNode node);

    void addInspector(PropertyNodeFactoryParameterInspector inspector);

    static PropertyNodeFactory standard() {
        PropertyNodeFactory factory = StandardPropertyNodeFactory.newInstance();
        PropertyNodeFactoryParameterInspector.addStandardInspectors(factory);
        return factory;
    }
}
