package kontroler;

import domen.Grad;
import domen.Korisnik;
import domen.Poslovnica;
import domen.Radnik;
import domen.Tiket;
import domen.Utakmica;
import java.io.IOException;
import java.net.Socket;
import transfer.KlijentskiZahtev;
import transfer.Receiver;
import transfer.Sender;
import transfer.ServerskiOdgovor;
import konstante.Operacije;

public class KlijentController {

    private static KlijentController instance;
    private Socket s;
    private Sender sender;
    private Receiver receiver;
    private boolean serverPun = false;

    private KlijentController() throws IOException, ClassNotFoundException {
        Socket s = new Socket("localhost", 9000);
        sender = new Sender(s);
        receiver = new Receiver(s);
        

        Object inicijalnaPoruka = receiver.receive();
        if (inicijalnaPoruka instanceof String) {
            String poruka = (String) inicijalnaPoruka;
            if (poruka.contains("Server je pun")) {
                System.out.println(poruka);
                s.close();
                return; // ili baci exception
            } else if (poruka.contains("Klijent se povezuje")) {
                System.out.println("Uspešno povezan na server.");
            }
        }

    }

    public static KlijentController getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new KlijentController();
        }
        return instance;
    }

    public boolean isServerPun() {
        return serverPun;
    }

    public ServerskiOdgovor login(Radnik r) throws IOException, ClassNotFoundException, Exception {
        if (serverPun) {
            throw new IOException("Nema mesta na serveru – konekcija odbijena.");
        }
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PRIJAVI_RADNIK, r);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public void logout() throws IOException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.LOGOUT, null);
        sender.send(kz);
        System.out.println("Odjavljen sa servera.");
    }

    public ServerskiOdgovor kreirajKorisnik(Korisnik k) throws IOException, ClassNotFoundException, Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.KREIRAJ_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor obrisiKorisnik(Korisnik k) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.OBRISI_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor pretraziKorisnika(Korisnik k) throws IOException, ClassNotFoundException, Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PRETRAZI_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor izmeniKorisnika(Korisnik k) throws IOException, ClassNotFoundException, Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PROMENI_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        
        return so;
    }
    
    public ServerskiOdgovor ubaciPoslovnica(Poslovnica p) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.UBACI_POSLOVNICA, p);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor promeniPoslovnica(Poslovnica p) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PROMENI_POSLOVNICA, p);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor kreirajTiket(Tiket t) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.KREIRAJ_TIKET, t);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuSviRadnik() throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_SVI_RADNIK, null);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuSviGrad() throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_SVI_GRAD, null);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuSviKorisnik() throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_SVI_KORISNIK, null);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuSviUtakmica() throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_SVI_UTAKMICA, null);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuKorisnikKriterijumGrad(Grad g) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_KORISNIK_KRITERIJUM_GRAD, g);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuKorisnikKriterijumKorisnik(Korisnik k) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_KORISNIK_KRITERIJUM_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumRadnik(Radnik r) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_TIKET_KRITERIJUM_RADNIK, r);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumKorisnik(Korisnik k) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_TIKET_KRITERIJUM_KORISNIK, k);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumUtakmica(Utakmica u) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_TIKET_KRITERIJUM_UTAKMICA, u);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumTiket(Tiket t) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.VRATI_LISTU_TIKET_KRITERIJUM_TIKET, t);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor pretraziTiket(Tiket t) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PRETRAZI_TIKET, t);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }
    
    public ServerskiOdgovor izmeniTiket(Tiket t) throws IOException, ClassNotFoundException{
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PROMENI_TIKET, t);
        sender.send(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) receiver.receive();
        return so;
    }



    
}
