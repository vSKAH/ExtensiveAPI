package fr.skah.skmdl.api.commons.mavenresolver;

import fr.skah.skmdl.api.spigot.ModulesPlugin;

import java.net.URL;
import java.net.URLClassLoader;

public class DependencyClassLoader extends URLClassLoader {

    public DependencyClassLoader(URL[] urls) {
        super(urls, ModulesPlugin.getInstance().getClass().getClassLoader());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
