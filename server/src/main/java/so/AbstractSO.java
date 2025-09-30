/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.Konekcija;
import java.sql.SQLException;

/**
 *
 * @author Darko
 */
public abstract class AbstractSO {
    
    public void izvrsi(Object parametar) throws Exception{
        try{
            preduslov(parametar);
            izvrsiOperaciju(parametar);
            commit();
        }catch(Exception e){
            rollback();
            throw new RuntimeException("Greska prilikom izvrsavanja operacija: " + e.getMessage());
        }
    }
    
    protected abstract void preduslov(Object parametar) throws Exception;
    
    protected abstract void izvrsiOperaciju(Object parametar) throws Exception;

    protected void commit() throws SQLException, Exception{
        Konekcija.getInstance().getConnection().commit();
    }
    
    protected void rollback() throws SQLException, Exception{
        Konekcija.getInstance().getConnection().rollback();
    }
}
