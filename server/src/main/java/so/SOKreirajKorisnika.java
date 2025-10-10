/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;

/**
 * Sistemska operacija koja kreira novog korisnika u bazi podataka.
 * Nakon uspesnog kreiranja, cuva se generisani ID korisnika.
 * 
 * @author Darko
 */

public class SOKreirajKorisnika extends AbstractSO {

    private Korisnik korisnik = new Korisnik();

    /**
     * Vraca korisnika koji je kreiran tokom izvrsavanja operacije.
     * 
     * @return objekat klase {@link Korisnik} koji je kreiran
     */
    
    public Korisnik getKorisnik() {
        return korisnik;
    }

    /**
     * Ova operacija ne zahteva posebne preduslove.
     * 
     * @param parametar objekat operacije (nije potreban)
     * @throws Exception ako dodje do greske prilikom provere preduslova
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    /**
     * Izvrsava kreiranje novog korisnika u bazi podataka.
     * Dobijeni ID se postavlja u lokalni objekat korisnika.
     * 
     * @param parametar objekat klase {@link Korisnik} (nije potreban u ovoj implementaciji)
     * @throws Exception ako dodje do greske prilikom upisa u bazu
     */
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        
        int id = DBBroker.getInstance().kreiraj(new Korisnik());
        korisnik.setIdKorisnik(id);
    }
}
