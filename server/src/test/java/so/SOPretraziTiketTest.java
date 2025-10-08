/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Tiket;
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
public class SOPretraziTiketTest {
    
    private SOPretraziTiket so;
    private Tiket validanTiket;

    @BeforeEach
    void setUp() {
        so = new SOPretraziTiket();

        validanTiket = new Tiket();
        validanTiket.setIdTiket(1);
        validanTiket.setUkupnaKvota(5.5);
        validanTiket.setMoguciDobitak(1000);
    }
    
    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovValidanTiket() {
        assertDoesNotThrow(() -> so.preduslov(validanTiket));
    }

    @Test
    void testPreduslovNullParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Neispravan ID tiketa za pretragu.", e.getMessage());
    }

    @Test
    void testPreduslovNevalidanId() {
        validanTiket.setIdTiket(0);
        Exception e = assertThrows(Exception.class, () -> so.preduslov(validanTiket));
        assertEquals("Neispravan ID tiketa za pretragu.", e.getMessage());
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperaciju_TiketPostoji() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.vratiJednog(any(Tiket.class))).thenReturn(validanTiket);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validanTiket);

            verify(dbMock, times(1)).vratiJednog(validanTiket);
            assertEquals(validanTiket, so.getRezultat());
        }
    }
    
    @Test
    void testIzvrsiOperacijuTiketNePostoji() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.vratiJednog(any(Tiket.class))).thenReturn(null);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            Exception e = assertThrows(Exception.class, () -> so.izvrsiOperaciju(validanTiket));
            assertEquals("Tiket sa ID 1 ne postoji.", e.getMessage());
        }
    }
    
    @Test
    void testIzvrsiOperaciju_BacaSQLException() throws SQLException {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.doThrow(new SQLException("Greška u bazi")).when(dbMock).vratiJednog(any(Tiket.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanTiket));
            assertEquals("Greška u bazi", e.getMessage());
        }
    }
    
    
}
