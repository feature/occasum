package pw.stamina.occasum.dao.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import pw.stamina.occasum.properties.Property;

import java.lang.reflect.Type;

final class PropertyJsonSerializer implements JsonSerializer<Property> {

    @Override
    public JsonElement serialize(Property property, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(property.getValueAsString());
    }
}
