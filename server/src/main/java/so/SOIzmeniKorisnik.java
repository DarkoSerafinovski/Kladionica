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
public class SOIzmeniKorisnik extends AbstractSO{
    
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
        k.getGrad() == null || k.getGrad().getIdGrad() <= 0) {
        throw new Exception("Svi podaci moraju biti popunjeni.");
    }

    if (!k.getJmbg().matches("\\d{13}")) {
        throw new Exception("JMBG mora sadržati tačno 13 cifara.");
    }
}


    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        DBBroker.getInstance().izmeni(k);
    }
}
