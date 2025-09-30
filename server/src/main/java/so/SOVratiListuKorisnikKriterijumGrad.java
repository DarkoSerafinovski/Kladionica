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
public class SOVratiListuKorisnikKriterijumGrad extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Grad)){
            throw new Exception("Parametar mora biti instanca grada.");
        }
    }

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

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
}
