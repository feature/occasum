package pw.stamina.occasum.dao

import pw.stamina.occasum.PropertyHandle
import java.nio.file.Path

interface FlatfilePropertyDao : PropertyDao {

    fun saveAll(destination: Path): Iterable<Exception>

    @Throws(Exception::class)
    fun save(handle: PropertyHandle, destination: Path)

    fun loadAll(source: Path): Iterable<Exception>

    @Throws(Exception::class)
    fun load(handle: PropertyHandle, destination: Path)
}

