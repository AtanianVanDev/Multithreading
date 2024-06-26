package thread.termination.example1;

public class InterruptionHandlingThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(5000000L);
        } catch (InterruptedException e) {
            System.out.println("Exiting Blocking Thread");
        }
    }
}
