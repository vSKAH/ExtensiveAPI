package fr.skah.skmdl.api.spigot.common.modules.exceptions;

/*
 *  * @Created on 15/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
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
