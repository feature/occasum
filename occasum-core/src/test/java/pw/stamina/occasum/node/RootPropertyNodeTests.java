package pw.stamina.occasum.node;

import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class RootPropertyNodeTests {

    @Mock private PropertyNodeFactory factory;
    @Mock private PropertyHandle handle;

    private final String handleName = "Test";
    private final String handleId = "test";

    private PropertyNode rootNode;

    @BeforeEach
    void setupRootNodeAndHandleNameAndId() {
        rootNode = new RootPropertyNode(factory, handle);

        when(handle.getName()).thenReturn(handleName);
        when(handle.getId()).thenReturn(handleId);
    }

    @Test
    @DisplayName("getName() -> handle.getName()")
    void getName_shouldReturnNameOfHandle() {
        assertSame(rootNode.getName(), handleName);
        verify(handle).getName();
    }

    @Test
    @DisplayName("getId() -> handle.getId()")
    void getId_shouldReturnIdOfHandle() {
        assertSame(rootNode.getId(), handleId);
        verify(handle).getId();
    }

    @Test
    @DisplayName("getProperty() -> IllegalStateException")
    void getProperty_shouldThrowExceptionIndicatingRootNodeCannotHaveProperties() {
        assertThrows(IllegalStateException.class, () -> rootNode.getProperty());
    }

    @Test
    @DisplayName("hasProperty() == false")
    void hasProperty_shouldReturnFalse() {
        assertFalse(rootNode.hasProperty());
    }

    @Test
    @DisplayName("getParent() -> IllegalStateException")
    void getParent_shouldThrowExceptionIndicatingRootNodeCannotHaveParents() {
        assertThrows(IllegalStateException.class, () -> rootNode.getParent());
    }

    @Test
    @DisplayName("hasParent() == false")
    void hasParent_shouldReturnFalse() {
        assertFalse(rootNode.hasParent());
    }
}
