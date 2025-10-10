/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu svih gradova iz baze podataka.
 * Pretraga ne koristi nikakve kriterijume, vec vraca sve gradove iz baze.
 * 
 * @author Darko
 */

public class SOVratiListuSviGrad extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    /**
     * Ova operacija ne zahteva posebne preduslove.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri proveri preduslova
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    /**
     * Izvrsava pretragu svih gradova u bazi podataka.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar parametar operacije (ne koristi se)
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Grad grad = new Grad();
        

        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(grad);
        setLista(lista1);
        
    }

    /**
     * Vraca listu gradova pronadjenih u bazi podataka.
     * 
     * @return lista objekata tipa {@link OpstiDomenskiObjekat} koji predstavljaju gradove
     */
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    /**
     * Postavlja listu gradova na prosledjenu vrednost.
     * 
     * @param lista lista objekata tipa {@link OpstiDomenskiObjekat} koja se postavlja
     */
    
    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
}
