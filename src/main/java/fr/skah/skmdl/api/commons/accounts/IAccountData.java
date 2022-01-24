package fr.skah.skmdl.api.commons.accounts;

/*
 *  * @Created on 2022 - 15:18
 *  * @Project SK-MDL
 *  * @Author jimmy  / vSKAH#0075
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface IAccountData {

   Map<String, Object> playerData = new HashMap<>();

    default Optional<Object> getDataFromMap(final String key) {
        return IAccountData.playerData.containsKey(key) ? Optional.of(IAccountData.playerData.get(key)) : Optional.empty();
    }

}
