package fr.skah.skmdl.api.commons.accounts;

import java.util.UUID;

public interface IAccountIdentifier {

    int getPlayerDatabaseId();

    /**
     * @return return the player uuid
     */
    UUID getPlayerUniqueId();


    /**
     * @return return the player name
     */
    String getPlayerName();

}
