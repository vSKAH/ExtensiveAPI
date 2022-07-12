package fr.skah.skmdl.api.commons.async;


import fr.skah.skmdl.api.spigot.modules.manage.ModuleManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ModuleScheduler {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleTask" + r.getClass().getName()));
    public static final ScheduledExecutorService RUNNABLE_EXECUTOR = Executors.newScheduledThreadPool(10 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleRunnable" + r.getClass().getName()));


    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
        RUNNABLE_EXECUTOR.shutdown();
    }

    public static void shutdownNow() {
        EXECUTOR_SERVICE.shutdownNow();
        RUNNABLE_EXECUTOR.shutdownNow();
    }
}
