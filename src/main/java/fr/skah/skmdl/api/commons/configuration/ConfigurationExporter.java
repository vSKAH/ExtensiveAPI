package fr.skah.skmdl.api.commons.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ConfigurationExporter {

    public static File createConfig(File targetFile, InputStream in, boolean replace) throws IOException {
        if (replace && targetFile.exists()) {
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else if (!targetFile.exists()) {
            Files.copy(in, targetFile.toPath());
        }
        return targetFile;
    }

    public static File createFile(final File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    public static File createFolders(final File folder) {
        if (!folder.exists()) {
            folder.getParentFile().mkdirs();
        }
        return folder;
    }

    public static File createFolder(final File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}