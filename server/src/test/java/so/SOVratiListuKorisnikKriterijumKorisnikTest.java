/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOVratiListuKorisnikKriterijumKorisnikTest {
    
    private SOVratiListuKorisnikKriterijumKorisnik so;

    @BeforeEach
    public void setUp() {
        so = new SOVratiListuKorisnikKriterijumKorisnik();
    }

    // ----- PREDUSLOV -----

    @Test
    public void testPreduslovUspesan() {
        Korisnik k = new Korisnik();
        assertDoesNotThrow(() -> so.preduslov(k));
    }

    @Test
    public void testPreduslovNevalidanObjekat() {
        Object pogresanParametar = new Object();
        Exception e = assertThrows(Exception.class, () -> so.preduslov(pogresanParametar));
        assertEquals("Parametar mora biti instanca korisnik.", e.getMessage());
    }

    @Test
    public void testPreduslovNull() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Parametar mora biti instanca korisnik.", e.getMessage());
    }

    // ----- IZVRSI OPERACIJU -----

    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {
        Korisnik k = new Korisnik();
        List<OpstiDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(k);

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(k)).thenReturn(ocekivanaLista);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(k);

            assertEquals(ocekivanaLista, so.getLista());
            verify(dbMock, times(1)).pretrazi(k);
        }
    }

    @Test
    public void testIzvrsiOperacijuPraznaLista() throws Exception {
        Korisnik k = new Korisnik();
        List<OpstiDomenskiObjekat> praznaLista = new ArrayList<>();

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(k)).thenReturn(praznaLista);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(k);

            assertTrue(so.getLista().isEmpty());
            verify(dbMock, times(1)).pretrazi(k);
        }
    }

    @Test
    public void testIzvrsiOperacijuBacaIzuzetak() throws SQLException {
        Korisnik k = new Korisnik();

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(k)).thenThrow(new SQLException("Greška pri pretrazi korisnika!"));

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(k));
            assertEquals("Greška pri pretrazi korisnika!", e.getMessage());
        }
    }
}
