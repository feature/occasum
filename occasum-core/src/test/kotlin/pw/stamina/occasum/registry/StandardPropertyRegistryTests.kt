package pw.stamina.occasum.registry

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.properties.Property

@ExtendWith(MockitoExtension::class)
internal class StandardPropertyRegistryTests {

    private lateinit var registry: PropertyRegistry

    //TODO: Locator is never used
    @Mock private lateinit var locator: PropertyLocatorService
    @Mock private lateinit var factory: PropertyNodeFactory

    @Mock private lateinit var handle: PropertyHandle
    @Mock private lateinit var rootNode: PropertyNode

    @Mock private lateinit var property: Property<*>
    @Mock private lateinit var propertyNode: PropertyNode

    @BeforeEach
    fun setupRegistry() {
        registry = StandardPropertyRegistry(factory)
    }

    @BeforeEach
    fun setupFactoryRootNodeAndPropertyNodeMocks() {
        `when`(factory.root(handle)).thenReturn(rootNode)
        `when`(rootNode.property(property)).thenReturn(propertyNode)
    }

    @Test
    fun register_givenNonNullHandleAndProperty_shouldCreateRootNodeAndRegisterPropertyAsChildNodeForRootNode() {
        val node = registry.register(handle, property)

        assertSame(propertyNode, node)
        verify<PropertyNodeFactory>(factory).root(handle)
        verify<PropertyNode>(rootNode).property(property)
    }

    //TODO: Test multiple registerAll with elements

    @Test
    fun registerAll_givenNonNullHandleAndSingleElementProperties_shouldCreateRootNodeAndRegisterPropertyAsChildNodeForRootNode() {
        val properties = setOf(property)

        registry.registerAll(handle, properties)

        verify<PropertyNodeFactory>(factory).root(handle)
        verify<PropertyNode>(rootNode).property(property)
    }
}