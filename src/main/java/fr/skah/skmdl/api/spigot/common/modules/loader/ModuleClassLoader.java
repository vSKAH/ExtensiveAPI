package fr.skah.skmdl.api.spigot.common.modules.loader;

/*
 *  * @Created on 2021 - 20:02
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import fr.skah.skmdl.api.spigot.ModulesPlugin;
import fr.skah.skmdl.api.spigot.common.modules.models.Module;
import fr.skah.skmdl.api.spigot.common.modules.models.ModuleOption;
import lombok.Getter;

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

    @Override
    protected void addURL(URL url) {
        super.addURL(url);
    }


}
