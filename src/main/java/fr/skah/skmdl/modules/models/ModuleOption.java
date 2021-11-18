package fr.skah.skmdl.modules.models;

/*
 *  * @Created on 2021 - 20:00
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

public class ModuleOption {

    private String moduleName;
    private String moduleDescription;
    private boolean canBeDisabled;
    private String moduleAuthor;
    private String moduleVersion;
    private String moduleMainClass;

    public ModuleOption() {
        super();
    }

    public ModuleOption(String moduleName, String moduleDescription, boolean canBeDisabled, String moduleAuthor, String moduleVersion, String moduleMainClass) {
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.canBeDisabled = canBeDisabled;
        this.moduleAuthor = moduleAuthor;
        this.moduleVersion = moduleVersion;
        this.moduleMainClass = moduleMainClass;
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
}
