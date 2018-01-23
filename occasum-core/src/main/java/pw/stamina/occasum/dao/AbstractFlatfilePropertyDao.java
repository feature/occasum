package pw.stamina.occasum.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractFlatfilePropertyDao implements FlatfilePropertyDao {
    //TODO: Add

    protected static void writeBytesToLocation(byte[] bytes, Path location) throws IOException {
        Files.write(location, bytes);
    }

    // This doesn't need to be closed
    protected static Reader readBytesFromLocation(Path location) throws IOException {
        return new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(location)));
    }
}
