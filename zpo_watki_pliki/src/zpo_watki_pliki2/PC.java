package zpo_watki_pliki2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PC {
	
	private char[] bufor;
	private WatekCzytajacy obecny;

	public void produce(WatekCzytajacy watekCzytajacy, FileInputStream plik) throws InterruptedException, IOException {

        synchronized (this) {
        	
        	this.tryObecnyWatek(watekCzytajacy);
        	
            while (this.obecny != watekCzytajacy) {
                wait();
                this.tryObecnyWatek(watekCzytajacy);
            }
            while ((int) bufor[0] == 0) {
            	
            	bufor[0] = (char) plik.read();
                System.out.println("Odczytany bufor: \t" + bufor[0]);
                notifyAll();
                
                if (plik.available() == 0) {
                    watekCzytajacy.zatrzymaj();
                    this.setObecnyWatek(null);
                }
                
                if((int) bufor[0] == 10){
                    this.setObecnyWatek(null);
                }
                break;
            }
        }

    }

    public void consume(FileOutputStream plik) throws InterruptedException {
    	
        while (true) {        	
            synchronized (this) {

                while ((int) bufor[0] == 0)
                    wait();
                
                
                try {
                    plik.write(bufor[0]);
                    System.out.println("Zapisany bufor: \t" + bufor[0]);
                    
                    bufor = new char[1];
                    
                } catch (IOException e) {
                	System.out.print("Blad: \t");
                    e.printStackTrace();
                }

                notifyAll();

                Thread.sleep(30);
            }
        }
    }
    

    public void nowyBufor(char[] bufor) {
        this.bufor = bufor;
    }
    
    public void setObecnyWatek(WatekCzytajacy watek) {
        this.obecny = watek;
    }
    public void tryObecnyWatek(WatekCzytajacy watek){
        if(this.obecny == null){
            this.setObecnyWatek(watek);
        }
    }

}