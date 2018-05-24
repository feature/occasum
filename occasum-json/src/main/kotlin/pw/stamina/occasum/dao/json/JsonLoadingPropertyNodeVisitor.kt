package pw.stamina.occasum.dao.json

import com.google.gson.JsonElement
import pw.stamina.occasum.dao.PropertyDao
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.PropertyParseException
import java.util.*

internal class JsonLoadingPropertyNodeVisitor private constructor(
        private val element: JsonElement,
        private val exceptions: MutableList<Exception>
) : PropertyNodeVisitor {

    constructor(element: JsonElement) : this(element, ArrayList<Exception>())

    override fun visitNode(node: PropertyNode) {
        val property = node.property ?: return

        findPropertyValueFromElement().ifPresent { value ->
            try {
                property.parseAndSet(value)
            } catch (e: PropertyParseException) {
                exceptions.add(e)
            }
        }
    }

    private fun findPropertyValueFromElement(): Optional<String> {
        if (element.isJsonPrimitive) {
            return Optional.of(element.asString)
        }

        if (element.isJsonObject) {
            val `object` = element.asJsonObject
            val name = PropertyDao.RESERVED_SERIALIZED_VALUE_NAME

            if (`object`.has(name)) {
                val value = `object`.getAsJsonPrimitive(name)
                return Optional.of(value.asString)
            }
        }

        return Optional.empty()
    }

    override fun visitChildNode(childNode: PropertyNode): PropertyNodeVisitor? {
        if (!element.isJsonObject) {
            return null
        }

        val `object` = element.asJsonObject
        val name = childNode.id

        if (!`object`.has(name)) {
            return null
        }

        val childElement = `object`.get(name)
        return JsonLoadingPropertyNodeVisitor(childElement, exceptions)
    }

    fun getExceptions(): List<Exception> {
        return exceptions
    }
}
