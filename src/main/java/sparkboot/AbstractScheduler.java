package sparkboot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by misha on 03.11.2016.
 */
public abstract class AbstractScheduler {

    private ScheduledExecutorService executorService;

    public AbstractScheduler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public abstract void schedule();

    protected void task(Runnable command, int timeValue, TimeUnit timeUnit) {
        executorService.schedule(command, timeValue, timeUnit);
    }

    public void stop() {
        executorService.shutdown();
    }
}
