/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Tiket;

/**
 *
 * @author Darko
 */
public class SOKreirajTiket extends AbstractSO {

    private Tiket tiket;

    public Tiket getTiket() {
        return tiket;
    }

    @Override
    protected void preduslov(Object parametar) throws Exception {
        
    }

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
