package pw.stamina.occasum.properties.primitives;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pw.stamina.occasum.properties.PropertyParseException;
import pw.stamina.occasum.properties.PropertyTests;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class BooleanPropertyTests extends PropertyTests<BooleanProperty> {


    @ParameterizedTest
    @CsvSource({
            "true, true",
            "false, false",
            "foo, false",
            "bar, false"})
    void parseAndSet_validStringInput_shouldParseAndSetExpectedValue(String input, boolean expected) throws PropertyParseException {
        property.parseAndSet(input);
        boolean parsedValue = property.get();

        assertEquals(expected, parsedValue);
    }

    @Override
    protected BooleanProperty provideProperty() {
        return BooleanProperty.from("dummy", false);
    }

    @Override
    protected void shufflePropertyValue(BooleanProperty property, Random random) {
        property.set(random.nextBoolean());
    }
}