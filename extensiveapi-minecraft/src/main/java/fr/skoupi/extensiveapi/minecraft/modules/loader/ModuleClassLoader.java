package fr.skoupi.extensiveapi.minecraft.modules.loader;

/*  ModuleClassLoader
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class ModuleClassLoader {

    public Module loadModule(URL url, ModuleOption moduleOptions) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ModuleFinder.URL_INJECTOR.get().addURL(url);
        Class<?> jarClass = Class.forName(moduleOptions.getModuleMainClass(), true, ModuleFinder.URL_INJECTOR.get().getClassLoader());
        Class<? extends Module> moduleClass = jarClass.asSubclass(Module.class);
        return moduleClass.getDeclaredConstructor().newInstance();
    }

}
