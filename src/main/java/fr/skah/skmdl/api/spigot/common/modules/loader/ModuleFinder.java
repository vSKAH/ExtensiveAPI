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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleFinder {

    // It's creating a new File object that points to the module's folder.
    private static final File MODULES_FOLDER = new File(ModulesPlugin.getPlugin(ModulesPlugin.class).getDataFolder(), "modules");
    public static ModuleClassLoader classLoader = new ModuleClassLoader(new URL[]{});


    private static ModuleOption getModuleOption(File file) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            JarEntry jarEntry = jarFile.getJarEntry("Module.json");
            if (jarEntry == null) return null;
            return new ObjectMapper().readValue(jarFile.getInputStream(jarEntry), ModuleOption.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public static CompletableFuture reloadClassLoader() {
        return CompletableFuture.runAsync(() -> {
            classLoader = new ModuleClassLoader(new URL[]{});
            for (File moduleFile : MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"))) {
                getModuleFromFile(moduleFile.getName());
            }
        });

    }

    public static Module getModuleFromFile(String jarName) {

        File moduleFile = new File(MODULES_FOLDER, jarName);
        try {
            ModuleOption moduleOptions = getModuleOption(moduleFile);
            if (moduleOptions == null) {
                throw new InvalidModuleException("The module " + jarName + " doesn't contains Module.json !!");
            }

            Module module = classLoader.loadModule(moduleFile.toURI().toURL(), moduleOptions);
            module.setModuleOptions(moduleOptions);
            module.setModuleFileName(jarName);
            return module;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 InvocationTargetException |
                 NoSuchMethodException | InvalidModuleException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        for (File moduleFile : MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"))) {
            modules.add(getModuleFromFile(moduleFile.getName()));
        }
        return modules;
    }
}
