import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueue {
    private final Queue<String> taskQueue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void addTask(String task) {
        lock.lock();
        try {
            taskQueue.add(task);
        } finally {
            lock.unlock();
        }
    }

    public String getTask() {
        lock.lock();
        try {
            return taskQueue.poll();
        } finally {
            lock.unlock();
        }
    }
}
