package fr.skah.skmdl.api.data.nitrite;

/*
 *  * @Created on 2021 - 13:19
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;


// Defining an interface that will be used to define the methods that will be used to interact with the Nitrite database.
public interface IDelayedDataProvider<T> {


    /**
     * Initialize the repository with the given object.
     *
     * @param repository The repository that will be used to store the objects.
     */
    void init(ObjectRepository<T> repository);


    /**
     * Insert the object into the repository on a separate thread.
     *
     * @param repository The repository to insert the object into.
     * @param object The object to be inserted.
     */
    default void insertObject(ObjectRepository<T> repository, T object) {
        Executors.newSingleThreadExecutor().submit(() -> { repository.insert(object); });
    }


    /**
     * "Update the object in the repository with the given keyId and key, and return the updated object."

     * The function is defined as default, which means it can be overridden by the implementing class
     *
     * @param repository The repository to update the object in.
     * @param keyId The name of the key field in the object
     * @param key The key to be used to update the object
     * @param object The object to be updated
     */
    default void updateObject(ObjectRepository<T> repository, String keyId, String key, T object) {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.update(ObjectFilters.eq(keyId, key), object);
        });
    }


    /**
     * Remove an object from the repository asynchronously.
     *
     * @param repository The repository to remove the object from.
     * @param keyId The name of the key to search for.
     * @param key The key to be used to store the object.
     */
    default void removeObject(ObjectRepository<T> repository, String keyId, String key) {
        Executors.newSingleThreadExecutor().submit(() -> {
            repository.remove(ObjectFilters.eq(keyId, key));
        });
    }


    /**
     * "Get the first object from the repository that matches the keyId and key."

     * The function is asynchronous, so it returns a CompletableFuture
     *
     * @param repository The repository to use to get the object from.
     * @param keyId The name of the key in the object
     * @param key The key to search for
     * @return A CompletableFuture that will return a single object from the Nitrite database.
     */
    default CompletableFuture<T> getObjectFromNitrite(ObjectRepository<T> repository, String keyId, String key) {
        return CompletableFuture.supplyAsync(() -> repository.find(ObjectFilters.eq(keyId, key)).firstOrDefault());
    }

    /**
     * Get all objects from the Nitrite database and return them as a list.
     *
     * @param repository The repository to get the objects from.
     * @return A list of objects of type T.
     */
    default List<T> getObjectsFromNitrite(ObjectRepository<T> repository) {
        return repository.find().toList();
    }

}
