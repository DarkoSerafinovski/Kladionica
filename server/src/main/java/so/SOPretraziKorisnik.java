/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;

/**
 *
 * @author Darko
 */
public class SOPretraziKorisnik extends AbstractSO{
    
    private Korisnik rezultat;

    public Korisnik getRezultat() {
        return rezultat;
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        rezultat = (Korisnik) DBBroker.getInstance().vratiJednog(k);
        if (rezultat == null) {
            throw new Exception("Korisnik sa ID " + k.getIdKorisnik() + " ne postoji.");
        }
    }

    @Override
    protected void preduslov(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        if (k == null || k.getIdKorisnik() <= 0) {
            throw new Exception("Neispravan ID korisnika za pretragu.");
        }
    }
}
