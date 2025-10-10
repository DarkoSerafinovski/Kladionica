/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu korisnika iz baze podataka
 * na osnovu prosledjenog objekta korisnika. Omogucava pretragu
 * po vise atributa korisnika (ime, prezime, JMBG itd.).
 * 
 * @author Darko
 */

public class SOVratiListuKorisnikKriterijumKorisnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    /**
     * Vraca listu korisnika pronadjenih u pretrazi.
     * 
     * @return lista objekata tipa {@link OpstiDomenskiObjekat} koji predstavljaju korisnike
     */
    
    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    /**
     * Postavlja listu korisnika na prosledjenu vrednost.
     * 
     * @param lista lista objekata tipa {@link OpstiDomenskiObjekat} koja se postavlja
     */
    
    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    /**
     * Proverava da li je parametar instanca klase {@link Korisnik}.
     * 
     * @param parametar objekat klase {@link Korisnik} po kojem se vrsi pretraga
     * @throws Exception ako parametar nije instance korisnik
     */
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Korisnik)){
            throw new Exception("Parametar mora biti instanca korisnik.");
        }
    }

    /**
     * Izvrsava pretragu korisnika u bazi podataka prema prosledjenom kriterijumu.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar objekat klase {@link Korisnik} po kojem se filtrira lista korisnika
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(k);
        setLista(lista1);
    }
    
}
