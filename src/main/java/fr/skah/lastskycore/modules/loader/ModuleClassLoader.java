package fr.skah.lastskycore.modules.loader;

/*
 *  * @Created on 2021 - 20:02
 *  * @Project LastSkyCore
 *  * @Author Jimmy
 */

import fr.skah.lastskycore.modules.LastModule;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {

    private final LastModule module;

   public ModuleClassLoader(File moduleFile, ClassLoader parent, ModuleOption moduleOptions) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(new URL[]{moduleFile.toURI().toURL()}, parent);
            Class<?> jarClass = Class.forName(moduleOptions.getModuleMainClass(), true, this);
            Class<? extends LastModule> moduleClass;
            moduleClass = jarClass.asSubclass(LastModule.class);
            module = moduleClass.newInstance();
    }

    public LastModule getModule() {
        return module;
    }

}
