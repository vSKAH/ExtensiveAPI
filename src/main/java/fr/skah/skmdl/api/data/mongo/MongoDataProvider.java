package fr.skah.skmdl.api.data.mongo;

/*
 *  * @Created on 15/08/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.InsertManyResult;
import fr.skah.skmdl.api.commons.async.ModuleScheduler;
import lombok.Getter;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Getter
public abstract class MongoDataProvider {

    // It's just a variable declaration.
    private final String databaseName;
    private final String collectionName;

    private final MongoCollection<Document> mongoCollection;

    // It's the constructor of the class.
    public MongoDataProvider(String databaseName, String collectionName) {
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.mongoCollection = MongoDataSource.getInstance().getMongoDatabase(databaseName).getCollection(collectionName);
    }


    public Document syncGetDocumentFromFilter(Bson filter) {
        return mongoCollection.find(filter).first();
    }

    public CompletableFuture<Document> asyncGetDocumentFromUniqueId(Bson filter) {
        return CompletableFuture.supplyAsync(() -> syncGetDocumentFromFilter(filter), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public boolean syncInsertDocument(Document document) {
        return mongoCollection.insertOne(document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncInsertDocument(Document document) {
        return CompletableFuture.supplyAsync(() -> syncInsertDocument(document), ModuleScheduler.EXECUTOR_SERVICE);
    }


    public void syncInsertMultipleDocuments(List<Document> documents, boolean ordered, BiConsumer<Boolean, Map<Integer, BsonValue>> consumer) {
        InsertManyResult result = mongoCollection.insertMany(documents, new InsertManyOptions().ordered(ordered));
        consumer.accept(result.wasAcknowledged(), result.getInsertedIds());
    }

    public void asyncInsertMultipleDocuments(List<Document> documents, boolean ordered, BiConsumer<Boolean, Map<Integer, BsonValue>> consumer) {
        CompletableFuture.runAsync(() -> syncInsertMultipleDocuments(documents, ordered, consumer));
    }

    public boolean syncReplaceDocument(Bson filter, Document document) {
        return mongoCollection.replaceOne(filter, document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncReplaceDocument(Bson filter, Document document) {
        return CompletableFuture.supplyAsync(() -> syncReplaceDocument(filter, document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    public boolean syncDeleteDocument(Bson filter) {
        return mongoCollection.deleteOne(filter).wasAcknowledged();
    }


    public CompletableFuture<Boolean> asyncDeleteDocument(Bson filter) {
        return CompletableFuture.supplyAsync(() -> syncDeleteDocument(filter));
    }


    public MongoCursor<Document> syncGetAllDocuments(Bson filter) {
        if (filter == null)
            return mongoCollection.find().iterator();
        return mongoCollection.find(filter).iterator();
    }


    public CompletableFuture<MongoCursor<Document>> asyncGetAllDocuments(Bson filter) {
        return CompletableFuture.supplyAsync(() -> syncGetAllDocuments(filter), ModuleScheduler.EXECUTOR_SERVICE);
    }

}
