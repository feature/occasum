package pw.stamina.occasum.node.visit;

import pw.stamina.occasum.node.PropertyNode
import java.io.PrintStream
import java.util.stream.Stream

private const val DEFAULT_INDENTION = 0
private const val INDENTION_PRINTING_OFFSET = 1L
private const val INDENTION_LEVEL_INCREASE = 1

class PrettyPrintingPropertyNodeVisitor private constructor(
        private val out: PrintStream,
        private val indention: Int)
    : AbstractPropertyNodeVisitor() {

    constructor(out: PrintStream) : this(out, DEFAULT_INDENTION)

    override fun visitNode(node: PropertyNode) {
        addIndention()

        out.print(node.id)
        addPropertyValueIfPresent(node)

        out.println()
    }

    private fun addIndention() {
        if (indention > 0) {
            val indentionExcludingFirstStep: Long = indention - INDENTION_PRINTING_OFFSET


            Stream.generate({ "  " })
                    .limit(indentionExcludingFirstStep)
                    .forEach(out::print)

            out.print("+ ")
        }
    }

    private fun addPropertyValueIfPresent(node: PropertyNode) {
        val property = node.property//TODO ?: return

        out.print(":")
        out.print(property.valueAsString)
    }

    override fun createChildVisitor(childNode: PropertyNode): PropertyNodeVisitor {
        return withIncreasedIndention()
    }

    private fun withIncreasedIndention(): PrettyPrintingPropertyNodeVisitor {
        val increasedIndention = indention + INDENTION_LEVEL_INCREASE
        return PrettyPrintingPropertyNodeVisitor(out, increasedIndention)
    }
}
