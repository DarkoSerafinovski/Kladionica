/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;

/**
 * Sistemska operacija koja omogucava pretragu korisnika u bazi podataka
 * na osnovu prosledjenog ID-a. Ako korisnik ne postoji, baca se izuzetak.
 * 
 * @author Darko
 */

public class SOPretraziKorisnik extends AbstractSO{
    
    private Korisnik rezultat;

    /**
     * Vraca korisnika koji je pronadjen tokom pretrage.
     * 
     * @return objekat klase {@link Korisnik} koji je pronadjen u bazi
     */
    
    public Korisnik getRezultat() {
        return rezultat;
    }

    /**
     * Izvrsava pretragu korisnika u bazi podataka.
     * Ako korisnik sa zadatim ID-em ne postoji, baca se izuzetak.
     * 
     * @param parametar objekat klase {@link Korisnik} sa postavljenim ID-em
     * @throws Exception ako korisnik ne postoji u bazi ili dodje do greske pri pretrazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        rezultat = (Korisnik) DBBroker.getInstance().vratiJednog(k);
        if (rezultat == null) {
            throw new Exception("Korisnik sa ID " + k.getIdKorisnik() + " ne postoji.");
        }
    }

    /**
     * Proverava da li je prosledjen validan korisnik za pretragu.
     * 
     * @param parametar objekat klase {@link Korisnik} koji se pretrazuje
     * @throws Exception ako korisnik nije prosledjen ili ID nije validan
     */
    @Override
    protected void preduslov(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        if (k == null || k.getIdKorisnik() <= 0) {
            throw new Exception("Neispravan ID korisnika za pretragu.");
        }
    }
}
