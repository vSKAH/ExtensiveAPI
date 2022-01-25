package fr.skah.skmdl.api.data.sql;

/*
 *  * @Created on 2022 - 16:05
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.skah.skmdl.api.commons.configuration.ConfigurationExporter;
import fr.skah.skmdl.api.data.IDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SQLDataSource implements IDataSource {

    private static SQLDataSource instance;
    private final File configurationFile;
    private HikariDataSource hikariDataSource;

    public SQLDataSource(final File configurationFile) {
        this.configurationFile = configurationFile;
        instance = this;
    }

    @Override
    public void openDataSource() {
        try {
            this.hikariDataSource = new HikariDataSource(new HikariConfig(ConfigurationExporter.createConfig(configurationFile, this.getClass().getResourceAsStream("/hikari.properties"), false).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dataSourceIsOpen() throws SQLException {
        return this.hikariDataSource != null && !this.hikariDataSource.getConnection().isClosed();
    }

    @Override
    public void closeDataSource() {
        try {
            if (this.dataSourceIsOpen()) {
                this.hikariDataSource.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }

    public static SQLDataSource getInstance() {
        return instance;
    }

}
