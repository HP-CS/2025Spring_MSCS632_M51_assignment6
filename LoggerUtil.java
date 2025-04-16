public class LoggerUtil {
    public static synchronized void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
