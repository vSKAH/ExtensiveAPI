package fr.skoupi.extensiveapi.minecraft.modules;

/*  ModuleScheduler
 *  By: vSKAH <vskahhh@gmail.com>

 * Created with IntelliJ IDEA
 * For the project ExtensiveAPI
 */

import fr.skoupi.extensiveapi.minecraft.modules.manage.ModuleManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ModuleScheduler {

    // It's creating a new thread pool with 5 threads per module.
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleTask" + r.getClass().getName()));
    // It's creating a new thread pool with 5 threads per module.
    public static final ScheduledExecutorService RUNNABLE_EXECUTOR = Executors.newScheduledThreadPool(5 * ModuleManager.getModules().size(), r -> new Thread(r, "ModuleRunnable" + r.getClass().getName()));

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
