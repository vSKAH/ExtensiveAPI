package fr.skah.skmdl.api.spigot.common.modules.loader;

/*
 *  * @Created on 2021 - 19:59
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.modules.exceptions.InvalidModuleException;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.modules.models.ModuleOption;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleFinder {

    // It's creating a new File object that points to the modules folder.
    private static final File MODULES_FOLDER = new File(ModulesPlugin.getPlugin(ModulesPlugin.class).getDataFolder(), "modules");
    private static final ModuleClassLoader classLoader;

    static {
        if (!MODULES_FOLDER.exists()) MODULES_FOLDER.mkdirs();
        List<URL> urls = new ArrayList<>();
        for (File file : MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"))) {
            try {
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        classLoader = new ModuleClassLoader(urls.toArray(new URL[0]));
    }

    public static List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        for (File moduleFileName : Objects.requireNonNull(MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar")))) {

            modules.add(getModuleFromFile(moduleFileName.getName()));
        }
        return modules;
    }

    public static Module getModuleFromFile(String jarName) {

        File moduleFile = new File(MODULES_FOLDER, jarName);
        try {
            ModuleOption moduleOptions = getModuleOption(moduleFile);
            if (moduleOptions == null) {
                throw new InvalidModuleException("The module " + jarName + " doesn't contains Module.json !!");
            }
            Module module = classLoader.loadModule(moduleOptions);
            module.setModuleOptions(moduleOptions);
            module.setModuleFileName(jarName);
            return module;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 InvocationTargetException | NoSuchMethodException | InvalidModuleException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ModuleOption getModuleOption(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            JarEntry jarEntry = jarFile.getJarEntry("Module.json");
            if (jarEntry == null) return null;
            return new ObjectMapper().readValue(jarFile.getInputStream(jarEntry), ModuleOption.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
