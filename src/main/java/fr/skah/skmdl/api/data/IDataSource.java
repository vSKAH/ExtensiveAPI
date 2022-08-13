package fr.skah.skmdl.api.data;

/*
 *  * @Created on 2021 - 13:15
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */


import java.io.IOException;
import java.sql.SQLException;

public interface IDataSource {
    /**
     * This function opens a data source and throws an IOException if it fails.
     */
    void openDataSource() throws IOException;
    /**
     * > Returns true if the data source is open, false otherwise
     *
     * @return A boolean value.
     */
    boolean dataSourceIsOpen() throws SQLException;
    /**
     * Closes the data source
     */
    void closeDataSource();
}
