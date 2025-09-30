/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darko
 */
public class SOVratiListuSviGrad extends AbstractSO{

    private List<OpstiDomenskiObjekat> lista = new ArrayList<>();
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Grad grad = new Grad();
        
        grad.setNaziv(null);
        grad.setPostanskiBroj(null);
        
        List<OpstiDomenskiObjekat> lista1 = DBBroker.getInstance().pretrazi(grad);
        setLista(lista1);
        
    }

    public List<OpstiDomenskiObjekat> getLista() {
        return lista;
    }

    public void setLista(List<OpstiDomenskiObjekat> lista) {
        this.lista = lista;
    }
    
}
