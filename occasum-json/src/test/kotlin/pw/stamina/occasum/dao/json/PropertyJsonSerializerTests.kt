package pw.stamina.occasum.dao.json

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import pw.stamina.occasum.dao.PropertyDao
import pw.stamina.occasum.properties.Property

@ExtendWith(MockitoExtension::class)
internal class PropertyJsonSerializerTests {

    @Mock private lateinit var property: Property
    private val valueAsString = PropertyDao.RESERVED_SERIALIZED_VALUE_NAME

    private lateinit var serializer: PropertyJsonSerializer

    @BeforeEach
    fun setupMockedPropertyAndSerializer() {
        `when`(property.valueAsString).thenReturn(valueAsString)
        serializer = PropertyJsonSerializer()
    }

    @Test
    fun serialize_givenNonNullProperty_shouldSerializePropertyGetValueAsStringToJsonPrimitive() {
        val serialized = serializer.serialize(property, null, null)
        assertTrue(serialized.isJsonPrimitive)

        val primitive = serialized.asJsonPrimitive
        assertEquals(valueAsString, primitive.asString)

        verify(property).valueAsString
    }
}
