package zpo_watki_pliki3;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class PC {
	
	private char[] bufor;	
	private LinkedList<WatekCzytajacy> listaWatkow = new LinkedList<WatekCzytajacy>();

	public void produce(WatekCzytajacy watekCzytajacy, FileInputStream plik) throws InterruptedException, IOException {

        synchronized (this) {
        	
            while (this.listaWatkow.size() > 0 && this.listaWatkow.getFirst() != watekCzytajacy)
                wait();

            if ((int) bufor[0] == 0) {
            	
            	bufor[0] = (char) plik.read();
                System.out.println("Odczytany bufor: \t" + bufor[0]);
                
                nowyBufor(bufor);

                if((int) bufor[0] == 10) { 
                    if(plik.available() > 0)
                        this.listaWatkow.add(watekCzytajacy);

                    listaUsunPierwszy();
                    
                } else if (plik.available() == 0) {
                    listaUsunPierwszy();
                    watekCzytajacy.zatrzymaj();

                    nowyBufor(new char[1]);
                    return;
                }
                
                notifyAll();
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
    
    public void listaDodaj(WatekCzytajacy watek){
        if(this.listaWatkow.indexOf(watek) == -1)
            this.listaWatkow.add(watek);
    }
    
    public void listaUsunPierwszy(){
        if(this.listaWatkow.size() > 0)
            this.listaWatkow.removeFirst();
    }

}