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
 *
 * @author Darko
 */
public class SOVratiListuTiketKriterijumRadnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Radnik)){
            throw new Exception("Parametar mora biti instanca radnika.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {

        Radnik radnik = (Radnik) parametar;  // parametar je Radnik
    
        Tiket tiket = new Tiket();
        tiket.setRadnik(radnik);
        tiket.setKorisnik(null);
        tiket.setParovi(null);
        tiket.setUlog(0);
        tiket.setUkupnaKvota(0);
        tiket.setMoguciDobitak(0);
        tiket.setStatus(-1);

        // Poziv brokera
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(tiket);

        // Rezultat setujemo u listu iz AbstractSO (ako je imaš)
        setLista(lista1);
    }
    
}
