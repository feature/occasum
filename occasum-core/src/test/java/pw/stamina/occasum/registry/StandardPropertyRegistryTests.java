package pw.stamina.occasum.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.properties.Property;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO
//@ExtendWith(MockitoExtension.class)
final class StandardPropertyRegistryTests {
    private PropertyRegistry registry;

    @Mock
    private PropertyLocatorService locator;
    @Mock
    private PropertyNodeFactory factory;

    @Mock
    private PropertyHandle handle;
    @Mock
    private PropertyNode rootNode;

    @Mock
    private Property property;
    @Mock
    private PropertyNode propertyNode;

    @BeforeEach
    void setupRegistry() {
        registry = new StandardPropertyRegistry(factory);
    }

    @BeforeEach
    void setupFactoryRootNodeAndPropertyNodeMocks() {
        when(factory.root(handle)).thenReturn(rootNode);
        when(rootNode.property(property)).thenReturn(propertyNode);
    }

    @Test
    void register_givenNonNullHandleAndProperty_shouldCreateRootNodeAndRegisterPropertyAsChildNodeForRootNode() {
        PropertyNode node = registry.register(handle, property);

        assertSame(propertyNode, node);
        verify(factory).root(handle);
        verify(rootNode).property(property);
    }

    //TODO: Test multiple registerAll with elements

    @Test
    void registerAll_givenNonNullHandleAndSingleElementProperties_shouldCreateRootNodeAndRegisterPropertyAsChildNodeForRootNode() {
        Set<Property> properties = Collections.singleton(property);

        registry.registerAll(handle, properties);

        verify(factory).root(handle);
        verify(rootNode).property(property);
    }
}