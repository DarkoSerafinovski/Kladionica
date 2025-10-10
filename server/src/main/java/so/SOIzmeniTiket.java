/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Par;
import domen.Tiket;

/**
 * Sistemska operacija koja omogucava izmenu tiketa u bazi podataka.
 * Ukoliko prosledjeni tiket sadrzi parove, oni se kreiraju i azurira se moguci dobitak.
 * Ukoliko ne sadrzi parove, znaci da se u bazi menja samo status tiketa.
 * 
 * @author Darko
 */

public class SOIzmeniTiket extends AbstractSO{

     /**
     * Ova operacija ne zahteva posebne preduslove.
     * 
     * @param parametar objekat klase {@link Tiket}
     * @throws Exception ako dodje do greske tokom validacije ako se kasnije ubaci ista
     */
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    /**
     * Izvrsava izmenu podataka o tiketu u bazi. 
     * Ako tiket sadrzi listu parova, svaki se posebno upisuje u bazu,
     * a zatim se izracunava moguci dobitak na osnovu kvote i uloga.
     * 
     * @param parametar objekat klase {@link Tiket} koji se menja
     * @throws Exception ako dodje do greske prilikom upisa u bazu podataka
     */
    
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
