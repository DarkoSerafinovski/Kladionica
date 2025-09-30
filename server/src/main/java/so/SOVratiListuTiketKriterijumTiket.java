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
 *
 * @author Darko
 */
public class SOVratiListuTiketKriterijumTiket extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        if(!(parametar instanceof Tiket)){
            throw new Exception("Parametar mora biti instanca tiker.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        System.out.println(t);
        lista = DBBroker.getInstance().pretrazi(t);
    }
    
}
