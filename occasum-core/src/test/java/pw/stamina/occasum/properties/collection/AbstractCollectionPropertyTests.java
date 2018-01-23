package pw.stamina.occasum.properties.collection;

import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
abstract class AbstractCollectionPropertyTests<T> {
    private Collection<T> elements;
    protected CollectionProperty<T> property;

    @Mock
    protected CollectionPropertyAdapter<T> adapter;

    @BeforeEach
    void setupProperty() {
        elements = provideElements();
        property = provideProperty();
    }

    @Test
    @DisplayName("add(null) throws NullPointerException")
    void add_givenNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> property.add(null));
    }

    @Test
    @DisplayName("add(element) -> elements.add(element)")
    void add_givenMockedElement_shouldAddToElements(@Mock T element) {
        assertTrue(property.add(element));
        verify(elements).add(element);
    }

    @Test
    @DisplayName("remove(null) throws NullPointerException")
    void remove_givenNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> property.remove(null));
    }

    @Test
    @DisplayName("remove(element) -> elements.remove(element)")
    void remove_givenMockedElement_shouldAttemptToRemoveElement(@Mock T element) {
        assertFalse(property.remove(element));
        verify(elements).remove(element);
    }

    @Test
    @DisplayName("contains(null) throws NullPointerException")
    void contains_givenNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> property.contains(null));
    }

    @Test
    @DisplayName("contains(element) -> elements.contains(element)")
    void contains_givenMockedElement_shouldAttemptToRemoveElement(@Mock T element) {
        assertFalse(property.contains(element));
        verify(elements).contains(element);
    }

    @Test
    @DisplayName("getElements() != elements")
    void getElements_shouldNotReturnInternalCollectionOfElements() {
        assertNotSame(elements, property.getElements());
    }

    @Test
    @DisplayName("getElements().clear() throws UnsupportedOperationException")
    void getElements_shouldBeImmutableAndThrowExceptionIfAttemptedToBeModified() {
        assertThrows(UnsupportedOperationException.class, () -> property.getElements().clear());
    }

    abstract Collection<T> provideElements();

    abstract CollectionProperty<T> provideProperty();
}
