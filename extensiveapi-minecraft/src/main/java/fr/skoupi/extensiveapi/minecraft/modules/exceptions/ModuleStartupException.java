package fr.skoupi.extensiveapi.minecraft.modules.exceptions;

/*  ModuleLoadingException
 * By: vSKAH <vskahhh@gmail.com>
 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

public class ModuleStartupException extends Exception {

    public ModuleStartupException(String moduleName) {
        super("Module Startup Error :" + moduleName);
    }

    public ModuleStartupException(String moduleName, Throwable cause) {
        super("Module Startup Error :" + moduleName, cause);
    }}
