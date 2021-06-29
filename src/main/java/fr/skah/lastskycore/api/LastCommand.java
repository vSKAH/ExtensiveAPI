package fr.skah.lastskycore.api;

/*
 *  * @Created on 2021 - 15:13
 *  * @Project LastSkyCore
 *  * @Author Jimmy  / vSKAH#0075
 */

import co.aikar.commands.BaseCommand;

public class LastCommand extends BaseCommand {

    private final String moduleName;

    public LastCommand(String moduleName) {
        this.moduleName = moduleName;
    }


    public String getModuleName() {
        return moduleName;
    }

}
