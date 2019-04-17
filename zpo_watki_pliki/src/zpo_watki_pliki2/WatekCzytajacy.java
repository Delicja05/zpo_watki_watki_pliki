package zpo_watki_pliki2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WatekCzytajacy implements Runnable {
	
    private PC pc;
    private String nazwapliku;
    
    private volatile boolean wyjscie = true;

    WatekCzytajacy(PC pc, String nazwapliku){
        this.pc = pc;
        this.nazwapliku = nazwapliku;
    }

    @Override
    public void run() {
    	
    	FileInputStream plik;
    	
        try {
            plik = new FileInputStream(new File(nazwapliku));
            
            while(wyjscie) {
                pc.produce(this, plik);
            }
            
            System.out.println("\n //  Watek - " + nazwapliku + " - zatrzmany  //");
            
        } catch (InterruptedException | IOException e) {
        	System.out.print("Blad: \t");
            e.printStackTrace();
        }
    }

    public void zatrzymaj(){
    	wyjscie = false;
    }
}
