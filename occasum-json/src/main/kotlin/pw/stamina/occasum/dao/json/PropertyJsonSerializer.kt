package pw.stamina.occasum.dao.json

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import pw.stamina.occasum.properties.Property

import java.lang.reflect.Type

internal class PropertyJsonSerializer : JsonSerializer<Property> {

    override fun serialize(property: Property, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(property.valueAsString)
    }
}
