public class DeadlockExample {
    // Define two shared resources as locks
    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    
    public static void main(String[] args) {
        
        // Thread 1: Wants Lock A then Lock B
        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: Holding Lock A...");
                
                // Sleep to ensure Thread 2 has enough time to lock Lock B
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 1: Waiting for Lock B...");
                synchronized (lockB) {
                    System.out.println("Thread 1: Acquired Lock B!");
                }
            }
        }, "Thread-1");

        // Thread 2: Wants Lock B then Lock A
        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2: Holding Lock B...");
                
                // Sleep to ensure Thread 1 has enough time to lock Lock A
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 2: Waiting for Lock A...");
                synchronized (lockA) {
                    System.out.println("Thread 2: Acquired Lock A!");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
