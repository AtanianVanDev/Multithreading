package thread.termination.example1;

public class AppRunner {
    public static void main(String[] args) {
        Thread thread = new InterruptionHandlingThread();

        thread.start();
        thread.interrupt();
    }
}
