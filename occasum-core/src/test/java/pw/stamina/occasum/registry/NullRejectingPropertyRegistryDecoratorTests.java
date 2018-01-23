package pw.stamina.occasum.registry;

import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.properties.Property;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
final class NullRejectingPropertyRegistryDecoratorTests {

    private PropertyRegistry decorator;

    @Mock private PropertyRegistry registry;
    @Mock private PropertyHandle handle;
    @Mock private Property property;
    @Mock private PropertyNode node;

    @BeforeEach
    void setupRegistryDecorator() {
        decorator = new NullRejectingPropertyRegistryDecorator(registry);
    }

    @Test
    @DisplayName("new NullRejectingPropertyRegistryDecorator(null) throws NullPointerException")
    void constructor_givenNullRegistry_shouldThrowException() {
        assertThrowsNullPointerException("registry", () -> new NullRejectingPropertyRegistryDecorator(null));
    }

    @Test
    @DisplayName("decorator.register(null, null) throws NullPointerException")
    void register_givenNullArguments_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.register(null, property));
        assertThrowsNullPointerException("property", () -> decorator.register(handle, null));
    }

    @Test
    @DisplayName("decorator.register(handle, property) -> registry.register(handle, property)")
    void register_givenNonNullArguments_shouldDelegateCallToUnderlyingRegistry() {
        decorator.register(handle, property);
        verify(registry).register(handle, property);
    }

    @Test
    @DisplayName("decorator.registerAll(null, null) throws NullPointerException")
    void registerAll_givenNullArguments_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.registerAll(null, emptyList()));
        assertThrowsNullPointerException("properties", () -> decorator.registerAll(handle, null));
    }

    @Test
    @DisplayName("decorator.registerAll(handle, emptyList()) -> registry.registerAll(handle, property)")
    void registerAll_givenNonNullArguments_shouldDelegateCallToUnderlyingRegistry() {
        decorator.registerAll(handle, emptyList());
        verify(registry).registerAll(handle, emptyList());
    }

    @Test
    @DisplayName("decorator.unregisterAll(null) throws NullPointerException")
    void unregisterAll_givenNullHandle_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.unregisterAll(null));
    }

    @Test
    @DisplayName("decorator.unregisterAll(handle) -> registry.unregisterAll(handle)")
    void unregisterAll_givenNonNullHandle_shouldDelegateCallToUnderlyingRegistry() {
        decorator.unregisterAll(handle);
        verify(registry).unregisterAll(handle);
    }

    @Test
    @DisplayName("decorator.unregister(null) throws NullPointerException")
    void unregister_givenNullNode_shouldThrowException() {
        assertThrowsNullPointerException("node", () -> decorator.unregister(null));
    }

    @Test
    @DisplayName("decorator.unregister(node) -> registry.unregister(node)")
    void unregister_givenNonHandle_shouldDelegateCallToUnderlyingRegistry() {
        decorator.unregister(node);
        verify(registry).unregister(node);
    }

    @Test
    @DisplayName("decorator.unregister(null, null) throws NullPointerException")
    void unregister_givenNullArguments_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.unregister(null, ""));
        assertThrowsNullPointerException("path", () -> decorator.unregister(handle, null));
    }

    @Test
    @DisplayName("decorator.unregister(handle, \"\") -> registry.unregister(handle, \"\")")
    void unregister_givenNonNullArguments_shouldDelegateCallToUnderlyingRegistry() {
        decorator.unregister(handle, "");
        verify(registry).unregister(handle, "");
    }

    @Test
    @DisplayName("decorator.findRootNode(null) throws NullPointerException")
    void findRootNode_givenNullHandle_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.findRootNode(null));
    }

    @Test
    @DisplayName("decorator.findRootNode(handle) -> registry.findRootNode(handle)")
    void findRootNode_givenNonNullHandle_shouldDelegateCallToUnderlyingRegistry() {
        decorator.findRootNode(handle);
        verify(registry).findRootNode(handle);
    }

    @Test
    @DisplayName("decorator.findOrCreateRootNode(null) throws NullPointerException")
    void findOrCreateRootNode_givenNullHandle_shouldThrowException() {
        assertThrowsNullPointerException("handle", () -> decorator.findOrCreateRootNode(null));
    }

    @Test
    @DisplayName("decorator.findOrCreateRootNode(handle) -> registry.findOrCreateRootNode(handle)")
    void findOrCreateRootNode_givenNonNullHandle_shouldDelegateCallToUnderlyingRegistry() {
        decorator.findOrCreateRootNode(handle);
        verify(registry).findOrCreateRootNode(handle);
    }

    @Test
    @DisplayName("decorator.findAllRootNodes() -> registry.findAllRootNodes()")
    void findAllRootNodes_shouldDelegateCallToUnderlyingRegistry() {
        decorator.findAllRootNodes();
        verify(registry).findAllRootNodes();
    }

    private void assertThrowsNullPointerException(String expectedMessage, Executable executable) {
        NullPointerException exception = assertThrows(NullPointerException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
