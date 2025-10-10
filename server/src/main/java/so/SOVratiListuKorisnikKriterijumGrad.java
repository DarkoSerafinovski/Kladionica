/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistemska operacija koja vraca listu korisnika iz baze podataka
 * na osnovu prosledjenog grada. Pretrazuje se samo po gradu, dok su
 * ostali atributi korisnika ignorisani.
 * 
 * @author Darko
 */

public class SOVratiListuKorisnikKriterijumGrad extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    /**
     * Proverava da li je parametar instanca klase {@link Grad}.
     * 
     * @param parametar objekat klase {@link Grad} po kojem se vrsi pretraga
     * @throws Exception ako parametar nije instanca klase Grad
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Grad)){
            throw new Exception("Parametar mora biti instanca grada.");
        }
    }

    /**
     * Izvrsava pretragu korisnika u bazi na osnovu prosledjenog grada.
     * Rezultat pretrage se smesta u listu.
     * 
     * @param parametar objekat klase {@link Grad} po kojem se filtrira lista korisnika
     * @throws Exception ako dodje do greske pri pretrazi u bazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = new Korisnik();
        Grad g = (Grad) parametar;
        
        k.setGrad(g);
        k.setIme(null);
        k.setPrezime(null);
        k.setJmbg(null);
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(k);
        
        setLista(lista1);
    }

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
    
}
