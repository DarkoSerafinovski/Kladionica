/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;

/**
 * Sistemska operacija koja brise korisnika iz baze podataka.
 * Pre brisanja proverava se da li je prosledjen validan objekat korisnika.
 * 
 * @author Darko
 */

public class SOObrisiKorisnika extends AbstractSO{
    
    /**
     * Proverava da li je prosledjen validan korisnik za brisanje.
     * Baca izuzetak ako objekat nije klase {@link Korisnik} ili ako ID nije ispravan (Nije veci od 0).
     * 
     * @param parametar objekat klase {@link Korisnik} koji se brise
     * @throws Exception ako korisnik nije validan ili nema validan ID
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if (parametar == null || !(parametar instanceof Korisnik)) {
            throw new Exception("Niste prosledili validnog korisnika za brisanje.");
        }
        Korisnik k = (Korisnik) parametar;
        if (k.getIdKorisnik() <= 0) {
            throw new Exception("Korisnik nema validan ID.");
        }
    }
    
    /**
     * Izvrsava brisanje korisnika iz baze podataka.
     * 
     * @param parametar objekat klase {@link Korisnik} koji se brise
     * @throws Exception ako dodje do greske prilikom komunikacije sa bazom podataka
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        DBBroker.getInstance().obrisi(k);
    }
}
