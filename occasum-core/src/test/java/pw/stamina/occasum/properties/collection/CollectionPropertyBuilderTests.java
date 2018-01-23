package pw.stamina.occasum.properties.collection;

import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
final class CollectionPropertyBuilderTests<T> {

    private CollectionPropertyBuilder<T> builder;

    @Test
    @DisplayName("new CollectionPropertyBuilder(null) throws NullPointerException")
    void constructor_givenNullName_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> new CollectionPropertyBuilder(null));
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void setupBuilder() {
            builder = new CollectionPropertyBuilder<>("name");
        }

        @Test
        void buildSet_adapterHasNotBeenSet_shouldThrowException() {
            assertThrowsAdapterRequiredException(() -> builder.buildSet(Collections::emptySet));
        }

        @Test
        void buildList_adapterHasNotBeenSet_shouldThrowException() {
            assertThrowsAdapterRequiredException(() -> builder.buildList(Collections::emptyList));
        }

        @Test
        void buildHashSet_adapterHasNotBeenSet_shouldThrowException() {
            assertThrowsAdapterRequiredException(() -> builder.buildHashSet());
        }

        @Test
        void buildConcurrentHashSet_adapterHasNotBeenSet_shouldThrowException() {
            assertThrowsAdapterRequiredException(() -> builder.buildConcurrentHashSet());
        }

        @Test
        void buildArrayList_adapterHasNotBeenSet_shouldThrowException() {
            assertThrowsAdapterRequiredException(() -> builder.buildArrayList());
        }

        @Nested
        @DisplayName("adding default elements")
        class AddingDefaultElements {

            @Test
            @DisplayName("add(null) throws NullPointerException")
            void add_givenNullElement_shouldThrowException() {
                assertThrows(NullPointerException.class, () -> builder.add(null));
            }

            @Test
            @DisplayName("add(element)")
            void add_givenMockedElement_shouldAddElement(@Mock T element) {
                builder.add(element);
            }

            @Test
            @DisplayName("addAll((T[]) null) throws NullPointerException")
            void addAll_givenNullElementsArray_shouldThrowException() {
                assertThrows(NullPointerException.class, () -> builder.addAll((T[]) null));
            }

            @Test
            @DisplayName("addAll({null}) throws NullPointerException")
            void addAll_givenArrayContainingNullElement_shouldThrowException() {
                assertThrows(NullPointerException.class, () -> builder.addAll((T) null));
            }

            @Test
            @DisplayName("addAll({}) throws IllegalArgumentException")
            void addAll_givenEmptyArray_shouldThrowException() {
                assertThrows(IllegalArgumentException.class, () -> builder.addAll());
            }

            @Test
            @DisplayName("addAll({one, two, three})")
            void addAll_givenArrayWithMockedElement_shouldAddElement(@Mock T element) {
                builder.addAll(element);
            }

            @Test
            @DisplayName("addAll((Iterable<T>) null)) throws NullPointerException")
            void addAll_givenNullElementsIterable_shouldThrowException() {
                assertThrows(NullPointerException.class, () -> builder.addAll((Iterable<T>) null));
            }

            @Test
            @DisplayName("addAll(singleton(null)) throws NullPointerException")
            void addAll_givenElementsIteratorContainingNull_shouldThrowException() {
                assertThrows(NullPointerException.class, () -> builder.addAll(Collections.singleton(null)));
            }

            @Test
            @DisplayName("addAll(singleton(element))")
            void addAll_givenArrayWithMockedElement_shouldAddElements(@Mock T element) {
                builder.addAll(Collections.singleton(element));
            }
        }

        @Nested
        @DisplayName("with adapter set")
        class WithAdapter {

            @BeforeEach
            void setAdapter(@Mock CollectionPropertyAdapter<T> adapter) {
                builder.adapter(adapter);
            }

            @Test
            void buildSet_adapterHasNotBeenSet_shouldThrowException() {
                builder.buildSet(Collections::emptySet);
            }

            @Test
            void buildList_adapterHasNotBeenSet_shouldThrowException() {
                builder.buildList(Collections::emptyList);
            }

            @Test
            void buildHashSet_adapterHasNotBeenSet_shouldThrowException() {
                builder.buildHashSet();
            }

            @Test
            void buildConcurrentHashSet_adapterHasNotBeenSet_shouldThrowException() {
                builder.buildConcurrentHashSet();
            }

            @Test
            void buildArrayList_adapterHasNotBeenSet_shouldThrowException() {
                builder.buildArrayList();
            }
        }
    }

    private static void assertThrowsAdapterRequiredException(Executable executable) {
        IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertEquals("no adapter has been set", exception.getMessage());
    }
}
