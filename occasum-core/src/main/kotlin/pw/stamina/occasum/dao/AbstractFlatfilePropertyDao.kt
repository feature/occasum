package pw.stamina.occasum.dao

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path

abstract class AbstractFlatfilePropertyDao : FlatfilePropertyDao {
    companion object {
        //TODO: Add

        @Throws(IOException::class)
        fun writeBytesToLocation(bytes: ByteArray, location: Path) {
            Files.write(location, bytes)
        }

        // This doesn't need to be closed
        @Throws(IOException::class)
        fun readBytesFromLocation(location: Path): Reader {
            return InputStreamReader(ByteArrayInputStream(Files.readAllBytes(location)))
        }
    }
}
