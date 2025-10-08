/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Poslovnica;
import java.sql.SQLException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOUbaciPoslovnicaTest {
    
    private SOUbaciPoslovnica so;
    private Poslovnica validnaPoslovnica;

    @BeforeEach
    void setUp() {
        so = new SOUbaciPoslovnica();
        validnaPoslovnica = new Poslovnica(1, "Bulevar kralja Aleksandra 73", "0111234567");
    }
    
    // ----- PREDUSLOV -----

    @Test
    void testPreduslovNevalidanObjekat() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(new Object()));
        assertEquals("Parametar mora biti objekat tipa Poslovnica.", e.getMessage());
    }

    @Test
    void testPreduslovNullParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Parametar mora biti objekat tipa Poslovnica.", e.getMessage());
    }

    @Test
    void testPreduslovAdresaNull() {
        validnaPoslovnica.setAdresa(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Adresa poslovnice je obavezna.", e.getMessage());
    }

    @Test
    void testPreduslovAdresaPrazna() {
        validnaPoslovnica.setAdresa("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Adresa poslovnice je obavezna.", e.getMessage());
    }

    @Test
    void testPreduslovBrojTelefonaNull() {
        validnaPoslovnica.setBrojTelefona(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Broj telefona poslovnice je obavezan.", e.getMessage());
    }

    @Test
    void testPreduslovBrojTelefonaPrazan() {
        validnaPoslovnica.setBrojTelefona("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Broj telefona poslovnice je obavezan.", e.getMessage());
    }

    @Test
    void testPreduslovValidanObjekat() {
        assertDoesNotThrow(() -> so.preduslov(validnaPoslovnica));
    }

    // ----- IZVRSI OPERACIJU -----

    @Test
    void testIzvrsiOperacijuPozivaDBBrokerKreiraj() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validnaPoslovnica);

            verify(dbMock, times(1)).kreiraj(validnaPoslovnica);
        }
    }

    @Test
    void testIzvrsiOperacijuBacaSQLException() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);

        doThrow(new SQLException("Greska u ubacivanju")).when(dbMock).kreiraj(any(Poslovnica.class));
        
        try(MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)){
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            
            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validnaPoslovnica));
            assertEquals("Greska u ubacivanju", e.getMessage());
        }
    }
}
