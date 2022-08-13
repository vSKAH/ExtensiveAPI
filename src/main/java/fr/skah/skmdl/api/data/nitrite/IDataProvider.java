package fr.skah.skmdl.api.data.nitrite;

/*
 *  * @Created on 2021 - 13:16
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.Iterator;
import java.util.List;

public interface IDataProvider<T> {


    /**
     * Initialize the repository with the given object.
     *
     * @param repository The repository that will be used to store the objects.
     */
    void init(ObjectRepository<T> repository);



    /**
     * Insert the object into the repository.
     *
     * @param repository The repository to use for the operation.
     * @param object The object to be inserted.
     */
    default void insertObject(ObjectRepository<T> repository, T object) {
        repository.insert(object);
    }


    /**
     * "Update the object in the repository with the given keyId and key."
     *
     * The function is defined as default so that it can be overridden by the implementing class
     *
     * @param repository The repository to update the object in.
     * @param keyId The name of the key field in the object.
     * @param key The key to be used to update the object.
     * @param object The object to be updated
     */
    default void updateObject(ObjectRepository<T> repository, String keyId, String key, T object) {
        repository.update(ObjectFilters.eq(keyId, key), object);
    }


    /**
     * Remove the object from the repository that has the given keyId and key.
     *
     * @param repository The repository to use to remove the object.
     * @param keyId The name of the key field in the object.
     * @param key The key to be used to store the object.
     */
    default void removeObject(ObjectRepository<T> repository, String keyId, String key) {
        repository.remove(ObjectFilters.eq(keyId, key));
    }


    /**
     * Get the first object from the repository that matches the keyId and key.
     *
     * @param repository The repository to search in
     * @param keyId The name of the field in the object that you want to search by.
     * @param key The key to search for
     * @return The first object in the iterator.
     */
    default T getObjectFromNitrite(ObjectRepository<T> repository, String keyId, String key) {
        Iterator<T> iterator = repository.find(ObjectFilters.eq(keyId, key)).iterator();
        return iterator.hasNext() ? iterator.next() : null;
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
