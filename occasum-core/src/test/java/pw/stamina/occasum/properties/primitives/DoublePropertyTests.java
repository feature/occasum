package pw.stamina.occasum.properties.primitives;

import pw.stamina.occasum.properties.PropertyTests;

import java.util.Random;

final class DoublePropertyTests extends PropertyTests<DoubleProperty> {

    @Override
    protected DoubleProperty provideProperty() {
        return DoubleProperty.from("test", 10);
    }

    @Override
    protected void shufflePropertyValue(DoubleProperty property, Random random) {
        property.set(random.nextDouble());
    }
}