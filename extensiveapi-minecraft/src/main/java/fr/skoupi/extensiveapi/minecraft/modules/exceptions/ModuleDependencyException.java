package fr.skoupi.extensiveapi.minecraft.modules.exceptions;

/*  ModuleDependencyException
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI

 * Created At 04/11/2022 15:53:56 */
public class ModuleDependencyException extends Exception {

	public ModuleDependencyException (String moduleName, String dependency)
	{
		super("Impossible de charger la dépendance " + dependency + " pour le module " + moduleName);
	}
}
