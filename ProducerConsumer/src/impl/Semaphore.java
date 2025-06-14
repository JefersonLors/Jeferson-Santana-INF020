package impl;

public class Semaphore {
    protected int count;

    public Semaphore(int count) {
        this.count = count;
    }

    public synchronized void down() {
        while(count == 0){
            try {
                wait();
            } catch(InterruptedException _ex) { }
        }
        count--;
    }

    public synchronized void up() {
        count++;
        notify();
    }
}
