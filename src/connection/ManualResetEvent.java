package connection;


class ManualResetEvent {
    private final Object monitor = new Object();
    private volatile boolean open = false;
    //=============


    ManualResetEvent(boolean open) {
        this.open = open;
    }

    void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (!open) {
                monitor.wait();
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


