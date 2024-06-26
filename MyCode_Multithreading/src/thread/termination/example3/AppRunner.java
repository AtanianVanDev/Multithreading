package thread.termination.example3;

import java.math.BigInteger;

public class AppRunner {
    public static void main(String[] args) throws InterruptedException {
        Thread calculator = new LongExecutingDaemonThread(new BigInteger("2000"), new BigInteger("1000000"));

        calculator.setDaemon(true);
        calculator.start();
        Thread.sleep(100L);
        calculator.interrupt();
    }
}
