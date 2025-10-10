/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;

/**
 * Sistemska operacija koja omogucava izmenu postojeceg korisnika u bazi podataka.
 * Pre izvrsenja operacije proverava se da li su svi potrebni podaci validni.
 * 
 * @author Darko
 */

public class SOIzmeniKorisnik extends AbstractSO{
    
    /**
     * Proverava da li prosledjeni objekat sadrzi sve potrebne i validne podatke
     * za izmenu korisnika. Ako neki uslov nije ispunjen, baca se izuzetak.
     * 
     * @param parametar objekat klase {@link Korisnik} koji se menja
     * @throws Exception ako ID korisnika nije validan, ako neki od obaveznih podataka
     *         nije popunjen ili ako JMBG nije tacno 13 cifara
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;

        if (k.getIdKorisnik() <= 0) {
            throw new Exception("Nevažeći ID korisnika.");
        }
            System.out.println("Ime: " + k.getIme() + " Prezime: " + k.getPrezime() + " JMBG: " + k.getJmbg() + " Grad: " +k.getGrad());
        if (k.getIme() == null || k.getIme().isEmpty() ||
            k.getPrezime() == null || k.getPrezime().isEmpty() ||
            k.getJmbg() == null || k.getJmbg().isEmpty() ||
            k.getGrad() == null || k.getGrad().getIdGrad() < 1) {
            throw new Exception("Svi podaci moraju biti popunjeni.");
        }

        if (!k.getJmbg().matches("\\d{13}")) {
            throw new Exception("JMBG mora sadržati tačno 13 cifara.");
        }
    }


    /**
     * Izvrsava operaciju izmene korisnika u bazi podataka.
     * 
     * @param parametar objekat klase {@link Korisnik} sa novim vrednostima
     * @throws Exception ako dodje do greske prilikom komunikacije sa bazom
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        DBBroker.getInstance().izmeni(k);
    }
}
