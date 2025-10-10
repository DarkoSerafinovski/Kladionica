/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Poslovnica;

/**
 * Sistemska operacija koja ubacuje novu poslovnicu u bazu podataka.
 * Pre upisa proverava da li su svi obavezni podaci o poslovnici uneti.
 * 
 * @author Darko
 */

public class SOUbaciPoslovnica extends AbstractSO{
    
    /**
     * Proverava da li je prosledjeni objekat validan za ubacivanje poslovnice.
     * Baca izuzetak ako objekat nije klase {@link Poslovnica} ili ako neki obavezni
     * podatak nije popunjen.
     * 
     * @param parametar objekat klase {@link Poslovnica} koji se ubacuje
     * @throws Exception ako parametar nije validan ili nedostaju obavezni podaci
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if (parametar == null || !(parametar instanceof Poslovnica)) {
            throw new Exception("Parametar mora biti objekat tipa Poslovnica.");
        }
        Poslovnica p = (Poslovnica) parametar;
        
        if (p.getAdresa() == null || p.getAdresa().isEmpty()) {
            throw new Exception("Adresa poslovnice je obavezna.");
        }
        if (p.getBrojTelefona() == null || p.getBrojTelefona().isEmpty()) {
            throw new Exception("Broj telefona poslovnice je obavezan.");
        }
    }

    /**
     * Izvrsava ubacivanje poslovnice u bazu podataka.
     * 
     * @param parametar objekat klase {@link Poslovnica} koji se ubacuje
     * @throws Exception ako dodje do greske prilikom upisa u bazu
     */
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Poslovnica p = (Poslovnica) parametar;
        DBBroker.getInstance().kreiraj(p);
    }
}
