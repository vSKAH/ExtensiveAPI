package fr.skah.skmdl.api.config;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigurationExporter {

    public static File createConfig(@NotNull Path path, @NotNull String fileName, @NotNull Class classz) throws IOException {
        File folder = createFolder(path);
        File file = new File(folder, fileName + ".yml");

        if (!file.exists()) {
            InputStream input = classz.getResourceAsStream("/" + file.getName());
            if (input != null) Files.copy(input, file.toPath());
        }
        return file;
    }

    public static File createFolder(@NotNull Path path) {
        File folder = path.toFile();
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }



}
