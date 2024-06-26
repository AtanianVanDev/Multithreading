package thread.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) throws InterruptedException {
        List<Long> list = Arrays.asList(0L, 20L, 300L, 4000L, 50000000L, 600L, 7000L, 800L, 90L);

        List<Factorial> threads = new ArrayList<>();

        for(Long l : list) {
           threads.add(new Factorial(l));
        }

        for(Thread t : threads) {
            t.setDaemon(true);
            t.start();
        }

        for(Thread t : threads) {
            t.join(7000L);
        }

        for (int i = 0 ; i < list.size() ; i++) {
            Factorial f = threads.get(i);
            System.out.println("Factorial of " + list.get(i) + " is " + f.getResult());
        }
    }
}
