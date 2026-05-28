public class DeadlockSolvedExample4 {
    private static final Object multiResourceLock = new Object();

    public static void main(String[] args) {
        
        // Solução 4: Usar um lock global para proteger o acesso a ambos os recursos compartilhados.
        // Em vez de tentar adquirir locks separados para cada recurso, ambos os threads adquirem um lock global antes de acessar os recursos. 
        // Isso garante que apenas um thread possa acessar os recursos compartilhados de cada vez, eliminando a possibilidade de deadlock.

        // Thread 1
        Thread thread1 = new Thread(() -> {
            synchronized (multiResourceLock) {
                System.out.println("Thread 1: Holding multiResourceLock (Accessing A and B)...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 1: Finished work with A and B!");
            }
        }, "Thread-1");

        // Thread 2
        Thread thread2 = new Thread(() -> {
            synchronized (multiResourceLock) {
                System.out.println("Thread 2: Holding multiResourceLock (Accessing B and A)...");
                
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                
                System.out.println("Thread 2: Finished work with B and A!");
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
