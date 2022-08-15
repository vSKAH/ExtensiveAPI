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

    /**
     * Find the first document in the collection that has a field named 'uuid' with the value of the uniqueId parameter.
     *
     * @param uniqueId The unique id of the document you want to get.
     * @return A Document object.
     */
    public Document syncGetDocumentFromUniqueId(UUID uniqueId) {
        return mongoCollection.find(Filters.eq("uuid", uniqueId.toString())).first();
    }

    /**
     * "This function returns a CompletableFuture that will supply the result of the syncGetDocumentFromUniqueId function
     * when it is ready."
     *
     * The CompletableFuture class is a class that represents a future result of an asynchronous computation. It is a class
     * that is used to represent a result that will be available at some point in the future
     *
     * @param uniqueId The unique id of the document you want to get.
     * @return A CompletableFuture that will return a Document when it is completed.
     */
    public CompletableFuture<Document> asyncGetDocumentFromUniqueId(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> syncGetDocumentFromUniqueId(uniqueId), ModuleScheduler.EXECUTOR_SERVICE);
    }

    /**
     * Insert a document into the collection and return true if the operation was acknowledged.
     *
     * @param document The document to be inserted.
     * @return A boolean value.
     */
    public Boolean syncInsertDocument(Document document) {
        return mongoCollection.insertOne(document).wasAcknowledged();
    }

    /**
     * "This function takes a document and returns a CompletableFuture that will eventually contain a boolean value. The
     * boolean value will be the result of calling the syncInsertDocument function with the document as a parameter."
     *
     * The CompletableFuture class is a class that represents a future value. It's a value that will be available at some
     * point in the future. The CompletableFuture class has a static method called supplyAsync that takes a Supplier and an
     * ExecutorService. A Supplier is a function that takes no parameters and returns a value. The supplyAsync method
     * returns a CompletableFuture that will eventually contain the value returned by the Supplier. The ExecutorService is
     * a service that executes tasks. The supplyAsync method will execute the Supplier on the ExecutorService
     *
     * @param document The document to insert.
     * @return A CompletableFuture that will return a Boolean.
     */
    public CompletableFuture<Boolean> asyncInsertDocument(Document document) {
        return CompletableFuture.supplyAsync(() -> syncInsertDocument(document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    /**
     * Replace the document with the given uniqueId with the given document.
     *
     * @param uniqueId The unique ID of the document to replace.
     * @param document The document to be inserted.
     * @return A boolean value.
     */
    public boolean syncReplaceDocument(UUID uniqueId, Document document) {
        return mongoCollection.replaceOne(Filters.eq("uuid", uniqueId.toString()), document).wasAcknowledged();
    }

    /**
     * "Replace a document in the database asynchronously."
     *
     * The first line of the function is the return type. In this case, it's a `CompletableFuture` that will return a
     * `Boolean` when it's done
     *
     * @param uniqueId The unique ID of the document to replace.
     * @param document The document to be replaced.
     * @return A CompletableFuture that will return a Boolean.
     */
    public CompletableFuture<Boolean> asyncReplaceDocument(UUID uniqueId, Document document) {
        return CompletableFuture.supplyAsync(() -> syncReplaceDocument(uniqueId, document), ModuleScheduler.EXECUTOR_SERVICE);
    }

    /**
     * Delete the document with the given unique ID from the database.
     *
     * @param uniqueId The unique ID of the document to delete.
     * @return A boolean value.
     */
    public boolean syncDeleteDocument(UUID uniqueId) {
        return mongoCollection.deleteOne(Filters.eq("uuid", uniqueId.toString())).wasAcknowledged();
    }

    /**
     * "Delete a document from the database, and return a CompletableFuture that will complete with a Boolean indicating
     * whether the delete was acknowledged."
     *
     * The first thing to notice is that the function returns a CompletableFuture. This is a Java class that represents a
     * value that will be available at some point in the future. It's a way of saying "I'm going to do some work, and when
     * I'm done, I'll give you the result."
     *
     * The second thing to notice is that the function is marked with the async keyword. This is a Kotlin keyword that
     * tells the compiler to generate a CompletableFuture for the function
     *
     * @param uniqueId The unique ID of the document to delete.
     * @return A CompletableFuture that will return a Boolean.
     */
    public CompletableFuture<Boolean> asyncDeleteDocument(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() -> mongoCollection.deleteOne(Filters.eq("uuid", uniqueId.toString())).wasAcknowledged());
    }


    /**
     * Get all documents from the collection and return them as a cursor.
     *
     * @return A MongoCursor<Document>
     */
    public MongoCursor<Document> syncGetAllDocuments() {
        return mongoCollection.find().iterator();
    }

    /**
     * "Return a CompletableFuture that will supply the result of calling syncGetAllDocuments() on the ModuleScheduler's
     * executor service."
     *
     * The CompletableFuture class is a class that represents a future result of an asynchronous computation. It has a
     * number of methods that allow you to chain together multiple asynchronous operations
     *
     * @return A CompletableFuture that will return a MongoCursor<Document>
     */
    public CompletableFuture<MongoCursor<Document>> asyncGetAllDocuments() {
        return CompletableFuture.supplyAsync(this::syncGetAllDocuments, ModuleScheduler.EXECUTOR_SERVICE);
    }

}
