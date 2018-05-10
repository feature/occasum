package pw.stamina.occasum.dao.json

import com.google.gson.*
import pw.stamina.occasum.dao.PropertyDao
import pw.stamina.occasum.node.PropertyNode

import java.lang.reflect.Type

internal class PropertyNodeJsonSerializer : JsonSerializer<PropertyNode> {

    //TODO: Clean this up boi
    override fun serialize(node: PropertyNode, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        val children = node.children
        val property = node.property

        var serializedProperty: JsonElement? = null

        if (property != null) {
            serializedProperty = context.serialize(property)

            if (children == null) {
                return serializedProperty
            }
        }

        if (children != null) {
            val serialized = JsonObject()

            if (serializedProperty != null) {
                serialized.add(PropertyDao.RESERVED_SERIALIZED_VALUE_NAME, serializedProperty)
            }

            serializeChildren(children, context).forEach { (child, serializedChild) ->
                serialized.add(child.id, serializedChild)
            }

            return serialized
        }

        return JsonNull.INSTANCE
    }

    private fun serializeChildren(children: List<PropertyNode>,
                                  context: JsonSerializationContext):
            List<Pair<PropertyNode, JsonElement>> {
        return children.map { child -> child to context.serialize(child) }
    }
}
