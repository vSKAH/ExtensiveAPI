package fr.skoupi.extensiveapi.databases.mongodb.accounts.exception;

/*  AccountEmptyDocumentException
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

/**
 * > This class is an exception that is thrown when an account document is empty
 */
public class AccountEmptyDocumentException extends Exception {

    public AccountEmptyDocumentException(String message) {
        super(message);
    }
}
