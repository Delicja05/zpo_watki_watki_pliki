package zpo_watki_pliki2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class WatekPiszacy implements Runnable {
	
    private PC pc;
    private String nazwapliku;

    WatekPiszacy(PC pc, String nazwapliku) {
        this.pc = pc;
        this.nazwapliku = nazwapliku;
    }

    @Override
    public void run() {
    	
    	FileOutputStream plik;
    	
        try {
            plik = new FileOutputStream(new File(nazwapliku));
            pc.consume(plik);
            
        } catch (InterruptedException | FileNotFoundException e) {
        	System.out.print("Blad: \t");
            e.printStackTrace();
        }
    }
}
