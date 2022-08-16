package fr.skah.skmdl.api.commons.mavenresolver;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DependencyManager {

    private final DependencyClassLoader classLoader;
    private final DependencyDownloader dependencyDownloader;
    private final List<Dependency> toLoad;

    private final List<String> loaded;


    public DependencyManager() {
        this.toLoad = new ArrayList<>();
        this.loaded = new ArrayList<>();
        this.classLoader = new DependencyClassLoader(new URL[]{});
        this.dependencyDownloader = new DependencyDownloader();
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
                    this.classLoader.addURL(file.toURI().toURL());
                    this.loaded.add(file.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}