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
public class SOIzmeniPoslovnica extends AbstractSO{

    @Override
    protected void preduslov(Object parametar) throws Exception {
        Poslovnica p = (Poslovnica) parametar;

        if (p == null) {
            throw new Exception("Poslovnica ne sme biti null.");
        }
        if (p.getIdPoslovnica() <= 0) {
            throw new Exception("Nevažeći ID poslovnice.");
        }
        if (p.getAdresa() == null || p.getAdresa().isEmpty()) {
            throw new Exception("Adresa poslovnice mora biti popunjena.");
        }
        if (p.getBrojTelefona() == null || p.getBrojTelefona().isEmpty()) {
            throw new Exception("Broj telefona poslovnice mora biti popunjen.");
        }
    }


    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Poslovnica p = (Poslovnica) parametar;
        DBBroker.getInstance().izmeni(p);
    }
    
}
