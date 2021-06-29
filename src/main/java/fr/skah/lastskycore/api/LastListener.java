package fr.skah.lastskycore.api;

/*
 *  * @Created on 2021 - 15:07
 *  * @Project LastSkyCore
 *  * @Author Jimmy
 */

import org.bukkit.event.Listener;

public class LastListener implements Listener {

    private final String moduleName;

    public LastListener(String moduleName) {
        this.moduleName = moduleName;
    }


    public String getModuleName() {
        return moduleName;
    }
}
