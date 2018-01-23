package pw.stamina.occasum.node.factory.inspect;

import pw.stamina.occasum.Named;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;

final class NameValidatingPropertyNodeFactoryParameterInspector
        implements PropertyNodeFactoryParameterInspector {

    @Override
    public void inspectRoot(PropertyHandle handle) {
        validatePropertyNodeName(handle.getName());
    }

    @Override
    public void inspectProperty(PropertyHandle handle, PropertyNode parent, Property property) {
        validatePropertyNodeName(property.getName());
    }

    @Override
    public void inspectFolder(PropertyHandle handle, PropertyNode parent, String name) {
        validatePropertyNodeName(name);
    }

    private static void validatePropertyNodeName(String name) throws IllegalArgumentException {
        Named.validateName(name);

        if (name.equals("value")) {
            throw new IllegalArgumentException(
                    "the name \"value\" is reserved for serialization purposes");
        }
    }
}
