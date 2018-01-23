package pw.stamina.occasum.node;

import pw.stamina.occasum.Named;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.properties.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyNode extends Named {

    PropertyHandle getHandle();

    /**
     * Indicates if this node is holding a {@link Property property}.
     * The return value of this method should not change.
     *
     * @return <tt>true</tt> if this node holds a {@link Property}
     */
    boolean hasProperty();

    /**
     * Returns the {@link Property property} associated with this node. If the
     * {@link PropertyNode#hasProperty()} method indicates that this node does
     * not hold a property, an {@link IllegalStateException} is thrown
     *
     * @return the {@link Property} this node is hold
     * @throws IllegalStateException if this method is invoked, but this
     * node is not holding any {@link Property}.
     */
    Property getProperty() throws IllegalStateException;

    boolean hasParent();

    //TODO: Throw illegal state exception if hasParent returns false
    PropertyNode getParent();

    boolean hasChildren();

    List<PropertyNode> findChildren();

    PropertyNode property(PropertyHandle handle, Property property);

    PropertyNode property(Property property);

    PropertyNode folder(PropertyHandle handle, String name);

    PropertyNode folder(String name);

    void removeChild(PropertyNode node);

    void removeChild(String id);

    /**
     * Searches the children for a node
     *
     * @param id the id of the node being searched for
     * @return the node case insensitively matching the
     * specified {@code id} wrapped in an {@link Optional},
     * or an {@link Optional#empty()} Optional if no node
     * matched the specified id.
     */
    Optional<PropertyNode> find(String id);

    /**
     * As part of the visitor pattern this method accepts a
     * {@link PropertyNodeVisitor visitor}. The specified
     * visitor visits this node, and creates new individual
     * visitors for all children of this node.
     *
     * @param visitor the visitor used to visit this node
     */
    void accept(PropertyNodeVisitor visitor);
}
