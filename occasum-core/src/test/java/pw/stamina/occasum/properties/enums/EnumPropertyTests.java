package pw.stamina.occasum.properties.enums;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pw.stamina.occasum.properties.ParameterizedProperty;
import pw.stamina.occasum.properties.ParameterizedPropertyTests;

import java.util.Random;

final class EnumPropertyTests extends ParameterizedPropertyTests<DummyEnum> {

    @ParameterizedTest
    @CsvSource({
            "foo, FOO",
            "bar, BAR",
            "foo_bar, FOO_BAR"})
    void of_givenValidNameAndEnumValue_shouldCreateEnumPropertyInstance(String name, DummyEnum dummy) {
        EnumProperty.from(name, dummy);
    }

    @Override
    protected ParameterizedProperty<DummyEnum> provideProperty() {
        return EnumProperty.from("dummy", DummyEnum.FOO);
    }

    @Override
    protected void shufflePropertyValue(ParameterizedProperty<DummyEnum> property, Random random) {
        DummyEnum[] values = DummyEnum.values();
        property.set(values[random.nextInt(values.length)]);
    }
}