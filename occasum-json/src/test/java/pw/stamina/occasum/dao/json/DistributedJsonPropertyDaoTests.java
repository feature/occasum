package pw.stamina.occasum.dao.json;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pw.stamina.occasum.PropertyHandle;
import pw.stamina.occasum.dao.PropertyDao;
import pw.stamina.occasum.node.PropertyNode;
import pw.stamina.occasum.node.factory.PropertyNodeFactory;
import pw.stamina.occasum.node.visit.PrettyPrintingPropertyNodeVisitor;
import pw.stamina.occasum.node.visit.PropertyNodeVisitor;
import pw.stamina.occasum.properties.primitives.BooleanProperty;
import pw.stamina.occasum.properties.primitives.DoubleProperty;
import pw.stamina.occasum.properties.primitives.IntProperty;
import pw.stamina.occasum.registry.PropertyLocatorService;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//TODO: Write better tests
public final class DistributedJsonPropertyDaoTests {
    private static final String serializedJson =
            "{\n" +
            "  \"targeting\": {\n" +
            "    \"nestmedaddy\": {\n" +
            "      \"player\": \"true\",\n" +
            "      \"animals\": \"false\",\n" +
            "      \"monsters\": \"true\",\n" +
            "      \"othershit\": \"false\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"lolderp\": \"0\",\n" +
            "  \"autoblock\": \"true\"\n" +
            "}";

    private static FileSystem fileSystem;

    @Mock
    private PropertyLocatorService propertyLocator;
    @Mock
    private PropertyHandle handle;
    private PropertyDao dao;

    @BeforeAll
    static void setupFileSystem() {
        fileSystem = Jimfs.newFileSystem(Configuration.unix());
    }

    @BeforeEach
    void setupPropertyLocatorAndCreateDao() {
        MockitoAnnotations.initMocks(this);

        when(handle.getId()).thenReturn("test");
        when(handle.getName()).thenReturn("test");

        PropertyNodeFactory factory = PropertyNodeFactory.standard();
        PropertyNode root = factory.root(handle);

        PropertyNode targeting = root.folder("targeting");

        PropertyNode weNeedToGoDeeper =
                targeting.folder("nestmedaddy");

        weNeedToGoDeeper.property(BooleanProperty.from("player", true));
        weNeedToGoDeeper.property(BooleanProperty.from("animals", true));
        weNeedToGoDeeper.property(BooleanProperty.from("monsters", true));
        weNeedToGoDeeper.property(BooleanProperty.from("othershit", true));

        root.property(IntProperty.from("aps", 7));
        root.property(DoubleProperty.from("reach", 3.7));
        root.property(BooleanProperty.from("autoblock", false));

        Optional<PropertyNode> wrappedRoot = Optional.of(root);
        when(propertyLocator.findRootNode(handle)).thenReturn(wrappedRoot);
        when(propertyLocator.findRootNode("Test")).thenReturn(wrappedRoot);

        dao = new DistributedJsonPropertyDao(
                propertyLocator,
                fileSystem.getPath("test"));
    }

    @Test
    public void save_handleWithRegisteredProperties_shouldSerializeAndSaveToFileSystem() throws Exception {
        dao.save(handle);

        Files.readAllLines(fileSystem.getPath("test", "Test.json")).forEach(System.out::println);
    }

    @Test
    public void load_handleWithRegisteredProperties_shouldDeserializeAndLoadFromFileSystem() throws Exception {
        Path savePath = fileSystem.getPath("test", "Test.json");
        Files.createDirectories(savePath.getParent());
        Files.write(savePath, serializedJson.getBytes());

        dao.load(handle).forEach(Exception::printStackTrace);

        PropertyNodeVisitor visitor = new PrettyPrintingPropertyNodeVisitor(System.out);
        propertyLocator.findRootNode(handle).ifPresent(node -> node.accept(visitor));
    }
}
