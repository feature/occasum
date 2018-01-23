package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.PropertyParseException;
import pw.stamina.occasum.properties.PropertyTests;

import java.util.Random;

final class IntPropertyTests extends PropertyTests<IntProperty> {

    void parseAndSet(String input, int expected) throws PropertyParseException {
        property.parseAndSet(input);
        int parsedValue = property.get();
    }

    @Override
    protected IntProperty provideProperty() {
        return IntProperty.from("test", 5);
    }

    @Override
    protected void shufflePropertyValue(IntProperty property, Random random) {
        property.set(random.nextInt(100));
    }
}