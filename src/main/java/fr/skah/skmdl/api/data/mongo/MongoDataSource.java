package fr.skah.skmdl.api.data.mongo;

/*
 *  * @Created on 15/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import fr.skah.skmdl.api.data.IDataSource;
import lombok.Getter;

import java.util.HashMap;


@Getter
public class MongoDataSource implements IDataSource {

    @Getter
    private static MongoDataSource instance;

    private MongoClient mongoClient;

    private final HashMap<String, MongoDatabase> mongoDatabases = new HashMap<>();

    private final String mongoHostname;


    public MongoDataSource(String mongoHostname) {
        instance = this;
        this.mongoHostname = mongoHostname;
    }


    /**
     * Create a MongoClientSettings object, set the application name, set the connection pool settings, and create a
     * MongoClient object.
     */
    @Override
    public void openDataSource() {
        mongoClient = MongoClients.create("mongodb://" + mongoHostname + "/");
    }

    @Override
    public boolean dataSourceIsOpen() {
        return mongoClient != null;
    }

    @Override
    public void closeDataSource() {
        mongoClient.close();
    }

    /**
     * This function takes a database name as a parameter and adds it to the mongoDatabases map.
     *
     * @param databaseName The name of the database you want to register.
     */
    public void registerMongoDatabase(String databaseName) {
        mongoDatabases.put(databaseName, mongoClient.getDatabase(databaseName));
    }

    /**
     * If the database is already in the map, return it. Otherwise, create a new database and add it to the map.
     *
     * @param databaseName The name of the database you want to connect to.
     * @return A MongoDatabase object
     */
    public MongoDatabase getMongoDatabase(String databaseName) {
        return mongoDatabases.get(databaseName);
    }
}
