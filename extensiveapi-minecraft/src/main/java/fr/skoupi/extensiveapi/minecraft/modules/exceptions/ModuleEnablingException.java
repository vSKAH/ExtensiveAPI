package fr.skoupi.extensiveapi.minecraft.modules.exceptions;

/*  ModuleLoadingException
 * By: vSKAH <vskahhh@gmail.com>
 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

public class ModuleEnablingException extends Exception {

    public ModuleEnablingException(String moduleName) {
        super("Module Enabling Error :" + moduleName);
    }

    public ModuleEnablingException(String moduleName, Throwable cause) {
        super("Module Enabling Error :" + moduleName, cause);
    }
}
