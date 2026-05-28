public class DeadlockSolvedExample2 {
    // Define two shared resources as locks
    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        
        // Solução 2: Garantir que ambos os threads adquiram os locks na mesma ordem (Lock A, depois Lock B).
        // Isso elimina a possibilidade de deadlock, pois ambos os threads tentam adquirir os locks na mesma sequência.
        
        // Thread 1: Wants Lock A then Lock B
        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1: Holding Lock A...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 1: Waiting for Lock B...");
                synchronized (lockB) {
                    System.out.println("Thread 1: Acquired Lock B!");
                }
            }
        }, "Thread-1");

        // Thread 2: ALSO wants Lock A then Lock B
        Thread thread2 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 2: Holding Lock A...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 2: Waiting for Lock B...");
                synchronized (lockB) {
                    System.out.println("Thread 2: Acquired Lock B!");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
