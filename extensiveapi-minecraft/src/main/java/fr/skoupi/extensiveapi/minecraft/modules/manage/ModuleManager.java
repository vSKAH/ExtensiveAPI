package fr.skoupi.extensiveapi.minecraft.modules.manage;

/*  ModuleManager
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.google.common.collect.Maps;
import fr.skoupi.extensiveapi.minecraft.ModulesPlugin;
import fr.skoupi.extensiveapi.minecraft.modules.enums.ModuleState;
import fr.skoupi.extensiveapi.minecraft.modules.exceptions.ModuleDependencyException;
import fr.skoupi.extensiveapi.minecraft.modules.models.Module;
import fr.skoupi.extensiveapi.minecraft.modules.models.ModuleOption;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentMap;

public class ModuleManager {

	@Getter
	private static final ConcurrentMap<String, Module> modules = Maps.newConcurrentMap();

	/**
	 * It registers a module, calls the onStartup() function, and loads the module
	 *
	 * @param module The module to register.
	 */
	public static void registerModule (Module module)
	{
		if (module != null)
		{
			String moduleName = module.getModuleOptions().getModuleName();

			modules.put(moduleName, module);
			try
			{
				if (loadDependency(moduleName))
				{
					module.onStartup();
					module.onEnable();
				}
			} catch (ModuleDependencyException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * If the module is not loaded, it checks if all the dependencies are loaded, if they are, it loads the module
	 *
	 * @param moduleName The name of the module to load.
	 */
	private static boolean loadDependency (String moduleName) throws ModuleDependencyException
	{
		Module module = modules.get(moduleName);
		for (String pluginDependency : module.getModuleOptions().getPluginDependencies())
		{
			Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginDependency);
			if (plugin == null || !plugin.isEnabled())
				throw new ModuleDependencyException(moduleName, pluginDependency);
		}
		ModulesPlugin.getInstance().getLogger().info("Le module " + moduleName + " a pu charger toutes ses dépendances  !");
		return true;
	}


	/**
	 * It takes a module name as a parameter, gets the module from the module manager, gets the module options, checks if
	 * the module can be disabled, checks if the module is enabled, and if it is, unloads it, otherwise loads it
	 *
	 * @param moduleName The name of the module you want to change the state of.
	 * @return The module that was changed.
	 */
	public static Module changeModuleState (String moduleName)
	{
		Module module = ModuleManager.getModules().get(moduleName);
		ModuleOption moduleOption = module.getModuleOptions();
		if (!moduleOption.isCanBeDisabled()) return module;
		if (module.getModuleState() == ModuleState.ENABLED) modules.get(moduleName).onDisable();
		else if (module.getModuleState() == ModuleState.DISABLED)
		{
			try
			{
				if (ModuleManager.loadDependency(moduleOption.getModuleName())) module.onEnable();
			} catch (ModuleDependencyException e)
			{
				throw new RuntimeException(e);
			}
		}
		return module;
	}

}
