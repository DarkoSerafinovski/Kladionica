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
public class SOPretraziKorisnikTest {
    
    private SOPretraziKorisnik so;
    private Korisnik validanKorisnik;

    @BeforeEach
    void setUp() {
        so = new SOPretraziKorisnik();

        validanKorisnik = new Korisnik();
        validanKorisnik.setIdKorisnik(1);
        validanKorisnik.setIme("Petar");
        validanKorisnik.setPrezime("Petrovic");
    }
    
    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovValidanKorisnik() {
         assertDoesNotThrow(() -> so.preduslov(validanKorisnik));
    }

    @Test
    void testPreduslovNullParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Neispravan ID korisnika za pretragu.", e.getMessage());
    }

    @Test
    void testPreduslovNevalidanId() {
        validanKorisnik.setIdKorisnik(0);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanKorisnik));
        assertEquals("Neispravan ID korisnika za pretragu.", e.getMessage());
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperacijuKorisnikPostoji() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.vratiJednog(any(Korisnik.class))).thenReturn(validanKorisnik);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validanKorisnik);

            verify(dbMock, times(1)).vratiJednog(validanKorisnik);
            assertEquals(validanKorisnik, so.getRezultat());
        }
    }

    @Test
    void testIzvrsiOperacijuKorisnikNePostoji() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.vratiJednog(any(Korisnik.class))).thenReturn(null);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            Exception e = assertThrows(Exception.class, () -> so.izvrsiOperaciju(validanKorisnik));
            assertEquals("Korisnik sa ID 1 ne postoji.", e.getMessage());
        }
    }
    
    @Test
    void testIzvrsiOperaciju_BacaSQLException() throws SQLException {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.doThrow(new SQLException("Greska u bazi"))
                .when(dbMock).vratiJednog(any(Korisnik.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanKorisnik));
            assertEquals("Greska u bazi", e.getMessage());
        }
    }
}
