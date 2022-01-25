package fr.skah.skmdl.api.data;

/*
 *  * @Created on 2021 - 13:15
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */


import java.io.IOException;
import java.sql.SQLException;

public interface IDataSource {
    void openDataSource() throws IOException;
    boolean dataSourceIsOpen() throws SQLException;
    void closeDataSource();
}
