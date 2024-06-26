package thread.creation.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            //Code that will run in a new thread
            System.out.println();
            throw new RuntimeException();
//            System.out.println("We are now in thread: " + Thread.currentThread().getName());
//            System.out.println("The priority is " + Thread.currentThread().getPriority());
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("Something goes wrong");
        });

//        System.out.println(thread.isAlive());
//        System.out.println(thread.isInterrupted());
        System.out.println("We are in thread: " + Thread.currentThread().getName());
        System.out.println("This is BEFORE starting a new thread");

        System.out.println();

        thread.start();

//        System.out.println(thread.isAlive());
//        System.out.println(thread.isInterrupted());
        System.out.println("We are in thread: " + Thread.currentThread().getName());
        System.out.println("This is AFTER starting a new thread");

        Thread.sleep(1000L);
    }
}
