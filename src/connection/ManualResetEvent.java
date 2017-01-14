package connection;


class ManualResetEvent {
    private final Object monitor = new Object();
    private volatile boolean open = false;
    //=============


    ManualResetEvent(boolean open) {
        this.open = open;
    }

    void waitOne() {
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

    void set() {
        synchronized (monitor) {
            open = true;
            monitor.notify();
        }
    }

    void reset() {
        open = false;
    }
}


