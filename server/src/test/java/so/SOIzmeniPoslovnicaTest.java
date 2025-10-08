/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Poslovnica;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOIzmeniPoslovnicaTest {
    
    private SOIzmeniPoslovnica so;
    private Poslovnica validnaPoslovnica;

    @BeforeEach
    void setUp() {
        so = new SOIzmeniPoslovnica();

        validnaPoslovnica = new Poslovnica(1, "Kralja Petra 10", "011/123-4567");
    }

    // ----- PREDUSLOV -----

    @Test
    void testPreduslovNullPoslovnica() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Poslovnica ne sme biti null.", e.getMessage());
    }

    @Test
    void testPreduslovNevalidanId() {
        validnaPoslovnica.setIdPoslovnica(0);

        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Nevažeći ID poslovnice.", e.getMessage());
    }
    
    @Test
    void testPreduslovNullAdresa() {
        validnaPoslovnica.setAdresa(null);

        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Adresa poslovnice mora biti popunjena.", e.getMessage());
    }

    @Test
    void testPreduslovPraznaAdresa() {
        validnaPoslovnica.setAdresa("");

        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Adresa poslovnice mora biti popunjena.", e.getMessage());
    }

    @Test
    void testPreduslovNullBrojTelefona() {
        validnaPoslovnica.setBrojTelefona(null);

        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Broj telefona poslovnice mora biti popunjen.", e.getMessage());
    }
    
    @Test
    void testPreduslovPrazanTelefon() {
        validnaPoslovnica.setBrojTelefona("");

        Exception e = assertThrows(Exception.class, () -> so.preduslov(validnaPoslovnica));
        assertEquals("Broj telefona poslovnice mora biti popunjen.", e.getMessage());
    }

    @Test
    void testPreduslov_ValidnaPoslovnica() {
        assertDoesNotThrow(() -> so.preduslov(validnaPoslovnica));
    }

    // ----- IZVRSI OPERACIJU -----

    @Test
    void testIzvrsiOperacijuPozivaDBBrokerIzmeni() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validnaPoslovnica);

            verify(dbMock, times(1)).izmeni(validnaPoslovnica);
        }
    }

    @Test
    void testIzvrsiOperacijuBacaExceptionKadaDBBrokerBaci() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);
        doThrow(new SQLException("Greška prilikom izmene u bazi."))
                .when(dbMock).izmeni(validnaPoslovnica);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validnaPoslovnica));
            assertEquals("Greška prilikom izmene u bazi.", e.getMessage());
        }
    }
}
