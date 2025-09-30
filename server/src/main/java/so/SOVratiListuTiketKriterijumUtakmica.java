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
 *
 * @author Darko
 */
public class SOVratiListuTiketKriterijumUtakmica extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Utakmica)){
            throw new Exception("Parametar mora biti instanca utakmica.");
        }
    }

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
