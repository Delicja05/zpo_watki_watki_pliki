package zpo_watki_pliki2;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        final PC pc = new PC();        
        char[] buf = new char[1];
        
        pc.nowyBufor(buf);
        pc.setObecnyWatek(null);
        
        WatekCzytajacy wc1 = new WatekCzytajacy(pc, "plik1.txt");
        WatekCzytajacy wc2 = new WatekCzytajacy(pc, "plik2.txt");
        WatekCzytajacy wc3 = new WatekCzytajacy(pc, "plik3.txt");
        
        WatekPiszacy wp1 = new WatekPiszacy(pc, "wynik.txt");

        Thread t1 = new Thread(wc1);
        Thread t2 = new Thread(wc2);
        Thread t3 = new Thread(wc3);

        Thread t4 = new Thread(wp1);
        
        t4.start();

        t1.start();
        t2.start();
        t3.start();
        
    }
}