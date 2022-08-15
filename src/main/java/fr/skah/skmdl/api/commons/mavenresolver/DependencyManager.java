package fr.skah.skmdl.api.commons.mavenresolver;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DependencyManager {

    private final URLClassLoader classLoader;
    private final DependencyDownloader dependencyDownloader;
    private Method method;
    private final List<Dependency> toLoad;

    private final List<String> loaded;

    /**
     * @param mainClass The main class of the application / plugin (Spigot, BungeeCoord, ...)
     */
    public DependencyManager(Class<?> mainClass) {
        this.toLoad = new ArrayList<>();
        this.loaded = new ArrayList<>();

        if (mainClass.getClassLoader() instanceof URLClassLoader) {
            this.classLoader = (URLClassLoader) mainClass.getClassLoader();
        } else {
            throw new ClassCastException("Error while loading URLClassLoader");
        }
        this.dependencyDownloader = new DependencyDownloader();

        try {
            this.method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            this.method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function allow to put the dependency in a list before being downloaded in parallel.
     *
     * @param dependency The object of the dependency we want to download
     */
    public void preLoad(Dependency dependency) {
        if (!this.toLoad.contains(dependency)) {
            this.toLoad.add(dependency);
        }
    }

    /**
     * This function start the process to download and inject the dependencies.
     *
     * @param libsFolder the folder where the dependency will be placed
     */
    public DependencyManager dl(File libsFolder) {
        synchronized (this.dependencyDownloader) {
            this.dependencyDownloader.download(this.toLoad, libsFolder, dependencyFile -> {
            });
        }
        return this;
    }

    public void injectJar(File jarFilefolder) {
        try {
            for (File file : jarFilefolder.listFiles()) {
                if (!loaded.contains(file.getName())) {
                    this.method.invoke(this.classLoader, file.toURI().toURL());
                    this.loaded.add(file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}