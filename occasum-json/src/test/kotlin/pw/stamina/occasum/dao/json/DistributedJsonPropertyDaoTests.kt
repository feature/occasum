package pw.stamina.occasum.dao.json

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import pw.stamina.occasum.PropertyHandle
import pw.stamina.occasum.dao.PropertyDao
import pw.stamina.occasum.node.PropertyNode
import pw.stamina.occasum.node.factory.PropertyNodeFactory
import pw.stamina.occasum.node.visit.PrettyPrintingPropertyNodeVisitor
import pw.stamina.occasum.properties.primitives.BooleanProperty
import pw.stamina.occasum.properties.primitives.DoubleProperty
import pw.stamina.occasum.properties.primitives.IntProperty
import pw.stamina.occasum.registry.PropertyLocatorService
import java.nio.file.FileSystem
import java.nio.file.Files

//TODO: Write better tests
@ExtendWith(MockitoExtension::class)
class DistributedJsonPropertyDaoTests {

    @Mock private lateinit var propertyLocator: PropertyLocatorService
    @Mock private lateinit var handle: PropertyHandle

    private lateinit var dao: PropertyDao

    @BeforeEach
    internal fun setupPropertyLocatorAndCreateDao() {
        MockitoAnnotations.initMocks(this)

        `when`(handle.id).thenReturn("test")
        `when`(handle.name).thenReturn("test")

        val factory = PropertyNodeFactory.standard()
        val root = factory.root(handle)

        val targeting = root.folder("targeting")

        val weNeedToGoDeeper = targeting.folder("nestmedaddy")

        weNeedToGoDeeper.property(BooleanProperty("player", true))
        weNeedToGoDeeper.property(BooleanProperty("animals", true))
        weNeedToGoDeeper.property(BooleanProperty("monsters", true))
        weNeedToGoDeeper.property(BooleanProperty("othershit", true))

        root.property(IntProperty("aps", 7))
        root.property(DoubleProperty("reach", 3.7))
        root.property(BooleanProperty("autoblock", false))

        `when`(propertyLocator.findRootNode(handle)).thenReturn(root)
        `when`<PropertyNode>(propertyLocator.findRootNode("Test")).thenReturn(root)

        dao = DistributedJsonPropertyDao(
                propertyLocator,
                fileSystem!!.getPath("test"))
    }

    @Test
    @Throws(Exception::class)
    fun save_handleWithRegisteredProperties_shouldSerializeAndSaveToFileSystem() {
        dao.save(handle)

        Files.readAllLines(fileSystem!!.getPath("test", "Test.json"))
                .forEach { println(it) }
    }

    @Test
    @Throws(Exception::class)
    fun load_handleWithRegisteredProperties_shouldDeserializeAndLoadFromFileSystem() {
        val savePath = fileSystem!!.getPath("test", "Test.json")
        Files.createDirectories(savePath.parent)
        Files.write(savePath, SERIALIZED_JSON.toByteArray())

        try {
            dao.load(handle)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val visitor = PrettyPrintingPropertyNodeVisitor(System.out)
        propertyLocator.findRootNode(handle)?.apply { this.accept(visitor) }
    }

    companion object {
        private const val SERIALIZED_JSON = "{\n" +
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
                "}"

        private lateinit var fileSystem: FileSystem

        @BeforeAll
        internal fun setupFileSystem() {
            fileSystem = Jimfs.newFileSystem(Configuration.unix())
        }
    }
}
