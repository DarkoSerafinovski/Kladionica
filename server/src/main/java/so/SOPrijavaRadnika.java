/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Radnik;

/**
 *
 * @author Darko
 */
public class SOPrijavaRadnika extends AbstractSO{

    private Radnik radnik;
    
    public Radnik getRadnik(){
        return radnik;
    }
    
    @Override
    protected void izvrsiOperaciju(Object parametar) throws Exception{
        Radnik r = (Radnik) parametar;
        Radnik r1;
        r1 = (Radnik) DBBroker.getInstance().vratiJednog(r);
        radnik = r1;
    }

    @Override
    protected void preduslov(Object parametar) throws Exception {
        if (!(parametar instanceof Radnik)) {
            throw new Exception("Nevalidan objekat za prijavu!");
        }

        Radnik r = (Radnik) parametar;

        if (r.getKorisnickoIme() == null || r.getKorisnickoIme().isEmpty()) {
            throw new Exception("Korisničko ime nije uneto!");
        }

        if (r.getLozinka() == null || r.getLozinka().isEmpty()) {
            throw new Exception("Lozinka nije uneta!");
        }

        if (r.getLozinka().length() < 4) {
            throw new Exception("Lozinka mora imati bar 4 karaktera!");
        }
    }

    
}
