package niti;

import baza.DBBroker;
import controller.ServerController;
import domen.Grad;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Poslovnica;
import domen.Radnik;
import domen.Tiket;
import domen.Utakmica;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacije;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import java.util.ArrayList;
import so.AbstractSO;
import transfer.Receiver;
import transfer.Sender;

public class ObradaKlijentskiZahteva extends Thread {

    private Socket s;
    private Sender sender;
    private Receiver receiver;
    private PokreniServer server;
    private int maxBrojKlijenata;

    public ObradaKlijentskiZahteva(Socket socket, Sender sender, Receiver receiver, int maxBrojKlijenata, PokreniServer server) throws IOException {
        this.s = socket;
        this.sender = sender;
        this.receiver = receiver;
        this.maxBrojKlijenata = maxBrojKlijenata;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (!s.isClosed()) {
                System.out.println("Ceka zahtev");
                KlijentskiZahtev kz = (KlijentskiZahtev) receiver.receive();
                ServerskiOdgovor so = obradiZahtev(kz);
                sender.send(so);
            }
        } catch (SocketException ex) {
            System.out.println(server.getClientThreads().size());
            server.getClientThreads().remove(this);
            System.out.println(server.getClientThreads().size());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private ServerskiOdgovor obradiZahtev(KlijentskiZahtev kz) {
        
        ServerskiOdgovor odgovor = new ServerskiOdgovor();
        try{
            
            switch(kz.getOperacija()){
                case Operacije.PRIJAVI_RADNIK:
                    odgovor = ServerController.getInstance().prijaviRadnik((Radnik) kz.getParametar());
                    break;
                case Operacije.LOGOUT:
                    odjaviKlijenta();
                    break;
                case Operacije.KREIRAJ_KORISNIK:
                    odgovor = ServerController.getInstance().kreirajKorisnik();
                    break;
                case Operacije.OBRISI_KORISNIK:
                    odgovor = ServerController.getInstance().obrisiKorisnik((Korisnik) kz.getParametar());
                    break;
                case Operacije.PRETRAZI_KORISNIK:
                    odgovor = ServerController.getInstance().pretraziKorisnika((Korisnik) kz.getParametar());
                    break;
                case Operacije.PROMENI_KORISNIK:
                    odgovor = ServerController.getInstance().izmeniKorisnik((Korisnik) kz.getParametar());
                    break;
                case Operacije.UBACI_POSLOVNICA:
                    odgovor = ServerController.getInstance().ubaciPoslovnica((Poslovnica) kz.getParametar());
                    break;
                case Operacije.PROMENI_POSLOVNICA:
                    odgovor = ServerController.getInstance().izmeniPoslovnica((Poslovnica) kz.getParametar());
                    break;
                case Operacije.KREIRAJ_TIKET:
                    odgovor = ServerController.getInstance().kreirajTiket((Tiket) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_SVI_RADNIK:
                    odgovor = ServerController.getInstance().vratiListaSviRadnik();
                    break;
                case Operacije.VRATI_LISTU_SVI_GRAD:
                    odgovor = ServerController.getInstance().vratiListaSviGrad();
                    break;
                case Operacije.VRATI_LISTU_SVI_KORISNIK:
                    odgovor = ServerController.getInstance().vratiListaSviKorisnik();
                    break;
                case Operacije.VRATI_LISTU_SVI_UTAKMICA:
                    odgovor = ServerController.getInstance().vratiListaSviUtakmica();
                    break;
                case Operacije.VRATI_LISTU_KORISNIK_KRITERIJUM_GRAD:
                    odgovor = ServerController.getInstance().vratiListuKorisnikKriterijumGrad((Grad) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_KORISNIK_KRITERIJUM_KORISNIK:
                    odgovor = ServerController.getInstance().vratiListuKorisnikKriterijumKorisnik((Korisnik) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_TIKET_KRITERIJUM_RADNIK:
                    odgovor = ServerController.getInstance().vratiListuTiketKriterijumRadnik((Radnik) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_TIKET_KRITERIJUM_KORISNIK:
                    odgovor = ServerController.getInstance().vratiListuTiketKriterijumKorisnik((Korisnik) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_TIKET_KRITERIJUM_UTAKMICA:
                    odgovor = ServerController.getInstance().vratiListuTiketKriterijumUtakmica((Utakmica) kz.getParametar());
                    break;
                case Operacije.VRATI_LISTU_TIKET_KRITERIJUM_TIKET:
                    odgovor = ServerController.getInstance().vratiListuTiketKriterijumTiket((Tiket) kz.getParametar());
                    break;
                case Operacije.PRETRAZI_TIKET:
                    odgovor = ServerController.getInstance().pretraziTiket((Tiket) kz.getParametar());
                    break;
                case Operacije.PROMENI_TIKET:
                    odgovor = ServerController.getInstance().izmeniTiket((Tiket) kz.getParametar());
                    break;
                    
                default:
                    odgovor.setUspesno(false);
                    odgovor.setPoruka("Nepoznata operacija: " + kz.getOperacija());
                    break;
            }
        }catch(Exception ex){
            odgovor.setUspesno(false);
            odgovor.setException(ex);
        }
        return odgovor;
        
    }

    private void odjaviKlijenta() {
        try{
            s.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            server.removeClient(this);
        }
    }
}