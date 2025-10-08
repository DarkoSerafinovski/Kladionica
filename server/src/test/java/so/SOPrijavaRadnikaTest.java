/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Radnik;
import java.sql.SQLException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOPrijavaRadnikaTest {
    
    private SOPrijavaRadnika so;
    private Radnik validanRadnik;

    @BeforeEach
    void setUp() {
        so = new SOPrijavaRadnika();
        validanRadnik = new Radnik();
        validanRadnik.setKorisnickoIme("darko.s");
        validanRadnik.setLozinka("1234");
    }
    
    
    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovNevalidanObjekat() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(new Object()));
        assertEquals("Nevalidan objekat za prijavu!", e.getMessage());
    }

    @Test
    void testPreduslovNullKorisnickoIme() {
        validanRadnik.setKorisnickoIme(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanRadnik));
        assertEquals("Korisničko ime nije uneto!", e.getMessage());
    }

    @Test
    void testPreduslovPraznoKorisnickoIme() {
        validanRadnik.setKorisnickoIme("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanRadnik));
        assertEquals("Korisničko ime nije uneto!", e.getMessage());
    }

    @Test
    void testPreduslovNullLozinka() {
        validanRadnik.setLozinka(null);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanRadnik));
        assertEquals("Lozinka nije uneta!", e.getMessage());
    }

    @Test
    void testPreduslovPraznaLozinka() {
        validanRadnik.setLozinka("");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanRadnik));
        assertEquals("Lozinka nije uneta!", e.getMessage());
    }

    @Test
    void testPreduslovKratkaLozinka() {
        validanRadnik.setLozinka("abc");
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanRadnik));
        assertEquals("Lozinka mora imati bar 4 karaktera!", e.getMessage());
    }

    @Test
    void testPreduslovValidan() {
        assertDoesNotThrow(() -> so.preduslov(validanRadnik));
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperacijuPozivaDBBrokerIVracaRadnika() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);
        Radnik ocekivani = new Radnik();
        ocekivani.setKorisnickoIme("darko.s");
        ocekivani.setLozinka("1234");

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.vratiJednog(validanRadnik)).thenReturn(ocekivani);

            so.izvrsiOperaciju(validanRadnik);

            verify(dbMock, times(1)).vratiJednog(validanRadnik);
            assertEquals(ocekivani, so.getRadnik());
        }
    }

    @Test
    void testIzvrsiOperacijuBacaSQLException() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);

        doThrow(new SQLException("Greška u bazi")).when(dbMock).vratiJednog(any(Radnik.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanRadnik));
            assertEquals("Greška u bazi", e.getMessage());
        }
    }
}
