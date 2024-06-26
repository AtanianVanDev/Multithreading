package thread.termination.example2;

import java.math.BigInteger;

public class AppRunner {
    public static void main(String[] args) {
        Thread calculator = new LongExecutingThread(new BigInteger("2000"), new BigInteger("1000000"));

        calculator.start();
        calculator.interrupt();
    }
}
