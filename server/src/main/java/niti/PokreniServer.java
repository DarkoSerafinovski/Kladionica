/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Receiver;
import transfer.Sender;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Darko
 */
public class PokreniServer extends Thread{

    private ServerSocket ss;
    private int maxBrojKlijenata;
    private List<ObradaKlijentskiZahteva> clientThreads;

    public PokreniServer(int maxBrojKlijenata) throws Exception {
        
        try{
            ss = new ServerSocket(9000);
        }catch(Exception e){
            throw new Exception("Server je vec pokrenut!");
        }
        this.maxBrojKlijenata = maxBrojKlijenata;
        this.clientThreads = new ArrayList<>();
    }
    
    
    @Override
public void run() {
    try {
        while (true) {
            Socket s = ss.accept();
            System.out.println("Klijent se povezao!");

            // kreiramo sender i receiver jednom po socketu
            Sender sender = new Sender(s);
            Receiver receiver = new Receiver(s);

            synchronized (clientThreads) {
                if (clientThreads.size() >= maxBrojKlijenata) {
                    try {
                        sender.send("Server je pun - nema slobodnih mesta.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        s.close();
                    }
                    continue;
                } else {
                    sender.send("Klijent se povezuje.");
                }

                ObradaKlijentskiZahteva obrada = new ObradaKlijentskiZahteva(s, sender, receiver, maxBrojKlijenata, this);
                clientThreads.add(obrada);
                obrada.start();
            }
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (ss != null && !ss.isClosed()) {
                System.out.println("Zatvoreno");
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



    
    public void removeClient(ObradaKlijentskiZahteva nit) {
        synchronized (clientThreads) {
            clientThreads.remove(nit);
            System.out.println("Klijent je odjavljen. Trenutno aktivnih: " + clientThreads.size());
        }
    }

    public int getMaxBrojKlijenata() {
        return maxBrojKlijenata;
    }

    public List<ObradaKlijentskiZahteva> getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(List<ObradaKlijentskiZahteva> clientThreads) {
        this.clientThreads = clientThreads;
    }
    
    
    
    
}
