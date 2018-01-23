package pw.stamina.occasum.dao.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.visit.AbstractPropertyNodeVisitor;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.properties.PropertyParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

final class JsonLoadingPropertyNodeVisitor extends AbstractPropertyNodeVisitor {
    private static final String JSON_OBJECT_PROPERTY_VALUE_NAME = "value";

    private final JsonElement element;
    private final List<Exception> exceptions;

    JsonLoadingPropertyNodeVisitor(JsonElement element) {
        this(element, new ArrayList<>());
    }

    private JsonLoadingPropertyNodeVisitor(JsonElement element,
                                           List<Exception> exceptions) {
        this.element = element;
        this.exceptions = exceptions;
    }

    @Override
    public void visitNode(PropertyNode node) {
        if (!node.hasProperty()) {
            return;
        }

        findPropertyValueFromElement().ifPresent(value -> {
            try {
                node.getProperty().parseAndSet(value);
            } catch (PropertyParseException e) {
                exceptions.add(e);
            }
        });
    }

    private Optional<String> findPropertyValueFromElement() {
        if (element.isJsonPrimitive()) {
            return Optional.of(element.getAsString());
        }

        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            String name = JSON_OBJECT_PROPERTY_VALUE_NAME;

            if (object.has(name)) {
                JsonPrimitive value = object.getAsJsonPrimitive(name);
                return Optional.of(value.getAsString());
            }
        }

        return Optional.empty();
    }

    @Override
    protected PropertyNodeVisitor createChildVisitor(PropertyNode childNode) {
        if (!element.isJsonObject()) {
            return null;
        }

        JsonObject object = element.getAsJsonObject();
        String name = childNode.getId();

        if (!object.has(name)) {
            return null;
        }

        JsonElement childElement = object.get(name);
        return new JsonLoadingPropertyNodeVisitor(childElement, exceptions);
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
