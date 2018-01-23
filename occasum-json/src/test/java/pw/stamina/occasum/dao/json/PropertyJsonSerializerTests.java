package pw.stamina.occasum.dao.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import pw.stamina.occasum.properties.Property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class PropertyJsonSerializerTests {

    @Mock private Property property;
    private final String valueAsString = "value";

    private PropertyJsonSerializer serializer;

    @BeforeEach
    void setupMockedPropertyAndSerializer() {
        when(property.getValueAsString()).thenReturn(valueAsString);
        serializer = new PropertyJsonSerializer();
    }

    @Test
    void serialize_givenNonNullProperty_shouldSerializePropertyGetValueAsStringToJsonPrimitive() {
        JsonElement serialized = serializer.serialize(property, null, null);
        assertTrue(serialized.isJsonPrimitive());

        JsonPrimitive primitive = serialized.getAsJsonPrimitive();
        assertEquals(valueAsString, primitive.getAsString());

        verify(property).getValueAsString();
    }
}
