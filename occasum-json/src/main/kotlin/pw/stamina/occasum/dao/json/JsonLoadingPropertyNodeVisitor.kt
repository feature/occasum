package pw.stamina.occasum.dao.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.visit.AbstractPropertyNodeVisitor
import pw.stamina.occasum.node.visit.PropertyNodeVisitor
import pw.stamina.occasum.properties.PropertyParseException

import java.util.ArrayList
import java.util.Optional

internal class JsonLoadingPropertyNodeVisitor private constructor(
        private val element: JsonElement,
        exceptions: List<Exception>)
    : AbstractPropertyNodeVisitor() {

    private val exceptions: MutableList<Exception>

    constructor(element: JsonElement) : this(element, ArrayList<Exception>())

    init {
        this.exceptions = exceptions
    }

    override fun visitNode(node: PropertyNode) {
        if (!node.hasProperty()) {
            return
        }

        findPropertyValueFromElement().ifPresent { value ->
            try {
                node.property.parseAndSet(value)
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
            val name = JSON_OBJECT_PROPERTY_VALUE_NAME

            if (`object`.has(name)) {
                val value = `object`.getAsJsonPrimitive(name)
                return Optional.of(value.asString)
            }
        }

        return Optional.empty()
    }

    override fun createChildVisitor(childNode: PropertyNode): PropertyNodeVisitor? {
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

    companion object {
        private val JSON_OBJECT_PROPERTY_VALUE_NAME = "value"
    }
}
