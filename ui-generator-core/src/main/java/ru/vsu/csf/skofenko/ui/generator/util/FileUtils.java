package ru.vsu.csf.skofenko.ui.generator.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

@UtilityClass
public class FileUtils {
    @SneakyThrows
    public static void copyTo(String source, Path target) {
        URI resource = FileUtils.class.getResource("").toURI();
        FileSystem fileSystem = null;
        Path filePath;
        if (resource.toString().contains("jar")) {
            fileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap());
            filePath = fileSystem.getPath(source);
        } else {
            filePath = Paths.get(FileUtils.class.getResource(source).toURI());
        }
        Files.walkFileTree(filePath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path currentTarget = target.resolve(filePath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(filePath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
        if (fileSystem != null) {
            fileSystem.close();
        }
    }
}
