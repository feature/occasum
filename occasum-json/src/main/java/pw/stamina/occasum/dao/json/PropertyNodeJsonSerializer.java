package pw.stamina.occasum.dao.json;

import com.google.gson.*;
import pw.stamina.occasum.node.PropertyNode;

import java.lang.reflect.Type;

final class PropertyNodeJsonSerializer implements JsonSerializer<PropertyNode> {

    //TODO: Clean this up boi
    @Override
    public JsonElement serialize(PropertyNode node, Type typeOfSrc, JsonSerializationContext context) {
        boolean hasChildren = node.hasChildren();
        JsonElement serializedProperty = null;

        if (node.hasProperty()) {
            serializedProperty = context.serialize(node.getProperty());

            if (!hasChildren) {
                return serializedProperty;
            }
        }

        if (hasChildren) {
            JsonObject serialized = new JsonObject();

            if (serializedProperty != null) {
                serialized.add("value", serializedProperty);
            }

            serializeAndAddChildren(node, serialized, context);
            return serialized;
        }

        return JsonNull.INSTANCE;
    }

    private void serializeAndAddChildren(PropertyNode node,
                                         JsonObject serialized,
                                         JsonSerializationContext context) {
        node.findChildren().forEach(child -> serialized.add(child.getId(), context.serialize(child)));
    }
}
