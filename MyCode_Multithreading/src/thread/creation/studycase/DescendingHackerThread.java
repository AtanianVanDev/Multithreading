package thread.creation.studycase;

public class DescendingHackerThread extends HackerThread {
    DescendingHackerThread(Vault vault) {
        super(vault);
    }

    @Override
    public void run() {
        for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
            if (vault.isCorrectPassword(guess)) {
                System.out.println(this.getName() + " has found the correct password: " + guess);
                System.exit(0);
            }
        }
    }
}
