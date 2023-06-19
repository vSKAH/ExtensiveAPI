package fr.skoupi.extensiveapi.core.mavenresolver;

import lombok.Getter;

@Getter
public class Dependency {

    private String groupId;
    private String artifactId;
    private String version;
    private String repository;

    /**
     * Used if you want to download a dependency from a private repository
     *
     * @param groupId    the group id of the dependency
     * @param artifactId the artifact id of the dependency
     * @param version    the version of the dependency
     * @param repository the custom repository of the dependency
     */
    public Dependency(String groupId, String artifactId, String version, String repository) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;

        this.repository = repository.isEmpty() ? "https://repo1.maven.org/maven2/" : repository;
    }

    /***
     * Used if we only use public maven dependencies
     *
     * @param groupId the group id of the dependency
     * @param artifactId the artifact id of the dependency
     * @param version the version of the dependency
     */
    public Dependency(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, "https://repo1.maven.org/maven2/");
    }

    /**
     * @return the full URL of the dependency
     */

    public String getURLName() {
        return getRepository() + getGroupId().replace(".", "/") + "/" + getArtifactId() + "/" + getVersion() + "/" + getArtifactId() + "-" + getVersion() + ".jar";
    }

    /**
     * @return the full URL of the dependency
     */
    public String getURLNameWithoutId() {
        return getRepository();
    }

}