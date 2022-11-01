package fr.skoupi.extensiveapi.minecraft.modules.loader;

/*  ModuleClassLoader
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {

    public ModuleClassLoader(URL[] urls) {
        super(urls, ModulesPlugin.getInstance().getClass().getClassLoader());
    }

    public Module loadModule(URL url, ModuleOption moduleOptions) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        addURL(url);
        Class<?> jarClass = Class.forName(moduleOptions.getModuleMainClass(), true, this);
        Class<? extends Module> moduleClass = jarClass.asSubclass(Module.class);
        return moduleClass.getDeclaredConstructor().newInstance();
    }

}
