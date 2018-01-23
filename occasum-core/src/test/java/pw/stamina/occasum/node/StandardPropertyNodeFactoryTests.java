package pw.stamina.occasum.node;

import org.junit.jupiter.api.BeforeEach;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;

final class StandardPropertyNodeFactoryTests {

    private PropertyNodeFactory factory;

    @BeforeEach
    void setUp() {
        factory = new StandardPropertyNodeFactory();
    }
}
