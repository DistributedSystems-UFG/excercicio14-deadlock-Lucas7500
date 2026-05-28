import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockSolvedExample3 {
    // Define two shared resources as ReentrantLocks
    private static final Lock lockA = new ReentrantLock();
    private static final Lock lockB = new ReentrantLock();

    public static void main(String[] args) {
        
        // Solução 3: Usar tryLock com timeout para evitar deadlock.
        // Cada thread tentará adquirir os locks, mas se não conseguir dentro de um tempo limite, ela liberará os locks que já possui e tentará novamente.
        
        Thread thread1 = new Thread(() -> {
            boolean acquired = false;
            while (!acquired) {
                try {
                    if (lockA.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Thread 1: Holding Lock A...");
                            Thread.sleep(50); // Simulate work
                            
                            System.out.println("Thread 1: Waiting for Lock B...");
                            if (lockB.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Thread 1: Acquired Lock B!");
                                    acquired = true;
                                } finally {
                                    lockB.unlock();
                                }
                            } else {
                                System.out.println("Thread 1: Could not get Lock B, backing off...");
                            }
                        } finally {
                            lockA.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                if (!acquired) {
                    try { Thread.sleep(10); } catch (InterruptedException e) {} // Random wait before retry
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            boolean acquired = false;
            while (!acquired) {
                try {
                    if (lockB.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Thread 2: Holding Lock B...");
                            Thread.sleep(50); // Simulate work
                            
                            System.out.println("Thread 2: Waiting for Lock A...");
                            if (lockA.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("Thread 2: Acquired Lock A!");
                                    acquired = true;
                                } finally {
                                    lockA.unlock();
                                }
                            } else {
                                System.out.println("Thread 2: Could not get Lock A, backing off...");
                            }
                        } finally {
                            lockB.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (!acquired) {
                    try { Thread.sleep(10); } catch (InterruptedException e) {} // Random wait before retry
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
