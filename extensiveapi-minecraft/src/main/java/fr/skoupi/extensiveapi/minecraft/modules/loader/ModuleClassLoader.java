package fr.skoupi.extensiveapi.minecraft.modules.loader;

/*  ModuleClassLoader
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import fr.skoupi.extensiveapi.core.classloader.URLClassLoaderAccess;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader {

    @SuppressWarnings("Guava")
    public static final Supplier<URLClassLoaderAccess> URL_INJECTOR = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) ModulesPlugin.class.getClassLoader()));

    public Module loadModule(URL url, ModuleOption moduleOptions) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        URL_INJECTOR.get().addURL(url);
        Class<?> jarClass = Class.forName(moduleOptions.getModuleMainClass(), true, URL_INJECTOR.get().getClassLoader());
        Class<? extends Module> moduleClass = jarClass.asSubclass(Module.class);
        return moduleClass.getDeclaredConstructor().newInstance();
    }

    public void unloadModule(URL url) {
        URL_INJECTOR.get().removeURL(url);
    }

}
