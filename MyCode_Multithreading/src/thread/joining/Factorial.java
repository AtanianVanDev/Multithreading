package thread.joining;

import java.math.BigInteger;

public class Factorial extends Thread {
    private long input;
    private BigInteger result;
    private boolean isFinished = false;

    public Factorial(long input) {
        this.input = input;
    }

    @Override
    public void run() {
        result = factorial(input);
        isFinished = true;
    }

    private BigInteger factorial(long n) {
        BigInteger tempResult = BigInteger.ONE;
        for (long i = 1; i <= input; i++) {
            tempResult = tempResult.multiply(BigInteger.valueOf(i));
        }

        return tempResult;
    }

    public BigInteger getResult() {
        return result;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
