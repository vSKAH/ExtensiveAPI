package fr.skoupi.extensiveapi.minecraft.modules.exceptions;

/*  InvalidModuleException
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

public class InvalidModuleException extends Exception {

    public InvalidModuleException(String message) {
        super(message);
    }

    public InvalidModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidModuleException(Throwable cause) {
        super(cause);
    }

    protected InvalidModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
