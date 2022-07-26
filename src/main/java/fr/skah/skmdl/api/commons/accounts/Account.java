package fr.skah.skmdl.api.commons.accounts;

/*
 *  * @Created on 2022 - 15:50
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import java.util.UUID;

public class Account implements IAccountIdentifier, IAccountData, Cloneable
{
    private final UUID playerUniqueId;

    public Account(final UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
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
}
