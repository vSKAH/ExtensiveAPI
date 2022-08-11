package fr.skah.skmdl.api.commons.accounts;

/*
 *  * @Created on 2022 - 15:50
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Account implements IAccountIdentifier, Cloneable
{
    private UUID playerUniqueId;
    private Map<String, Object> playerData;

    public Account(UUID playerUniqueId) {
        this.playerData = new HashMap<>();
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public Optional<Object> getDataFromMap(final String key) {
        return this.playerData.containsKey(key) ? Optional.of(this.playerData.get(key)) : Optional.empty();
    }

    public void setDataToMap(final String key, final Object value) {
        this.playerData.put(key, value);
    }

    public Account clone() {
        try {
            return (Account)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Account account)) {
            return false;
        }
        return Objects.equal(this.getPlayerUniqueId(), account.getPlayerUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getPlayerUniqueId());
    }
}
