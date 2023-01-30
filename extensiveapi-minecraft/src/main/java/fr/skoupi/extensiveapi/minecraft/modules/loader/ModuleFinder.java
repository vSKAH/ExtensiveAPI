package fr.skoupi.extensiveapi.minecraft.modules.loader;

/*  ModuleFinder
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import fr.skoupi.extensiveapi.core.mavenresolver.URLClassLoaderAccess;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.InvalidModuleException;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleFinder {

	// It's creating a new File object that points to the module's folder.
	private static final File MODULES_FOLDER = new File(ModulesPlugin.getPlugin(ModulesPlugin.class).getDataFolder(), "modules");
	@SuppressWarnings("Guava")
	public static final Supplier<URLClassLoaderAccess> URL_INJECTOR = Suppliers.memoize(() -> URLClassLoaderAccess.create((URLClassLoader) ModulesPlugin.class.getClassLoader()));

	public static ModuleClassLoader classLoader = new ModuleClassLoader();


	private static ModuleOption getModuleOption (File file)
	{
		try (JarFile jarFile = new JarFile(file))
		{
			JarEntry jarEntry = jarFile.getJarEntry("Module.json");
			if (jarEntry == null) return null;
			return new ObjectMapper().readValue(jarFile.getInputStream(jarEntry), ModuleOption.class);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

/*	public static CompletableFuture<Void> reloadClassLoader ()
	{
		return CompletableFuture.runAsync(() -> {
			classLoader = new ModuleClassLoader(new URL[]{});
			File[] files = MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"));
			if (files != null && files.length > 0)
				for (File moduleFile : files) getModuleFromFile(moduleFile.getName());
		});

	}

 */

	public static Module getModuleFromFile (String jarName)
	{

		File moduleFile = new File(MODULES_FOLDER, jarName);
		try
		{
			ModuleOption moduleOptions = getModuleOption(moduleFile);
			if (moduleOptions == null)
				throw new InvalidModuleException("The module " + jarName + " doesn't contains Module.json !!");

			Module module = classLoader.loadModule(moduleFile.toURI().toURL(), moduleOptions);
			module.setModuleOptions(moduleOptions);
			module.setModuleFileName(jarName);
			return module;
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException |
		         NoSuchMethodException | InvalidModuleException | MalformedURLException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public static List<Module> getAllModules ()
	{
		List<Module> modules = new ArrayList<>();
		File[] files = MODULES_FOLDER.listFiles(file -> file.getName().endsWith(".jar"));
		if (files != null && files.length > 0)
			for (File moduleFile : files) modules.add(getModuleFromFile(moduleFile.getName()));
		return modules;
	}
}
