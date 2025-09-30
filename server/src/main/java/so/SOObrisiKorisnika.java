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
public class SOObrisiKorisnika extends AbstractSO{
    
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

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        DBBroker.getInstance().obrisi(k);
    }
}
