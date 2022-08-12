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
     * @param repository Nitrite ObjectRepository
     */
    void init(ObjectRepository<T> repository);


    /**
     * @param repository Nitrite ObjectRepository
     * @param object     Object insert in ObjectRepository
     */
    default void insertObject(ObjectRepository<T> repository, T object) {
        repository.insert(object);
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute update
     * @param object     The  object put in ObjectRepository
     */
    default void updateObject(ObjectRepository<T> repository, String keyId, String key, T object) {
        repository.update(ObjectFilters.eq(keyId, key), object);
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute delete
     */
    default void removeObject(ObjectRepository<T> repository, String keyId, String key) {
        repository.remove(ObjectFilters.eq(keyId, key));
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @param keyId      The id of the column
     * @param key        The reference key for execute delete
     * @return T
     */
    default T getObjectFromNitrite(ObjectRepository<T> repository, String keyId, String key) {
        Iterator<T> iterator = repository.find(ObjectFilters.eq(keyId, key)).iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * @param repository Nitrite ObjectRepository
     * @return T
     */
    default List<T> getObjectsFromNitrite(ObjectRepository<T> repository) {
        return repository.find().toList();
    }

}
