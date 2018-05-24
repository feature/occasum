package pw.stamina.occasum.node

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.node.factory.PropertyNodeFactory

@ExtendWith(MockitoExtension::class)
internal class RootPropertyNodeTests {

    @Mock private lateinit var factory: PropertyNodeFactory
    @Mock private lateinit var handle: PropertyHandle

    private val handleName = "Test"
    private val handleId = "test"

    private lateinit var rootNode: PropertyNode

    @BeforeEach
    fun setupRootNodeAndMockHandleNameAndId() {
        rootNode = RootPropertyNode(factory, handle)

        mockHandleNameAndId(handle)
    }

    private fun mockHandleNameAndId(handle: PropertyHandle) {
        `when`(handle.name).thenReturn(handleName)
        `when`(handle.id).thenReturn(handleId)
    }

    @Test
    @DisplayName("name -> handle.name")
    fun name_shouldReturnNameOfHandle() {
        assertSame(rootNode.name, handleName)
        verify<PropertyHandle>(handle).name
    }

    @Test
    @DisplayName("id -> handle.id")
    fun id_shouldReturnIdOfHandle() {
        assertSame(rootNode.id, handleId)
        verify<PropertyHandle>(handle).id
    }

    @Test
    @DisplayName("property == null")
    fun property_shouldReturnNullIndicatingNoProperty() {
        assertNull(rootNode.property)
    }

    @Test
    @DisplayName("parent == null")
    fun parent_shouldReturnNullIndicatingNoParent() {
        assertNull(rootNode.parent)
    }
}
