package threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private String threadName;
    private AtomicInteger number = new AtomicInteger(0);

    public NamedThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = threadName + number.incrementAndGet();
        return new Thread(r, name);
    }
}
