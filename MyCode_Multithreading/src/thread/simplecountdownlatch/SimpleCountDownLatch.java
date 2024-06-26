import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCountDownLatch {
    private int count;
    AtomicReference
    public SimpleCountDownLatch(int count) {
        this.count = count;
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
    }

    /**
     * Causes the current thread to wait until the latch has counted down to zero.
     * If the current count is already zero then this method returns immediately.
     */
    public synchronized void await() throws InterruptedException {
        /**
         * Fill in your code
         */
        if (count == 0)
            return;

        while (count > 0) {
            Thread.currentThread().wait();
        }
    }

    /**
     *  Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
     *  If the current count already equals zero then nothing happens.
     */
    public synchronized void countDown() {
        /**
         * Fill in your code
         */
        if (count > 0) {
            count--;
        }

        Thread.currentThread().notifyAll();

    }

    /**
     * Returns the current count.
     */
    public synchronized int getCount() {
        /**
         * Fill in your code
         */
        return count;
    }
}
/**Question 1:
In this question, we will design a Barrier class.

When running tasks by multiple threads concurrently,  sometimes we would like to coordinate the work to guarantee that some portion of the work is done by all threads before the rest of the work is performed.

The following task is performed by multiple threads concurrently:*/

private void task() throws InterruptedException {

    // Performing Part 1
    System.out.println(Thread.currentThread().getName()
                               + " part 1 of the work is finished");

    barrier.waitForOthers();

    //Performing Part2
    System.out.println(Thread.currentThread().getName()
                               + " part 2 of the work is finished");
}


/**If we have 3 threads executing this task concurrently, we would like the output to look like this:

thread1 part 1 of the work is finished
thread2 part 1 of the work is finished
thread3 part 1 of the work is finished

thread2 part 2 of the work is finished
thread1 part 2 of the work is finished
thread3 part 2 of the work is finished
The order of the execution of each part is not important. But we want to make sure that all threads finish part1 before any thread can go ahead and perform part2



Here is a proposed solution with some blank spots we need to fill in*/

public static class Barrier {
    private final int numberOfWorkers;
    private final Semaphore semaphore = new Semaphore( //** blank 1 **/);
    private int counter = //** blank 2 **/;
    private final Lock lock = new ReentrantLock();

    public Barrier(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public void waitForOthers() throws InterruptedException {
        lock.lock();
        boolean isLastWorker = false;
        try {
            counter++;

            if (counter == numberOfWorkers) {
                isLastWorker = true;
            }
        } finally {
            lock.unlock();
        }

        if (isLastWorker) {
            semaphore.release(//** blank 3 **/);
        } else {
            semaphore.acquire();
        }
    }
}

public static class CoordinatedWorkRunner implements Runnable {
    private final Barrier barrier;

    public CoordinatedWorkRunner(Barrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            task();
        } catch (InterruptedException e) {
        }
    }

    private void task() throws InterruptedException {
        // Performing Part 1
        System.out.println(Thread.currentThread().getName()
                                   + " part 1 of the work is finished");

        barrier.waitForOthers();

        // Performing Part2
        System.out.println(Thread.currentThread().getName()
                                   + " part 2 of the work is finished");
    }
}
/**To make this solution correct, how should we fill in the /** blank ?  sections?



To test your code, you can use this main method:*/

public static void main(String [] args) throws InterruptedException {
    int numberOfThreads = 200; //or any number you'd like

    List<Thread> threads = new ArrayList<>();

    Barrier barrier = new Barrier(numberOfThreads);
    for (int i = 0; i < numberOfThreads; i++) {
        threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
    }

    for(Thread thread: threads) {
        thread.start();
    }
}









