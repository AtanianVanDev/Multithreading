package thread.creation.studycase;

public abstract class HackerThread extends Thread {
    public static final int MAX_PASSWORD = 9999;
    Vault vault;

    HackerThread(Vault vault) {
        this.vault = vault;
        this.setName(this.getClass().getSimpleName());
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void start() {
        System.out.println("Starting thread " + this.getName());
        super.start();
    }
}
