package fr.skah.skmdl.api.data.sql;

/*
 *  * @Created on 2022 - 16:05
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.skah.skmdl.api.commons.configuration.ConfigurationExporter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SQLManager {

    private static SQLManager INSTANCE;
    private HikariDataSource hikariDataSource;

    public SQLManager() {
        SQLManager.INSTANCE = this;
    }

    public void setupDatasource(final File configurationFile) {
        try {
            this.hikariDataSource = new HikariDataSource(new HikariConfig(ConfigurationExporter.createConfig(configurationFile, this.getClass().getResourceAsStream("/hikari.properties"), false).getPath()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeDatasource() {
        if (!this.datasourceIsClosed()) {
            this.hikariDataSource.close();
        }
    }

    private boolean datasourceIsClosed() {
        try {
            return this.hikariDataSource == null || this.hikariDataSource.getConnection().isClosed();
        }
        catch (SQLException e) {
            return false;
        }
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }

    public static SQLManager getInstance() {
        return SQLManager.INSTANCE;
    }

}
