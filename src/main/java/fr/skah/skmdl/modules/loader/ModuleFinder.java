package fr.skah.skmdl.modules.loader;

/*
 *  * @Created on 2021 - 19:59
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.skah.skmdl.ModulesPlugin;
import fr.skah.skmdl.modules.models.Module;
import fr.skah.skmdl.modules.models.ModuleOption;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleFinder {

    private static final File MODULES_FOLDER = new File(ModulesPlugin.getPlugin(ModulesPlugin.class).getDataFolder(), "modules");

    public static List<Module> getAllModules() {
        if (!MODULES_FOLDER.exists()) MODULES_FOLDER.mkdirs();
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
            Module module = new ModuleClassLoader(moduleFile, ModuleFinder.class.getClassLoader(), moduleOptions).getModule();
            module.setModuleOptions(moduleOptions);
            return module;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | InvocationTargetException | NoSuchMethodException e) {
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
