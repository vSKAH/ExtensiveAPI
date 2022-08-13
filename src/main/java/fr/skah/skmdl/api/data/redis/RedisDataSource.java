package fr.skah.skmdl.api.data.redis;

/*
 *  * @Created on 2022 - 15:45
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.data.IDataSource;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

public class RedisDataSource implements IDataSource {

    @Getter
    private static RedisDataSource instance;
    private final File redissonConfigurationFile;
    @Getter
    private RedissonClient redissonClient;

    public RedisDataSource(File redissonConfigurationFile) {
        instance = this;
        this.redissonConfigurationFile = redissonConfigurationFile;
    }

    /**
     * > This function opens a connection to the Redis server using the configuration file specified in the
     * `redissonConfigurationFile` variable
     */
    @Override
    public void openDataSource() throws IOException {
        final Config config = Config.fromYAML(redissonConfigurationFile);
        redissonClient = Redisson.create(config);
    }

    /**
     * > If the Redisson client is not shutting down, is not shutdown, and is not null, then the data source is open
     *
     * @return A boolean value.
     */
    @Override
    public boolean dataSourceIsOpen() {
        return !redissonClient.isShuttingDown() && !redissonClient.isShutdown() && redissonClient != null;
    }

    @Override
    public void closeDataSource() {
        if (dataSourceIsOpen()) redissonClient.shutdown();
    }



}
