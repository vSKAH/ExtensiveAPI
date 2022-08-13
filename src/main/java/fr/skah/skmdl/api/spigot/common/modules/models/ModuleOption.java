package fr.skah.skmdl.api.spigot.common.modules.models;

/*
 *  * @Created on 2021 - 20:00
 *  * @Project SKMDL
 *  * @Author Jimmy
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleOption {

    // It's a class that contains all the information about the module.
    private String moduleName;
    private String moduleDescription;
    private boolean canBeDisabled;
    private String moduleAuthor;
    private String moduleVersion;
    private String moduleMainClass;
    private List<String> pluginDependencies;


}
