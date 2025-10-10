/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu svih radnika iz baze.
 * Pretraga ne koristi nikakve kriterijume, vec vraca sve radnike koji su u bazi.
 * 
 * @author Darko
 */

public class SOVratiListuSviRadnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    /**
     * Ova operacija ne zahteva preduslove.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri proveri preduslova
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        
    }

    /**
     * Izvrsava pretragu svih radnika u bazi podataka.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Radnik radnik = new Radnik();

        radnik.setKorisnickoIme(null);
        radnik.setLozinka(null);
        radnik.setIme(null);
        radnik.setPrezime(null);
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(radnik);

        setLista(lista1);
    }
    
    /**
     * Vraca listu radnika pronadjenih u bazi podataka.
     * 
     * @return lista objekata tipa {@link OpstiDomenskiObjekat} koji predstavljaju radnike
     */

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    /**
     * Postavlja listu radnika na prosledjenu vrednost.
     * 
     * @param lista lista objekata tipa {@link OpstiDomenskiObjekat} koja se postavlja
     */
    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
}
