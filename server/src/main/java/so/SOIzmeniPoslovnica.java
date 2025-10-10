/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Poslovnica;

/**
 * Sistemska operacija koja omogucava izmenu postojece poslovnice u bazi podataka.
 * Pre izmene proverava se da li su svi podaci o poslovnici ispravni i popunjeni.
 * 
 * @author Darko
 */

public class SOIzmeniPoslovnica extends AbstractSO{

    /**
     * Proverava da li je prosledjeni objekat poslovnice validan za izmenu.
     * Baca izuzetak ako je parametar null, neki od podataka nije unet ili ako nije ispravan.
     * 
     * @param parametar objekat klase {@link Poslovnica} koji se menja
     * @throws Exception ako poslovnica nije validna, ako je null, ID manji ili jednak nuli,
     *         ili ako adresa i broj telefona nisu popunjeni (null vrednost ili prazan string.
     */
    
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

    /**
     * Izvrsava operaciju izmene podataka o poslovnici u bazi.
     * 
     * @param parametar objekat klase {@link Poslovnica} sa novim vrednostima
     * @throws Exception ako dodje do greske prilikom izmene u bazi podataka
     */

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Poslovnica p = (Poslovnica) parametar;
        DBBroker.getInstance().izmeni(p);
    }
    
}
