/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.Korisnik;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOIzmeniKorisnikTest {
    
    private SOIzmeniKorisnik so;
    private Korisnik validanKorisnik;
    
    @BeforeEach
    void setUp(){
        so = new SOIzmeniKorisnik();
        Grad grad = new Grad(1, "Pancevo", "26000");
        validanKorisnik = new Korisnik(1, "Darko", "Serafinovski", "2010001860014", grad);
    }
    
    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovNevalidanID(){
        validanKorisnik.setIdKorisnik(0);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Nevažeći ID korisnika.", e.getMessage());
    }
    
    @Test
    void testPreduslovImeNull(){
        validanKorisnik.setIme(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovImePrazno(){
        validanKorisnik.setIme("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovPrezimeNull(){
        validanKorisnik.setPrezime(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovPrezimePrazno(){
        validanKorisnik.setPrezime("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovJmbgNull(){
        validanKorisnik.setJmbg(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovJmbgPrazan(){
        validanKorisnik.setJmbg("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovGradNull(){
        validanKorisnik.setGrad(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovGradIdNula(){
        Grad g = new Grad();
        g.setIdGrad(0);
        validanKorisnik.setGrad(g);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Svi podaci moraju biti popunjeni.", e.getMessage());
    }
    
    @Test
    void testPreduslovJMBGManjeOd13() {
        validanKorisnik.setJmbg("123");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("JMBG mora sadržati tačno 13 cifara.", e.getMessage());
    }

    @Test
    void testPreduslovJMBGViseOd13() {
        validanKorisnik.setJmbg("12345678901234");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("JMBG mora sadržati tačno 13 cifara.", e.getMessage());
    }

    @Test
    void testPreduslovJMBGSadrziSlova() {
        validanKorisnik.setJmbg("12345a7890123");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("JMBG mora sadržati tačno 13 cifara.", e.getMessage());
    }

    @Test
    void testPreduslovValidanKorisnik() {
        assertDoesNotThrow(() -> so.preduslov(validanKorisnik));
    }
    
    // ----- Izvrsi Operaciju -----
    
    @Test 
    void testIzvrsiOperacijuDaLiPozivaDBBrokerIzmeni() throws Exception{
        DBBroker dbMock = Mockito.mock(DBBroker.class);

        try(MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)){
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            
            so.izvrsiOperaciju(validanKorisnik);
            
            verify(dbMock, times(1)).izmeni(validanKorisnik);
        }
    }
    
    @Test
    void testIzvrsiOperacijuBacaGresku() throws SQLException{
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        doThrow(new SQLException("Greska u bazi")).when(dbMock).izmeni(validanKorisnik);
        
        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(DBBroker::getInstance).thenReturn(dbMock);

            assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanKorisnik));
            verify(dbMock, times(1)).izmeni(validanKorisnik);
        }
    }
    
    
    
}
