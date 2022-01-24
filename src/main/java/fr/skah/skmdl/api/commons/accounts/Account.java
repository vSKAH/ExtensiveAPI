package fr.skah.skmdl.api.commons.accounts;

/*
 *  * @Created on 2022 - 15:50
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import java.util.UUID;

public class Account implements IAccountIdentifier, IAccountData, Cloneable
{
    private int accountId;
    private UUID playerUniqueId;
    private String playerName;

    public Account(final int accountId, final UUID playerUniqueId, final String playerName) {
        this.accountId = accountId;
        this.playerUniqueId = playerUniqueId;
        this.playerName = playerName;
    }

    @Override
    public int getPlayerDatabaseId() {
        return this.accountId;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
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
