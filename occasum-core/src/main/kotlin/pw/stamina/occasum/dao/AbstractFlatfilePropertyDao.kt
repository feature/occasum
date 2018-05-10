package pw.stamina.occasum.dao

import pw.stamina.occasum.PropertyHandle
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path

abstract class AbstractFlatfilePropertyDao(private val location: Path) : FlatfilePropertyDao {

    @Throws(Exception::class)
    override fun save(handle: PropertyHandle) {
        val handlePath = resolveHandlePath(handle)
        save(handle, handlePath)
    }

    override fun saveAll() = saveAll(location)

    @Throws(Exception::class)
    override fun load(handle: PropertyHandle) {
        val handlePath = resolveHandlePath(handle)
        load(handle, handlePath)
    }

    override fun loadAll() = saveAll(location)

    protected abstract fun resolveHandlePath(handle: PropertyHandle): Path

    protected companion object {

        @Throws(IOException::class)
        fun writeBytesToLocation(bytes: ByteArray, location: Path) {
            Files.write(location, bytes)
        }

        @Throws(IOException::class)
        fun streamBytesFromLocation(location: Path): Reader {
            return InputStreamReader(ByteArrayInputStream(Files.readAllBytes(location)))
        }
    }
}
