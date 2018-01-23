package pw.stamina.occasum.properties.selective;

import org.junit.jupiter.api.*;
import pw.stamina.occasum.Named;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("A SelectivePropertyBuilder")
final class SelectivePropertyBuilderTests {

    private SelectivePropertyBuilder<Named> builder;
    private SelectiveProperty<Named> property;

    @Test
    @DisplayName("is instantiated with new SelectivePropertyBuilder(\"name\")")
    void isInstantiatedWithStaticFactoryMethod() {
        new SelectivePropertyBuilder("name");
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewBuilder() {
            builder = new SelectivePropertyBuilder<>("name");
        }

        @Test
        @DisplayName("throws IllegalStateException then build is called")
        void throwsExceptionWhenBuildIsCalled() {
            Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
        }

        @Nested
        @DisplayName("selected set")
        class SelectedSet {

            private final Named selected = new Named() {
                @Override
                public String getName() {
                    return "Selected";
                }

                @Override
                public String getId() {
                    return "selected";
                }
            };

            @BeforeEach
            @DisplayName("setting selected")
            void setSelected() {
                builder.selected(selected);
            }

            @Test
            @DisplayName("build creates new SelectiveProperty")
            void canBuildWithSelectedValue() {
                assertNotNull(builder.build());
            }

            @Nested
            @DisplayName("verifying property value is value specified is selected()")
            class VerifyPropertyValue {

                @BeforeEach
                void setProperty() {
                    property = builder.build();
                }

                @Test
                void t() {
                    assertSame(selected, property.get());
                }
            }
        }
    }
}