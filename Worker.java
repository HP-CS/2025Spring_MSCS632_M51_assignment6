import java.io.FileWriter;
import java.io.IOException;

public class Worker implements Runnable {
    private final SharedQueue queue;
    private final FileWriter writer;

    public Worker(SharedQueue queue, FileWriter writer) {
        this.queue = queue;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = queue.getTask();
                if (task == null) break;

                LoggerUtil.log("Processing task: " + task);
                Thread.sleep(500); // simulate delay

                synchronized (writer) {
                    writer.write(task + " processed\n");
                }
                LoggerUtil.log("Completed task: " + task);
            }
        } catch (InterruptedException | IOException e) {
            LoggerUtil.log("Error: " + e.getMessage());
        }
    }
}
