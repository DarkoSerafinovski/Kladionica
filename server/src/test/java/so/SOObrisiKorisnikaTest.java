/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;
import java.sql.SQLException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOObrisiKorisnikaTest {
    
    private SOObrisiKorisnika so;
    private Korisnik validanKorisnik;

    @BeforeEach
    void setUp() {
        so = new SOObrisiKorisnika();

        validanKorisnik = new Korisnik();
        validanKorisnik.setIdKorisnik(1);
        validanKorisnik.setIme("Petar");
        validanKorisnik.setPrezime("Petrović");
    }
    
    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovValidanKorisnik() {
         assertDoesNotThrow(() -> so.preduslov(validanKorisnik));
    }

    @Test
    void testPreduslovNullParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Niste prosledili validnog korisnika za brisanje.", e.getMessage());
    }

    @Test
    void testPreduslovPogresanTip() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov("Nije korisnik"));
        assertEquals("Niste prosledili validnog korisnika za brisanje.", e.getMessage());
    }

    @Test
    void testPreduslovNevalidanId() {
        validanKorisnik.setIdKorisnik(0);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Korisnik nema validan ID.", e.getMessage());
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperaciju_PozitivanScenario() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validanKorisnik);

            verify(dbMock, times(1)).obrisi(validanKorisnik);
        }
    }

    @Test
    void testIzvrsiOperaciju_BacaSQLException() throws SQLException {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.doThrow(new SQLException("Greška prilikom brisanja"))
                .when(dbMock).obrisi(any(Korisnik.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanKorisnik));
            assertEquals("Greška prilikom brisanja", e.getMessage());
        }
    }
}
