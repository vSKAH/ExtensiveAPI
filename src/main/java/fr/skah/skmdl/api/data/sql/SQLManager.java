package fr.skah.skmdl.api.data.sql;

/*
 *  * @Created on 2022 - 16:05
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.skah.skmdl.api.commons.configuration.ConfigurationExporter;
import fr.skah.skmdl.api.data.database.IDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SQLManager implements IDatabase {

    private static SQLManager INSTANCE;
    private final File configurationFile;
    private HikariDataSource hikariDataSource;

    public SQLManager(final File configurationFile)
    {
        this.configurationFile = configurationFile;
        SQLManager.INSTANCE = this;
    }

    @Override
    public void open() {
        try {
            this.hikariDataSource = new HikariDataSource(new HikariConfig(ConfigurationExporter.createConfig(configurationFile, this.getClass().getResourceAsStream("/hikari.properties"), false).getPath()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean isClosed() {
        try {
            return this.hikariDataSource == null || this.hikariDataSource.getConnection().isClosed();
        }
        catch (SQLException e) {
            return false;
        }    }

    @Override
    public void close() {
        if (!this.isClosed()) {
            this.hikariDataSource.close();
        }
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }

    public static SQLManager getInstance() {
        return SQLManager.INSTANCE;
    }

}
