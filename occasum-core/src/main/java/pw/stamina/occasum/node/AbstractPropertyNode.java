package pw.stamina.occasum.node;

import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.properties.Property;

import java.util.*;
import java.util.function.Predicate;

public abstract class AbstractPropertyNode implements PropertyNode {
    private final PropertyNodeFactory factory;

    protected final PropertyHandle handle;
    private List<PropertyNode> children;

    protected AbstractPropertyNode(PropertyNodeFactory factory,
                                   PropertyHandle handle) {
        Objects.requireNonNull(factory, "factory");
        Objects.requireNonNull(handle, "handle");

        this.factory = factory;
        this.handle = handle;
    }

    @Override
    public final PropertyHandle getHandle() {
        return handle;
    }

    @Override
    public final boolean hasChildren() {
        return children != null;
    }

    @Override
    public final List<PropertyNode> findChildren() {
        if (hasChildren()) {
            return Collections.unmodifiableList(children);
        }

        return Collections.emptyList();
    }

    @Override
    public final PropertyNode property(PropertyHandle handle, Property property) {
        String propertyId = property.getId();

        if (find(propertyId).isPresent()) {
            throw new IllegalArgumentException(
                    "another node with the same id has " +
                            "already been registered. Id: " + propertyId);
        }

        return add(factory.property(handle, this, property));
    }

    @Override
    public final PropertyNode property(Property property) {
        return property(handle, property);
    }

    @Override
    public final PropertyNode folder(PropertyHandle handle, String name) {
        return find(name).orElseGet(() -> add(factory.folder(handle, this, name)));
    }

    @Override
    public final PropertyNode folder(String name) {
        return folder(handle, name);
    }

    private PropertyNode add(PropertyNode node) {
        createChildrenIfAbsent();

        children.add(node);
        return node;
    }

    private void createChildrenIfAbsent() {
        if (!hasChildren()) {
            children = new ArrayList<>();
        }
    }

    @Override
    public void removeChild(PropertyNode node) {
        if (hasChildren() && children.remove(node)) {
            factory.notifyRemoval(node);
        }
    }

    @Override
    public void removeChild(String id) {
        find(id).ifPresent(this::removeChild);
    }

    @Override
    public final Optional<PropertyNode> find(String id) {
        if (!hasChildren()) {
            return Optional.empty();
        }

        return children.stream()
                .filter(doesIdMatchIgnoreCase(id))
                .findAny();
    }

    private static Predicate<PropertyNode> doesIdMatchIgnoreCase(String id) {
        return node -> node.getId().equalsIgnoreCase(id);
    }

    @Override
    public void accept(PropertyNodeVisitor visitor) {
        visitor.visitNode(this);

        if (hasChildren()) {
            visitChildren(visitor);
        }
    }

    private void visitChildren(PropertyNodeVisitor visitor) {
        children.forEach(childNode -> visitor.visitChildNode(childNode).ifPresent(childNode::accept));
    }

    @Override
    public String toString() {
        return "PropertyNode{id=" + getId() + '}';
    }
}
