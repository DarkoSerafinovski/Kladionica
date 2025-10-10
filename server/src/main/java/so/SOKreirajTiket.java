/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Tiket;

/**
 * Sistemska operacija koja kreira novi tiket u bazi podataka.
 * Pre kreiranja postavlja pocetne vrednosti za ulog, status, moguci dobitak i kvotu.
 * 
 * @author Darko
 */

public class SOKreirajTiket extends AbstractSO {

    private Tiket tiket;

    /**
     * Vraca tiket koji je kreiran tokom izvrsavanja operacije.
     * 
     * @return objekat klase {@link Tiket} koji je kreiran
     */
    
    public Tiket getTiket() {
        return tiket;
    }

    /**
     * Ova operacija ne zahteva posebne preduslove.
     * 
     * @param parametar objekat operacije (nije potreban)
     * @throws Exception ako dodje do greske prilikom provere preduslova ukoliko se on kasnije doda
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        
    }

    /**
     * Izvrsava kreiranje novog tiketa u bazi podataka.
     * Postavlja pocetne vrednosti atributa i dodeljuje generisani ID.
     * 
     * @param parametar objekat klase {@link Tiket} koji se kreira
     * @throws Exception ako dodje do greske prilikom upisa u bazu
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;

        t.setUlog(0.0); 
        t.setStatus(0); 
        t.setMoguciDobitak(0);
        t.setUkupnaKvota(1);
        
        int id = DBBroker.getInstance().kreiraj(t);
        t.setIdTiket(id);

        this.tiket = t;
    }
}
