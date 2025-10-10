/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu tiketa iz baze podataka
 * filtriranih po prosledjenom objektu klase {@link Tiket}.
 * Omogucava pretragu tiketa prema specificnim atributima tiketa (Minimalna kvota, minimalni ulog,
 * minimalni dobitak ili status).
 * 
 * @author Darko
 */

public class SOVratiListuTiketKriterijumTiket extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    /**
     * Vraca listu tiketa pronadjenih po kriterijumu tiketa.
     * 
     * @return lista objekata tipa {@link OpstiDomenskiObjekat} koji predstavljaju tikete
     */
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    /**
     * Postavlja listu tiketa na prosledjenu vrednost.
     * 
     * @param lista lista objekata tipa {@link OpstiDomenskiObjekat} koja se postavlja
     */
    
    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    /**
     * Proverava da li je parametar instanca klase {@link Tiket}.
     * 
     * @param parametar objekat klase {@link Tiket} po kojem se vrsi filtriranje tiketa
     * @throws Exception ako parametar nije validan tiket
     */
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Tiket)){
            throw new Exception("Parametar mora biti instanca tiket.");
        }
    }

    /**
     * Izvrsava pretragu tiketa u bazi podataka prema prosledjenom objektu {@link Tiket}.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar objekat klase {@link Tiket} po kojem se filtrira lista tiketa
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        System.out.println(t);
        lista = DBBroker.getInstance().pretrazi(t);
    }
    
}
