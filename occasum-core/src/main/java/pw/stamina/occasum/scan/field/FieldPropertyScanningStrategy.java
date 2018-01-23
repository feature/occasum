package pw.stamina.occasum.scan.field;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.instructions.PropertyNodeInstructionList;
import pw.stamina.occasum.properties.PropertiesContainer;
import pw.stamina.occasum.properties.Property;
import pw.stamina.occasum.scan.PropertyScanningStrategy;
import pw.stamina.occasum.scan.field.model.Parent;
import pw.stamina.occasum.scan.field.model.Properties;
import pw.stamina.occasum.scan.result.InstructionBasedPropertyScanResult;
import pw.stamina.occasum.scan.result.PropertyScanResult;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

//TODO: @Extract - pulls properties out of a nested scan
//TODO: @Export - exports properties to the

//TODO: Clean this up
public final class FieldPropertyScanningStrategy implements PropertyScanningStrategy {
    private static final FieldPropertyScanningStrategy STANDARD =
            new FieldPropertyScanningStrategy(
                    FieldLocatorService.standard(),
                    FieldIgnorePredicate.standard());

    private final FieldLocatorService fieldLocatorService;
    private final FieldIgnorePredicate ignorePredicate;

    private FieldPropertyScanningStrategy(FieldLocatorService fieldLocatorService,
                                          FieldIgnorePredicate ignorePredicate) {
        this.fieldLocatorService = fieldLocatorService;
        this.ignorePredicate = ignorePredicate;
    }

    @Override
    public PropertyScanResult scan(PropertyHandle handle, Object source) {
        PropertyNodeInstructionList instructions = PropertyNodeInstructionList.newStandard();

        scanAndPopulate(source, instructions);

        return new InstructionBasedPropertyScanResult(instructions, Collections.emptyList());
    }

    private void scanAndPopulate(Object source,
                                 PropertyNodeInstructionList instructions) {
        List<Field> fields = fieldLocatorService.findAllFields(source, ignorePredicate);

        fields.forEach(field -> {
            try {
                Object value = field.get(source);
                if (value == null) {
                    return;
                }

                if (Property.class.isAssignableFrom(field.getType())) {
                    Property property = (Property) value;
                    PropertyNodeInstructionList registeringTo = instructions;

                    if (field.isAnnotationPresent(Parent.class)) {
                        Parent parent = field.getAnnotation(Parent.class);
                        registeringTo = findOrCreateFolder(registeringTo, parent.value());
                    }

                    registeringTo.property(property);
                }

                if (field.isAnnotationPresent(Properties.class)) {
                    Properties properties = field.getAnnotation(Properties.class);
                    PropertyNodeInstructionList registeringTo = instructions;

                    String path = properties.value();
                    if (!path.isEmpty()) {
                        registeringTo = findOrCreateFolder(registeringTo, path);
                    }

                    if (value instanceof PropertiesContainer) {
                        PropertiesContainer container = (PropertiesContainer) value;
                        container.forEach(registeringTo::property);
                    } else {
                        scanAndPopulate(value, registeringTo);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private PropertyNodeInstructionList findOrCreateFolder(PropertyNodeInstructionList instructions, String path) {
        String[] pathSegments = path.split("\\.");

        for (String pathSegment : pathSegments) {
            instructions = instructions.folder(pathSegment);
        }

        return instructions;
    }

    public static PropertyScanningStrategy standard() {
        return STANDARD;
    }
}
