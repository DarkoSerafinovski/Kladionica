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
public class SOKreirajKorisnika extends AbstractSO {

    private Korisnik korisnik = new Korisnik();

    public Korisnik getKorisnik() {
        return korisnik;
    }

    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        
        int id = DBBroker.getInstance().kreiraj(new Korisnik());
        korisnik.setIdKorisnik(id);
    }
}
