package zpo_watki;

import java.util.LinkedList;
import java.util.Random;

public class Threadexample {
    public static void main(String[] args) throws InterruptedException {
        // Object of a class that has both produce() and consume() methods
        final PC pc = new PC();

        // Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce("My cafe", 0,1000);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce("Cafe Nero", 0,600);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        t1.start();
        t2.start();
        t0.start();

        // t1 finishes before t2
        t1.join();
        t2.join();
        t0.join();

    }

    // This class has a list, producer (adds items to list and consumber (removes items).
    public static class PC {
        // Create a list shared by producer and consumer Size of list is 2.
        LinkedList<String> list = new LinkedList<>();
        int capacity = 2;

        // Function called by producer thread
        public void produce(String nazwaProducenta, int czasProdukcji, int przestoj) throws InterruptedException {
            Random generator = new Random();
            String valuet [] = {"kawa", "herbata", "ciasto", "czekolada", "lody", "lemoniada"};
            String value = "";

            while (true) {
                synchronized (this) {
                    // producer thread waits while list is full
                    while (list.size()==capacity)
                        wait();

                    value = valuet[generator.nextInt(6)];
                    czasProdukcji = generator.nextInt(20);
                    System.out.println(nazwaProducenta + " - " + value + " Czas produkcji " + czasProdukcji + " min");

                    // to insert the jobs in the list
                    list.add(value);

                    // notifies the consumer thread that now it can start consuming
                    notify();

                    // makes the working of program easier to understand
                    Thread.sleep(przestoj);
                }
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    // consumer thread waits while list is empty
                    while (list.size()==0)
                        wait();

                    //to retrive the ifrst job in the list
                    String val = list.removeFirst();

                    System.out.println("Konsument skonsumowal - " + val);

                    // Wake up producer thread
                    notify();

                    // and sleep
                    Thread.sleep(300);
                }
            }
        }
    }
}
