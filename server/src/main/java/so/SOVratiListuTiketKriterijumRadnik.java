/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.Tiket;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu tiketa iz baze podataka
 * filtriranih po prosledjenom radniku. Omogucava pregled svih tiketa
 * koje je odredjeni radnik otkucao.
 * 
 * @author Darko
 */

public class SOVratiListuTiketKriterijumRadnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    /**
     * Vraca listu pronadjenih tiketa.
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
     * Proverava da li je parametar instanca klase {@link Radnik}.
     * 
     * @param parametar objekat klase {@link Radnik} po kojem se vrsi filtriranje tiketa
     * @throws Exception ako parametar nije validan radnik
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Radnik)){
            throw new Exception("Parametar mora biti instanca radnika.");
        }
    }

    /**
     * Izvrsava pretragu tiketa u bazi podataka prema radniku.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar objekat klase {@link Radnik} po kojem se filtrira lista tiketa
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {

        Radnik radnik = (Radnik) parametar;  
    
        Tiket tiket = new Tiket();
        tiket.setRadnik(radnik);
        tiket.setKorisnik(null);
        tiket.setParovi(null);
        tiket.setUlog(0);
        tiket.setUkupnaKvota(0);
        tiket.setMoguciDobitak(0);
        tiket.setStatus(-1);

        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(tiket);

        setLista(lista1);
    }
    
}
