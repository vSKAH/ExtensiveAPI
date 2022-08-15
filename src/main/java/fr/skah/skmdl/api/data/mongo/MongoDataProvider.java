package fr.skah.skmdl.api.data.mongo;

/*
 *  * @Created on 15/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import fr.skah.skmdl.api.commons.async.ModuleScheduler;
import lombok.Getter;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public abstract class MongoDataProvider {

    private final String databaseName;
    private final String collectionName;

    private final MongoCollection<Document> mongoCollection;

    public MongoDataProvider(String databaseName, String collectionName) {
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.mongoCollection = MongoDataSource.getInstance().getMongoDatabase(databaseName).getCollection(collectionName);
    }

    public Document getSyncDocumentFromUniqueId(UUID uniqueId) {
        return mongoCollection.find(Filters.eq("uuid", uniqueId.toString())).first();
    }

    public CompletableFuture<Document> getAsyncDocumentFromUniqueId(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> getSyncDocumentFromUniqueId(uniqueId), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public Boolean insertSyncDocument(Document document) {
        return mongoCollection.insertOne(document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> insertAsyncDocument(Document document) {
        return CompletableFuture.supplyAsync(() -> insertSyncDocument(document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public boolean replaceSyncDocument(UUID uuid, Document document) {
        return mongoCollection.replaceOne(Filters.eq("uuid", uuid.toString()), document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> replaceAsyncDocument(UUID uuid, Document document) {
        return CompletableFuture.supplyAsync(() -> replaceSyncDocument(uuid, document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public MongoCursor<Document> getSyncAllDocuments() {
        return mongoCollection.find().iterator();
    }

    public CompletableFuture<MongoCursor<Document>> getAsyncAllDocuments() {
        return CompletableFuture.supplyAsync(this::getSyncAllDocuments, ModuleScheduler.EXECUTOR_SERVICE);

    }

}
