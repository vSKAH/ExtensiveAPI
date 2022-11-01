package fr.skoupi.extensiveapi.databases.mongodb;

/*  MongoDataProvider
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertManyResult;
import lombok.Getter;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
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

    //Get one Document Part

    public Document syncGetDocumentFromFilter(Bson filter) {
        return mongoCollection.find(filter).first();
    }

    public CompletableFuture<Document> asyncGetDocumentFromUniqueId(Bson filter, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncGetDocumentFromFilter(filter), executorService);
    }

    // Insert documents part

    public boolean syncInsertDocument(Document document) {
        return mongoCollection.insertOne(document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncInsertDocument(Document document, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncInsertDocument(document), executorService);
    }


    public void syncInsertMultipleDocuments(List<Document> documents, boolean ordered, BiConsumer<Boolean, Map<Integer, BsonValue>> consumer) {
        InsertManyResult result = mongoCollection.insertMany(documents, new InsertManyOptions().ordered(ordered));
        consumer.accept(result.wasAcknowledged(), result.getInsertedIds());
    }

    public void asyncInsertMultipleDocuments(List<Document> documents, boolean ordered, BiConsumer<Boolean, Map<Integer, BsonValue>> consumer, ExecutorService executorService) {
        CompletableFuture.runAsync(() -> syncInsertMultipleDocuments(documents, ordered, consumer), executorService);
    }

    //Replace document part

    public boolean syncReplaceDocument(Bson filter, Document document) {
        return mongoCollection.replaceOne(filter, document).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncReplaceDocument(Bson filter, Document document, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncReplaceDocument(filter, document), executorService);
    }


    //Update document part
    public boolean syncUpdateOneDocument(Bson filter, Document document, boolean upsert) {
        return mongoCollection.updateOne(filter, document, new UpdateOptions().upsert(upsert)).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncUpdateOneDocument(Bson filter, Document document, boolean upsert, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncUpdateOneDocument(filter, document, upsert), executorService);
    }

    public boolean syncUpdateManyDocuments(Bson filter, List<Document> documents, boolean upsert) {
        return mongoCollection.updateMany(filter, documents, new UpdateOptions().upsert(upsert)).wasAcknowledged();
    }

    public CompletableFuture<Boolean> asyncUpdateManyDocuments(Bson filter, List<Document> documents, boolean upsert, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncUpdateManyDocuments(filter, documents, upsert), executorService);
    }

    //Delete document part
    public boolean syncDeleteDocument(Bson filter) {
        return mongoCollection.deleteOne(filter).wasAcknowledged();
    }


    public CompletableFuture<Boolean> asyncDeleteDocument(Bson filter, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncDeleteDocument(filter), executorService);
    }

    //Get multiple multiple document part

    public MongoCursor<Document> syncGetAllDocuments(Bson filter) {
        if (filter == null)
            return mongoCollection.find().iterator();
        return mongoCollection.find(filter).iterator();
    }


    public CompletableFuture<MongoCursor<Document>> asyncGetAllDocuments(Bson filter, ExecutorService executorService) {
        return CompletableFuture.supplyAsync(() -> syncGetAllDocuments(filter), executorService);
    }

}
