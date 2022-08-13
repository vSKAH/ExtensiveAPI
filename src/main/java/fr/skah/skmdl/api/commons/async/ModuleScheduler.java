package fr.skah.skmdl.api.commons.async;


import fr.skah.skmdl.api.spigot.common.modules.manage.ModuleManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ModuleScheduler {

    // It's creating a new thread pool with 10 threads per module.
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleTask" + r.getClass().getName()));
    // It's creating a new thread pool with 10 threads per module.
    public static final ScheduledExecutorService RUNNABLE_EXECUTOR = Executors.newScheduledThreadPool(10 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleRunnable" + r.getClass().getName()));

    /**
     * Shutdown the executor services.
     */
    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
        RUNNABLE_EXECUTOR.shutdown();
    }

    /**
     * Shutdown the executor service and runnable executor service.
     */
    public static void shutdownNow() {
        EXECUTOR_SERVICE.shutdownNow();
        RUNNABLE_EXECUTOR.shutdownNow();
    }
}
