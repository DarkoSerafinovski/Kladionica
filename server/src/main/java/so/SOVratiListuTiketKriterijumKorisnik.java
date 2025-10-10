/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darko
 */
public class SOVratiListuTiketKriterijumKorisnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Korisnik)){
            throw new Exception("Parametar mora biti instanca korisnik.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik k = (Korisnik) parametar;  
    
        Tiket tiket = new Tiket();
        tiket.setKorisnik(k);
        tiket.setRadnik(null);
        tiket.setParovi(null);
        tiket.setUlog(0);
        tiket.setUkupnaKvota(0);
        tiket.setMoguciDobitak(0);
        tiket.setStatus(-1);

        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(tiket);

        setLista(lista1);
    }
    
}
