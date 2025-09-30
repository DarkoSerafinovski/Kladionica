/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Par;
import domen.Tiket;

/**
 *
 * @author Darko
 */
public class SOIzmeniTiket extends AbstractSO{

    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;

        if(t.getParovi() != null && !t.getParovi().isEmpty()){
            
            for (Par par : t.getParovi()) {
                DBBroker.getInstance().kreiraj(par);
            }
            t.setMoguciDobitak(t.getUkupnaKvota() * t.getUlog());
            System.out.println("Kvota: " + t.getUkupnaKvota() + " Dobitak: " + t.getMoguciDobitak());
        }
        System.out.println("Izmena");
        DBBroker.getInstance().izmeni(t);
    }
    
    
}
