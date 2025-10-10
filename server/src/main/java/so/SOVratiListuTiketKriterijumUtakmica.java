/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Par;
import domen.Tiket;
import domen.Utakmica;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu tiketa iz baze podataka
 * filtriranih po prosledjenoj utakmici. Omogucava pregled svih tiketa
 * koji sadrze dati mec u listi parova.
 * 
 * @author Darko
 */

public class SOVratiListuTiketKriterijumUtakmica extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    /**
     * Vraca listu tiketa pronadjenih po utakmici.
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
     * Proverava da li je parametar instanca klase {@link Utakmica}.
     * 
     * @param parametar objekat klase {@link Utakmica} po kojem se vrsi filtriranje tiketa
     * @throws Exception ako parametar nije validna utakmica
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Utakmica)){
            throw new Exception("Parametar mora biti instanca utakmica.");
        }
    }

    /**
     * Izvrsava pretragu tiketa u bazi podataka prema prosledjenoj utakmici.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar objekat klase {@link Utakmica} po kojem se filtrira lista tiketa
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Utakmica u = (Utakmica) parametar;
        Tiket t = new Tiket();
        Par p = new Par();
        p.setUtakmica(u);
        t.dodajPar(p);
        t.setStatus(-1);
        
        lista = DBBroker.getInstance().pretrazi(t);
    }
    
}
