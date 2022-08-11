package fr.skah.skmdl.api.spigot.common.hooks.basics;

/*
 *  * @Created on 2022 - 12:55
 *  * @Project SKMDL
 *  * @Author Jimmy  / vSKAH#0075
 */


import com.gamingmesh.jobs.Jobs;
import fr.skah.skmdl.api.spigot.common.hooks.Hook;

public class JobsHook implements Hook<Jobs> {

    private Jobs jobs;

    @Override
    public boolean registerHook() {
        if(!pluginEnabled()) return false;
        this.jobs = Jobs.getInstance();
        return true;
    }

    @Override
    public String getHookName() {
        return "Jobs";
    }

    @Override
    public String getClasz() {
        return "com.gamingmesh.jobs.Jobs";
    }

    @Override
    public Jobs get() {
        return jobs;
    }
}
