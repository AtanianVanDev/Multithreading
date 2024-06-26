package thread.creation.studycase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppRunner {
    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(HackerThread.MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        threads.forEach(Thread::start);
    }
}
