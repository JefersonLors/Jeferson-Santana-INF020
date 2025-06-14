package impl;

import java.util.ArrayList;

public class Program {
    final int BUFFER_SIZE = 10;
    int itemCount;

    ArrayList<Integer> buffer;

    Semaphore mutex;
    Semaphore empty;
    Semaphore full;

    Program() {
        itemCount = 0;
        buffer = new ArrayList<>();
        mutex = new Semaphore(1);
        empty = new Semaphore(BUFFER_SIZE);
        full = new Semaphore(0);
    }

    public static void main(String[] args) {
       new Program().run();
    }

    public void run() {
        // instancia produtores
        Producer p = new Producer(this, "Producer 1", 500);
        Producer p1 = new Producer(this, "Producer 2", 400);

        // instancia consumidores
        Consumer c0 = new Consumer(this, "Consummer 1", 700);
        Consumer c1 = new Consumer(this, "Consummer 2", 400);
        Consumer c2 = new Consumer(this, "Consummer 3", 500);
        Consumer c3 = new Consumer(this, "Consummer 4", 550);
        Consumer c4 = new Consumer(this, "Consummer 5", 600);

        // inicia consumidores
        c0.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();

        // inicia produtores
        p.start();
        p1.start();
    }
}
