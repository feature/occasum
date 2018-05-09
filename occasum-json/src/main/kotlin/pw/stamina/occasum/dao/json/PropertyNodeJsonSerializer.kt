package pw.stamina.occasum.dao.json

import com.google.gson.*
import pw.stamina.occasum.node.PropertyNode

import java.lang.reflect.Type

internal class PropertyNodeJsonSerializer : JsonSerializer<PropertyNode> {

    //TODO: Clean this up boi
    override fun serialize(node: PropertyNode, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        val hasChildren = node.hasChildren()
        var serializedProperty: JsonElement? = null

        if (node.hasProperty()) {
            serializedProperty = context.serialize(node.property)

            if (!hasChildren) {
                return serializedProperty
            }
        }

        if (hasChildren) {
            val serialized = JsonObject()

            if (serializedProperty != null) {
                serialized.add("value", serializedProperty)
            }

            serializeAndAddChildren(node, serialized, context)
            return serialized
        }

        return JsonNull.INSTANCE
    }

    private fun serializeAndAddChildren(node: PropertyNode,
                                        serialized: JsonObject,
                                        context: JsonSerializationContext) {
        node.findChildren().forEach { child -> serialized.add(child.id, context.serialize(child)) }
    }
}
