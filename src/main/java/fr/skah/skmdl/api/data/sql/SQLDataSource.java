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
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SQLDataSource implements IDataSource {

    @Getter
    private static SQLDataSource instance;
    private final File configurationFile;
    @Getter
    private HikariDataSource hikariDataSource;

    public SQLDataSource(final File configurationFile) {
        this.configurationFile = configurationFile;
        instance = this;
    }

    /**
     * This function opens a data source using the HikariCP library.
     */
    @Override
    public void openDataSource() throws IOException {
        this.hikariDataSource = new HikariDataSource(new HikariConfig(ConfigurationExporter.createConfig(configurationFile, this.getClass().getResourceAsStream("/hikari.properties"), false).getPath()));
    }


    @Override
    // It checks if the data source is open or not.
    public boolean dataSourceIsOpen() {
        try {
            return this.hikariDataSource != null && !this.hikariDataSource.getConnection().isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * > This function closes the data source if it is open
     */
    @Override
    public void closeDataSource() {
        if (this.dataSourceIsOpen()) this.hikariDataSource.close();
    }

}
