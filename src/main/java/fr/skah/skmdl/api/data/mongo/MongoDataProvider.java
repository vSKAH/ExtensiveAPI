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

    public Document syncGetDocumentFromUniqueId(UUID uniqueId) {
        return mongoCollection.find(Filters.eq("uuid", uniqueId.toString())).first();
    }

    public CompletableFuture<Document> asyncGetDocumentFromUniqueId(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> syncGetDocumentFromUniqueId(uniqueId), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public Boolean syncInsertDocument(Document document) {
        return mongoCollection.insertOne(document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncInsertDocument(Document document) {
        return CompletableFuture.supplyAsync(() -> syncInsertDocument(document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public boolean syncReplaceDocument(UUID uniqueId, Document document) {
        return mongoCollection.replaceOne(Filters.eq("uuid", uniqueId.toString()), document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncReplaceDocument(UUID uniqueId, Document document) {
        return CompletableFuture.supplyAsync(() -> syncReplaceDocument(uniqueId, document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public boolean syncDeleteDocument(UUID uniqueId) {
        return mongoCollection.deleteOne(Filters.eq("uuid", uniqueId.toString())).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncDeleteDocument(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> mongoCollection.deleteOne(Filters.eq("uuid", uniqueId.toString())).wasAcknowledged());
    }


    public MongoCursor<Document> syncGetAllDocuments() {
        return mongoCollection.find().iterator();
    }

    public CompletableFuture<MongoCursor<Document>> asyncGetAllDocuments() {
        return CompletableFuture.supplyAsync(this::syncGetAllDocuments, ModuleScheduler.EXECUTOR_SERVICE);
    }

}
