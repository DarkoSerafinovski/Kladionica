/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu svih korisnika iz baze podataka.
 * Pretraga ne koristi nikakve kriterijume te osim sto ukljucuje sve korisnike,
 * ukljucujuci i njihove gradove.
 * 
 * @author Darko
 */

public class SOVratiListuSviKorisnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    /**
     * Ova operacija ne zahteva preduslov.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri proveri preduslova
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    /**
     * Izvrsava pretragu svih korisnika u bazi podataka.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik korisnik = new Korisnik();
        
        korisnik.setIme(null);
        korisnik.setPrezime(null);
        korisnik.setJmbg(null);
        
        Grad grad = new Grad(0, null, null);
        
        korisnik.setGrad(grad);
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(korisnik);

        setLista(lista1);
    }

    /**
     * Vraca listu korisnika pronadjenih u bazi podataka.
     * 
     * @return lista objekata tipa {@link OpstiDomenskiObjekat} koji predstavljaju korisnike
     */
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    /**
     * Postavlja listu korisnika na prosledjenu vrednost.
     * 
     * @param lista lista objekata tipa {@link OpstiDomenskiObjekat} koja se postavlja
     */
    
    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    
}
