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

    /**
     * @param mainClass The main class of the application / plugin (Spigot, BungeeCoord, ...)
     */
    public DependencyManager(Class<?> mainClass) {
        this.toLoad = new ArrayList<>();

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
     * If the dependency is not already in the list of dependencies to load, add it to the list
     *
     * @param dependency The dependency to load.
     */
    public void preLoad(Dependency dependency) {
        if (!this.toLoad.contains(dependency)) {
            this.toLoad.add(dependency);
        }
    }


    /**
     * Downloads all the dependencies to the specified folder, and then returns the DependencyManager instance.
     *
     * @param libsFolder The folder where the dependencies will be downloaded to.
     * @return The DependencyManager object.
     */
    public DependencyManager dl(File libsFolder) {
        synchronized (this.dependencyDownloader) {
            this.dependencyDownloader.download(this.toLoad, libsFolder, dependencyFile -> {
            });
        }
        return this;
    }

    /**
     * It takes a folder, and injects all the jar files in that folder into the classpath
     *
     * @param jarFilefolder The folder where the jar files are located.
     */
    public void injectJar(File jarFilefolder) {
        File[] files = jarFilefolder.listFiles();
        if (files == null || files.length == 0) return;

        for (File file : files) {
            try {
                this.method.invoke(this.classLoader, file.toURI().toURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
