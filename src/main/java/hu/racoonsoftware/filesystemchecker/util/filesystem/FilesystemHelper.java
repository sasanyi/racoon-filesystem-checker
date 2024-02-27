package hu.racoonsoftware.filesystemchecker.util.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class contains utility functions for filesystem operations
 */
public final class FilesystemHelper {

    private FilesystemHelper() {
    }

    /**
     * This static method create a catalog of files recursively in a root folder
     * @param path The operation root folder
     * @param extension Optional parameter for filter only specific extension
     * @return A Map with key of the filename and value of teh occurrence
     * @throws IOException
     */
    public static Map<String, Integer> catalogFilesRecursivelyInAFolder(String path, String extension) throws IOException {
        Map<String, Integer> fileCatalog = new HashMap<>();
        try (Stream<Path> walkStream = Files.walk(Paths.get(path))) {
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
                if (extension == null) {
                    fileCatalog.put(f.getFileName().toString(),
                            fileCatalog.getOrDefault(f.getFileName().toString(), 0) + 1);
                    return;
                }

                if (f.toString().endsWith(extension)) {
                    fileCatalog.put(f.getFileName().toString(),
                            fileCatalog.getOrDefault(f.getFileName().toString(), 0) + 1);
                }
            });
        }
        return fileCatalog;
    }
}
