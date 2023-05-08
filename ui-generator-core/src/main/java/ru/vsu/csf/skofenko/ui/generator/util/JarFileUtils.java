package ru.vsu.csf.skofenko.ui.generator.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

@UtilityClass
public class JarFileUtils {
    @SneakyThrows
    public static void copyFromJar(String source, Path target) {
        URI resource = JarFileUtils.class.getResource("").toURI();
        try (FileSystem fileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap())) {
            Path jarPath = fileSystem.getPath(source);
            Files.walkFileTree(jarPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path currentTarget = target.resolve(jarPath.relativize(dir).toString());
                    Files.createDirectories(currentTarget);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
