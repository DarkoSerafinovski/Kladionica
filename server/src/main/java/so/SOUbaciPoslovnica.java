/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Poslovnica;

/**
 *
 * @author Darko
 */
public class SOUbaciPoslovnica extends AbstractSO{
    
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

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Poslovnica p = (Poslovnica) parametar;
        DBBroker.getInstance().kreiraj(p);
    }
}
