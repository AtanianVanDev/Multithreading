package thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {

        Barrier barrier = new Barrier(3);
        CoordinatedWorkRunner coordinatedWorkRunner = new CoordinatedWorkRunner(barrier);
        Thread t1 = new Thread(coordinatedWorkRunner);
        Thread t2 = new Thread(coordinatedWorkRunner);
        Thread t3 = new Thread(coordinatedWorkRunner);

        t1.start();
        t2.start();
        t3.start();


    }

    public static class Barrier {
        private final int numberOfWorkers;
        private final Semaphore semaphore;
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers) {
            this.numberOfWorkers = numberOfWorkers;
            semaphore = new Semaphore(0);
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
                System.out.println();
                System.out.println("============= BARRIER ============");
                System.out.println();
                semaphore.release(numberOfWorkers);
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
}
