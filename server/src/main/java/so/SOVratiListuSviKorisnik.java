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
 *
 * @author Darko
 */
public class SOVratiListuSviKorisnik extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Korisnik korisnik = new Korisnik();
        
        korisnik.setIme(null);
        korisnik.setPrezime(null);
        korisnik.setJmbg(null);
        
        Grad grad = new Grad(0, null, null);
        
        korisnik.setGrad(grad);
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(korisnik);

        setLista(lista1);
    }

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    
}
