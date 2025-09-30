/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Tiket;

/**
 *
 * @author Darko
 */
public class SOPretraziTiket extends AbstractSO{

    private Tiket rezultat;

    public Tiket getRezultat() {
        return rezultat;
    }

    public void setRezultat(Tiket rezultat) {
        this.rezultat = rezultat;
    }
    
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        if (t == null || t.getIdTiket()<= 0) {
            throw new Exception("Neispravan ID tiketa za pretragu.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        rezultat = (Tiket) DBBroker.getInstance().vratiJednog(t);
        if (rezultat == null) {
            throw new Exception("Tiket sa ID " + t.getIdTiket()+ " ne postoji.");
        }
    }
    
}
