package fr.skoupi.extensiveapi.minecraft.hooks.basics;

/*  JobsHook
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */


import com.gamingmesh.jobs.Jobs;
import fr.skoupi.extensiveapi.minecraft.hooks.Hook;

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
