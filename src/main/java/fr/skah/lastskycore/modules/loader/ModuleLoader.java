package fr.skah.lastskycore.modules.loader;

/*
 *  * @Created on 2021 - 19:59
 *  * @Project LastSkyCore
 *  * @Author Jimmy
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.skah.lastskycore.LastSkyCore;
import fr.skah.lastskycore.modules.LastModule;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    private static final File MODULES_FOLDER = new File(LastSkyCore.getPlugin(LastSkyCore.class).getDataFolder(), "modules");

    public void registerModule(String moduleName) {
        if (!MODULES_FOLDER.exists()) MODULES_FOLDER.mkdirs();
        LastModule module;
        try {
            module = loadModule(moduleName);
            LastSkyCore.getModulesLoaded().put(module.getModuleOptions().getModuleName(), module);
            module.onRegister();
            module.onEnable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registerModules() {
        if (!MODULES_FOLDER.exists()) MODULES_FOLDER.mkdirs();

        for (File file : Objects.requireNonNull(MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar")))) {
            try {
                LastModule module = loadModule(file.getName());
                LastSkyCore.getModulesLoaded().put(module.getModuleOptions().getModuleName(), module);
                module.onRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private LastModule loadModule(String jarName) throws IOException {

        File moduleFile = new File(MODULES_FOLDER, jarName);
        try {
            ModuleOption moduleOptions = getModuleOption(moduleFile);
            LastModule module = new ModuleClassLoader(moduleFile, getClass().getClassLoader(), Objects.requireNonNull(moduleOptions)).getModule();
            module.setModuleOptions(moduleOptions);
            return module;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ModuleOption getModuleOption(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        JarEntry jarEntry = jarFile.getJarEntry("LastModule.json");
        if (jarEntry == null) return null;
        return new ObjectMapper().readValue(jarFile.getInputStream(jarEntry), ModuleOption.class);
    }

}
