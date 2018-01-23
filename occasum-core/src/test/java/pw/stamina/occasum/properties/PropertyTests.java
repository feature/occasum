package pw.stamina.occasum.properties;

import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class PropertyTests<T extends Property> {
    private static final Random RANDOM = new Random();
    protected T property;

    @BeforeEach
    void setupPropertyFromProvider() {
        property = provideProperty();
    }

    @Test
    @DisplayName("parseAndSet(null) -> throws PropertyParseException")
    void parseAndSet_givenNullInput_shouldThrowNullPointerException() {
        assertThrows(PropertyParseException.class, () -> property.parseAndSet(null));
    }

    @RepeatedTest(10)
    @DisplayName("reset() -> isDefault() returns true")
    void isDefault_afterResetHasBeenInvoked_shouldReturnTrue() {
        shufflePropertyValue(property, RANDOM);
        property.reset();
        assertTrue(property.isDefault());
    }

    protected abstract T provideProperty();

    protected abstract void shufflePropertyValue(T property, Random random);
}
