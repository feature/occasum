package pw.stamina.occasum.node.visit;

import pw.stamina.occasum.node.PropertyNode;

import java.io.PrintStream;
import java.util.Objects;
import java.util.stream.Stream;

public final class PrettyPrintingPropertyNodeVisitor extends AbstractPropertyNodeVisitor {
    private static final int DEFAULT_INDENTION = 0;
    private static final int INDENTION_PRINTING_OFFSET = 1;
    private static final int INDENTION_LEVEL_INCREASE = 1;

    private final PrintStream out;
    private final int indention;

    public PrettyPrintingPropertyNodeVisitor(PrintStream out) {
        this(out, DEFAULT_INDENTION);
    }

    private PrettyPrintingPropertyNodeVisitor(PrintStream out, int indention) {
        Objects.requireNonNull(out, "out");
        this.out = out;
        this.indention = indention;
    }

    @Override
    public void visitNode(PropertyNode node) {
        addIndention();

        out.print(node.getId());
        addPropertyValueIfPresent(node);

        out.println();
    }

    private void addIndention() {
        if (indention > 0) {
            int indentionExcludingFirstStep = indention - INDENTION_PRINTING_OFFSET;
            Stream.generate(() -> "  ").limit(indentionExcludingFirstStep).forEach(out::print);
            out.print("+ ");
        }
    }

    private void addPropertyValueIfPresent(PropertyNode node) {
        if (node.hasProperty()) {
            out.print(":");
            out.print(node.getProperty().getValueAsString());
        }
    }

    @Override
    protected PropertyNodeVisitor createChildVisitor(PropertyNode childNode) {
        return withIncreasedIndention();
    }

    private PrettyPrintingPropertyNodeVisitor withIncreasedIndention() {
        int increasedIndention = indention + INDENTION_LEVEL_INCREASE;
        return new PrettyPrintingPropertyNodeVisitor(out, increasedIndention);
    }
}
