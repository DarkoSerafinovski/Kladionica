/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Tiket;

/**
 * Sistemska operacija koja omogucava pretragu tiketa u bazi podataka
 * na osnovu prosledjenog ID-a. Ako tiket ne postoji, baca se izuzetak.
 * 
 * @author Darko
 */

public class SOPretraziTiket extends AbstractSO{

    private Tiket rezultat;

    /**
     * Vraca tiket koji je pronadjen tokom pretrage.
     * 
     * @return objekat klase {@link Tiket} koji je pronadjen u bazi
     */
    
    public Tiket getRezultat() {
        return rezultat;
    }

    /**
     * Postavlja rezultat pretrage na zadati tiket.
     * 
     * @param rezultat objekat klase {@link Tiket} koji se postavlja kao rezultat
     */
    
    public void setRezultat(Tiket rezultat) {
        this.rezultat = rezultat;
    }
    
    /**
     * Proverava da li je prosledjen validan tiket za pretragu.
     * 
     * @param parametar objekat klase {@link Tiket} koji se pretrazuje
     * @throws Exception ako tiket nije prosledjen ili ID nije validan (Manji je od 1)
     */
    
    @Override
    protected void preduslov(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        if (t == null || t.getIdTiket()<= 0) {
            throw new Exception("Neispravan ID tiketa za pretragu.");
        }
    }

    /**
     * Izvrsava pretragu tiketa u bazi podataka.
     * Ako tiket sa zadatim ID-em ne postoji, baca se izuzetak.
     * 
     * @param parametar objekat klase {@link Tiket} sa postavljenim ID-em
     * @throws Exception ako tiket ne postoji u bazi ili dodje do greske pri pretrazi
     */
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception {
        Tiket t = (Tiket) parametar;
        rezultat = (Tiket) DBBroker.getInstance().vratiJednog(t);
        if (rezultat == null) {
            throw new Exception("Tiket sa ID " + t.getIdTiket()+ " ne postoji.");
        }
    }
    
}
