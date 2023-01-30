package fr.skoupi.extensiveapi.core.mavenresolver;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DependencyManager {

	@SuppressWarnings("Guava")
	private static final Supplier<URLClassLoaderAccess> URL_INJECTOR = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) DependencyManager.class.getClassLoader()));

	//private final URLClassLoader classLoader;
	private final DependencyDownloader dependencyDownloader;
//	private Method method;
	private final List<Dependency> toLoad;

	private final List<String> loaded;

	/**
	 * @param mainClass The main class of the application / plugin (Spigot, BungeeCoord, ...)
	 */
	public DependencyManager (Class<?> mainClass)
	{
		this.toLoad = new ArrayList<>();
		this.loaded = new ArrayList<>();
		this.dependencyDownloader = new DependencyDownloader();
	}

	/**
	 * This function allow to put the dependency in a list before being downloaded in parallel.
	 *
	 * @param dependency The object of the dependency we want to download
	 */
	public void preLoad (Dependency dependency)
	{
		if (!this.toLoad.contains(dependency)) this.toLoad.add(dependency);
	}

	/**
	 * This function start the process to download and inject the dependencies.
	 *
	 * @param libsFolder the folder where the dependency will be placed
	 */
	public DependencyManager dl (File libsFolder)
	{
		synchronized (this.dependencyDownloader)
		{
			this.dependencyDownloader.download(this.toLoad, libsFolder, dependencyFile -> {
			});
		}
		return this;
	}

	public void injectJar (File jarFilefolder)
	{
		try
		{
			if (!jarFilefolder.exists()) return;
			File[] files = jarFilefolder.listFiles(file -> file.getName().endsWith(".jar"));
			if (files == null || files.length == 0) return;
			for (File file : files)
			{
				if (!loaded.contains(file.getName()))
				{
					URL_INJECTOR.get().addURL(file.toURI().toURL());
				//	this.method.invoke(this.classLoader, file.toURI().toURL());
					this.loaded.add(file.getName());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}