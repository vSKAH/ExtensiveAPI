package fr.skoupi.extensiveapi.minecraft.modules.loader;

/*  ModuleFinder
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleInvalidException;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleFinder {

    // It's creating a new File object that points to the module's folder.
    private static final File MODULES_FOLDER = new File(ModulesPlugin.getPlugin(ModulesPlugin.class).getDataFolder(), "modules");
    public static ModuleClassLoader classLoader = new ModuleClassLoader();


    private static ModuleOption getModuleOption(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            JarEntry jarEntry = jarFile.getJarEntry("Module.json");
            if (jarEntry == null) return null;
            return new ObjectMapper().readValue(jarFile.getInputStream(jarEntry), ModuleOption.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getModuleFile(String jarName) {
        return new File(MODULES_FOLDER, jarName);
    }

    public static Module buildModuleFromFile(String jarName) {

        File moduleFile = getModuleFile(jarName);
        try {
            ModuleOption moduleOptions = getModuleOption(moduleFile);
            if (moduleOptions == null)
                throw new ModuleInvalidException("The module " + jarName + " doesn't contains Module.json !!");

            Module module = classLoader.loadModule(moduleFile.toURI().toURL(), moduleOptions);
            module.setModuleOptions(moduleOptions);
            module.setModuleFileName(jarName);
            return module;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException |
                 NoSuchMethodException | ModuleInvalidException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ConcurrentLinkedQueue<Module> getAllModules() {
        ConcurrentLinkedQueue<Module> modules = new ConcurrentLinkedQueue<>();
        File[] files = MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"));
        if (files != null)
            for (File moduleFile : files) modules.add(buildModuleFromFile(moduleFile.getName()));
        return modules;
    }
}
