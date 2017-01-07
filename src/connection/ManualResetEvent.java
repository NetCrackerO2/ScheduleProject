package connection;


public class ManualResetEvent {
    private final Object monitor = new Object();
    private volatile boolean open = false;
    //=============


    public ManualResetEvent(boolean open) {
        this.open = open;
    }

    public void waitOne() {
        synchronized (monitor) {
            while (!open) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    //TODO: уточнить, когда такое может произойти.
                    System.out.println("СТРАШНОЕМЕСТО: Наихудшее случилось. Убейтесь.");
                    System.exit(13);
                }
            }
        }
    }

    public void set() {
        synchronized (monitor) {
            open = true;
            monitor.notify();
        }
    }

    public void reset() {
        open = false;
    }
}


