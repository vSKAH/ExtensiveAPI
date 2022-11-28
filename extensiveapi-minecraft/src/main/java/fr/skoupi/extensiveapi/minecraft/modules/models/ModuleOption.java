package fr.skoupi.extensiveapi.minecraft.modules.models;

/*  ModuleOption
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModuleOption {

    // It's a class that contains all the information about the module.
    private String moduleName;
    private String moduleDescription;
    private boolean canBeDisabled;
    private String moduleAuthor;
    private String moduleVersion;
    private String moduleMainClass;
    private List<String> pluginDependencies;
    private List<String> modulesDependencies;


}
