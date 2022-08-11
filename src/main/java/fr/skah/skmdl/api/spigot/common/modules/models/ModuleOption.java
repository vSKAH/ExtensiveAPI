package fr.skah.skmdl.api.spigot.common.modules.models;

/*
 *  * @Created on 2021 - 20:00
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import java.util.List;

public class ModuleOption {

    private String moduleName;
    private String moduleDescription;
    private boolean canBeDisabled;
    private String moduleAuthor;
    private String moduleVersion;
    private String moduleMainClass;
    private List<String> pluginDependencies;

    public ModuleOption() {
        super();
    }

    public ModuleOption(String moduleName, String moduleDescription, boolean canBeDisabled, String moduleAuthor, String moduleVersion, String moduleMainClass, List<String> pluginDependencies) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.canBeDisabled = canBeDisabled;
        this.moduleAuthor = moduleAuthor;
        this.moduleVersion = moduleVersion;
        this.moduleMainClass = moduleMainClass;
        this.pluginDependencies = pluginDependencies;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public boolean isCanBeDisabled() {
        return canBeDisabled;
    }

    public String getModuleAuthor() {
        return moduleAuthor;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public String getModuleMainClass() {
        return moduleMainClass;
    }


    public List<String> getPluginDependencies() {
        return pluginDependencies;
    }
}
