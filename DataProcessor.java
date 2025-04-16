import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessor {
    public static void main(String[] args) {
        SharedQueue queue = new SharedQueue();
        for (int i = 1; i <= 20; i++) {
            queue.addTask("Task-" + i);
        }

        try (FileWriter writer = new FileWriter("results.txt")) {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 5; i++) {
                executor.execute(new Worker(queue, writer));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {}

            LoggerUtil.log("All tasks completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
