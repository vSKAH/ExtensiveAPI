package fr.skoupi.extensiveapi.minecraft.modules.exceptions;

/*  InvalidModuleException
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

public class ModuleInvalidException extends Exception {

    public ModuleInvalidException(String message) {
        super(message);
    }

    public ModuleInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModuleInvalidException(Throwable cause) {
        super(cause);
    }

    protected ModuleInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
