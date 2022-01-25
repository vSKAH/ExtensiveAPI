package fr.skah.skmdl.api.data.redis;

/*
 *  * @Created on 2022 - 15:45
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.data.IDataSource;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

public class RedisDataSource implements IDataSource {

    private static RedisDataSource instance;
    private final File redissonConfigurationFile;

    private RedissonClient redissonClient;

    public RedisDataSource(File redissonConfigurationFile) {
        instance = this;
        this.redissonConfigurationFile = redissonConfigurationFile;
    }

    @Override
    public void openDataSource() throws IOException {
        final Config config = Config.fromYAML(redissonConfigurationFile);
        redissonClient = Redisson.create(config);
    }

    @Override
    public boolean dataSourceIsOpen() {
        return !redissonClient.isShuttingDown() && !redissonClient.isShutdown() && redissonClient != null;
    }

    @Override
    public void closeDataSource() {
        if (dataSourceIsOpen()) redissonClient.shutdown();
    }


    public static RedisDataSource getInstance() {
        return instance;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
}
