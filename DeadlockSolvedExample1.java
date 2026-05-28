public class DeadlockSolvedExample1 {
    // Define two shared resources as locks
    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    // Introduce a global lock to control access to the shared resources.
    private static final Object lockG = new Object();
    
    public static void main(String[] args) {
        
        // Solução 1: Introduzir um lock global para controlar o acesso aos recursos compartilhados.
        // Ambos os threads adquirem o lock global antes de tentar adquirir os locks A e B.

        // Thread 1: Wants Lock A then Lock B
        Thread thread1 = new Thread(() -> {
            synchronized (lockG) {
                synchronized (lockA) {
                System.out.println("Thread 1: Holding Lock A...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 1: Waiting for Lock B...");
                synchronized (lockB) {
                    System.out.println("Thread 1: Acquired Lock B!");
                }
            }
            }
        }, "Thread-1");

        // Thread 2: Wants Lock B then Lock A
        Thread thread2 = new Thread(() -> {
            synchronized (lockG) {
                synchronized (lockB) {
                System.out.println("Thread 2: Holding Lock B...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 2: Waiting for Lock A...");
                synchronized (lockA) {
                    System.out.println("Thread 2: Acquired Lock A!");
                }
            }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
