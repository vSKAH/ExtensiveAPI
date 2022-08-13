package fr.skah.skmdl.api.spigot.common.modules.loader;

/*
 *  * @Created on 2021 - 20:02
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.modules.models.ModuleOption;
import lombok.Getter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {

    @Getter
    private final Module module;

   // It's loading the class from the jar file.
   public ModuleClassLoader(File moduleFile, ClassLoader parent, ModuleOption moduleOptions) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        super(new URL[]{moduleFile.toURI().toURL()}, parent);
            Class<?> jarClass = Class.forName(moduleOptions.getModuleMainClass(), true, this);
            Class<? extends Module> moduleClass;
            moduleClass = jarClass.asSubclass(Module.class);
            module = moduleClass.getDeclaredConstructor().newInstance();
    }

}
