/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Radnik;

/**
 * Sistemska operacija koja omogucava prijavu radnika u sistem.
 * Proverava validnost korisnickog imena i lozinke, a zatim ucitava podatke radnika iz baze.
 * 
 * @author Darko
 */
public class SOPrijavaRadnika extends AbstractSO{

    private Radnik radnik;
    
    /**
     * Vraca radnika koji je uspesno prijavljen.
     * 
     * @return objekat klase {@link Radnik} koji je prijavljen
     */
    
    public Radnik getRadnik(){
        return radnik;
    }
    
    /**
     * Izvrsava prijavu radnika tako sto ucitava njegove podatke iz baze na osnovu korisnickog imena i lozinke.
     * 
     * @param parametar objekat klase {@link Radnik} sa korisnickim imenom i lozinkom
     * @throws Exception ako dodje do greske prilikom ucitavanja iz baze
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception{
        Radnik r = (Radnik) parametar;
        Radnik r1;
        r1 = (Radnik) DBBroker.getInstance().vratiJednog(r);
        radnik = r1;
    }

    /**
     * Proverava preduslove prijave radnika.
     * Baca izuzetak ako objekat nije klase {@link Radnik}, ili ako korisnicko ime
     * ili lozinka nisu uneti ili lozinka nije dovoljno duga.
     * 
     * @param parametar objekat klase {@link Radnik} koji se prijavljuje
     * @throws Exception ako preduslovi nisu ispunjeni
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if (!(parametar instanceof Radnik)) {
            throw new Exception("Nevalidan objekat za prijavu!");
        }

        Radnik r = (Radnik) parametar;

        if (r.getKorisnickoIme() == null || r.getKorisnickoIme().isEmpty()) {
            throw new Exception("Korisničko ime nije uneto!");
        }

        if (r.getLozinka() == null || r.getLozinka().isEmpty()) {
            throw new Exception("Lozinka nije uneta!");
        }

        if (r.getLozinka().length() < 4) {
            throw new Exception("Lozinka mora imati bar 4 karaktera!");
        }
    }

    
}
