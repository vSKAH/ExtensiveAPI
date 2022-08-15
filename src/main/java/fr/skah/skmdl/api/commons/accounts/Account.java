package fr.skah.skmdl.api.commons.accounts;

/*
 *  * @Created on 2022 - 15:50
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import fr.skah.skmdl.api.commons.accounts.exception.AccountEmptyDocumentException;
import lombok.EqualsAndHashCode;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account implements IAccountIdentifier, Cloneable {
    @EqualsAndHashCode.Include
    private final UUID playerUniqueId;
    private final Map<String, Object> data;

    public Account(UUID playerUniqueId) {
        this.data = new HashMap<>();
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public Optional<Object> getDataFromMap(final String key) {
        return this.data.containsKey(key) ? Optional.of(this.data.get(key)) : Optional.empty();
    }

    public void setDataToMap(final String key, final Object value) {
        this.data.put(key, value);
    }

    public Document generateDocument() throws AccountEmptyDocumentException {
        if (data.isEmpty())
            throw new AccountEmptyDocumentException("Document can't be generated because the map data is null or empty !");
        Document document = new Document(data);
        if (!document.containsKey("uuid")) document.append("uuid", playerUniqueId.toString());
        return document;
    }

    public HashMap<String, Object> loadFromDocument(Document document) throws AccountEmptyDocumentException {
        if (document == null || document.isEmpty())
            throw new AccountEmptyDocumentException("Document can't be loaded because he is null or empty !");
        HashMap<String, Object> datas = new HashMap<>();
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            if (!entry.getKey().equals("uuid")) datas.put(entry.getKey(), entry.getValue());
        }
        return datas;
    }

    public void saveAccount(Document document) throws AccountEmptyDocumentException {

    }

    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
