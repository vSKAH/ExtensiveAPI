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

public interface IDelayedDataProvider<T> {

    /**
     * @param repository Nitrite ObjectRepository
     */
    void init(ObjectRepository<T> repository);

    /**
     * @param repository Nitrite ObjectRepository
     * @param object     Object insert in ObjectRepository
     */
    default void insertObject(ObjectRepository<T> repository, T object) {
        Executors.newCachedThreadPool().submit(() -> { repository.insert(object); });
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute update
     * @param object     The object put in ObjectRepository
     */
    default void updateObject(ObjectRepository<T> repository, String keyId, String key, T object) {
        Executors.newCachedThreadPool().submit(() -> {
            repository.update(ObjectFilters.eq(keyId, key), object);
        });
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute delete
     * callback CompletableFuture<Void></>
     */
    default void removeObject(ObjectRepository<T> repository, String keyId, String key) {
        Executors.newCachedThreadPool().submit(() -> {
            repository.remove(ObjectFilters.eq(keyId, key));
        });
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute delete
     * @return CompletableFuture<T></>
     */
    default CompletableFuture<T> getObjectFromNitrite(ObjectRepository<T> repository, String keyId, String key) {
        return CompletableFuture.supplyAsync(() -> repository.find(ObjectFilters.eq(keyId, key)).firstOrDefault());
    }


    /**
     * @param repository Nitrite ObjectRepository
     * @return Return object list in ObjectRepository<T></>
     */
    default List<T> getObjectsFromNitrite(ObjectRepository<T> repository) {
        return repository.find().toList();
    }

}
