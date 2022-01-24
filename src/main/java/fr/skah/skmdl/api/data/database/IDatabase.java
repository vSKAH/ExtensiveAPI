package fr.skah.skmdl.api.data.database;

/*
 *  * @Created on 2021 - 13:15
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */


public interface IDatabase {
    void open();
    boolean isClosed();
    void close();
}
