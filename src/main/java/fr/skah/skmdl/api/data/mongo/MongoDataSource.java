package fr.skah.skmdl.api.data.mongo;

/*
 *  * @Created on 15/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import fr.skah.skmdl.api.data.IDataSource;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;


@Getter
public class MongoDataSource implements IDataSource {

    @Getter
    private static MongoDataSource instance;

    private MongoClient mongoClient;

    private final HashMap<String, MongoDatabase> mongoDatabases = new HashMap<>();


    public MongoDataSource() {
        instance = this;
    }

    @Override
    public void openDataSource() {
        MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();
        mongoClientSettingsBuilder.applicationName("SKMDL_PLUGIN:" + Bukkit.getServer().getName() + ':' + Bukkit.getServer().getIp());
        mongoClientSettingsBuilder.applyToConnectionPoolSettings(builder -> builder.minSize(10).maxSize(1000));
        mongoClientSettingsBuilder.applyConnectionString(new ConnectionString("mongodb://host:port/"));
        mongoClient = MongoClients.create(MongoClientSettings.builder().build());
    }

    @Override
    public boolean dataSourceIsOpen() {
        return mongoClient != null;
    }

    @Override
    public void closeDataSource() {
        mongoClient.close();
    }

    public void registerMongoDatabase(String databaseName) {
        mongoDatabases.put(databaseName, mongoClient.getDatabase(databaseName));
    }

    public MongoDatabase getMongoDatabase(String databaseName) {
        return mongoDatabases.get(databaseName);
    }
}
