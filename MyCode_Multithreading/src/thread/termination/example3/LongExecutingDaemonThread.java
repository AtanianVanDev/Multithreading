package thread.termination.example3;

import java.math.BigInteger;

public class LongExecutingDaemonThread extends Thread {
    private BigInteger base;
    private  BigInteger power;

    public LongExecutingDaemonThread(BigInteger base, BigInteger power) {
        this.base = base;
        this.power = power;
    }

    @Override
    public void run() {
        System.out.println(base + "^" + power + " = " + pow(base, power));
    }

    private BigInteger pow(BigInteger base, BigInteger power) {
        BigInteger result = BigInteger.ONE;

        for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
            result = result.multiply(base);
        }

        return result;
    }
}
