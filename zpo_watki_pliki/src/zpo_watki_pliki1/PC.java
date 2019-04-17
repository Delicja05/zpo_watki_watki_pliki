package zpo_watki_pliki1;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PC {
	
	private char[] bufor;	

	public void produce(WatekCzytajacy watekCzytajacy, FileInputStream plik) throws InterruptedException, IOException {

        synchronized (this) {
        	
            while ((int)bufor[0]!=0)
                wait();

            while(plik.available() > 0) {
            	bufor[0] = (char) plik.read();
            	System.out.println("Odczytany bufor: \t" + bufor[0]);
            	
            	notifyAll();
            	
            	if (plik.available() == 0) {
                    watekCzytajacy.zatrzymaj();
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

}