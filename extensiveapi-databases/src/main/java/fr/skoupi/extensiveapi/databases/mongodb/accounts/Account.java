package fr.skoupi.extensiveapi.databases.mongodb.accounts;

/*  Account
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.databases.mongodb.accounts.exception.AccountEmptyDocumentException;
import lombok.*;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Account implements Cloneable {
	// It's a lombok annotation that allows you to specify which fields are included in the equals and hashCode methods.
	@EqualsAndHashCode.Include
	// It's a private variable that stores the UUID of the player.
	private UUID accountUniqueId;
	// It's a map that stores all the data of the account.
	private Map<String, Object> data = new HashMap<>();

	/**
	 * If the key exists in the map, return the value, otherwise return an empty Optional.
	 *
	 * @param key The key to get the data from.
	 * @return Optional<Object>
	 */
	public Optional<Object> getDataFromMap (final String key)
	{
		return this.data.containsKey(key) ? Optional.of(this.data.get(key)) : Optional.empty();
	}

	/**
	 * This function sets the value of the key in the data map to the value passed in.
	 *
	 * @param key   The key to store the data under.
	 * @param value The value to be stored in the map.
	 */
	public void setDataToMap (final String key, final Object value)
	{
		this.data.put(key, value);
	}

	/**
	 * It generates a document from the data map
	 *
	 * @return A Document object.
	 */
	public Document generateDocument () throws AccountEmptyDocumentException
	{
		if (data.isEmpty())
			throw new AccountEmptyDocumentException("Document can't be generated from data map because the map data is null or empty !");
		Document document = new Document(data);
		if (!document.containsKey("uuid")) document.append("uuid", accountUniqueId.toString());
		return document;
	}

	/**
	 * It loads the data from a document and returns it in a HashMap
	 *
	 * @param document The document to load
	 * @return A HashMap with all the data of the document.
	 */
	public HashMap<String, Object> loadFromDocument (Document document) throws AccountEmptyDocumentException
	{
		if (document == null || document.isEmpty())
			throw new AccountEmptyDocumentException("Document can't be loaded because he is null or empty !");
		HashMap<String, Object> datas = new HashMap<>();
		for (Map.Entry<String, Object> entry : document.entrySet())
		{
			if (!entry.getKey().equals("uuid")) datas.put(entry.getKey(), entry.getValue());
		}
		return datas;
	}


	/**
	 * If the object is cloneable, then clone it, otherwise throw a CloneNotSupportedException.
	 *
	 * @return A shallow copy of the Account object.
	 */
	public Account clone ()
	{
		try
		{
			return (Account) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
