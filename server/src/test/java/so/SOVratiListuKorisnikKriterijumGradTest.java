/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
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
public class SOVratiListuKorisnikKriterijumGradTest {
    
    private SOVratiListuKorisnikKriterijumGrad so;
    
    @BeforeEach
    public void setUp() {
        so = new SOVratiListuKorisnikKriterijumGrad();
    }
    
    @Test
    public void testPreduslovUspesan() {
        Grad g = new Grad();
        assertDoesNotThrow(() -> so.preduslov(g));
    }

    @Test
    public void testPreduslovNevalidanObjekat() {
        Korisnik k = new Korisnik();
        Exception e = assertThrows(Exception.class, () -> so.preduslov(k));
        assertEquals("Parametar mora biti instanca grada.", e.getMessage());
    }

    @Test
    public void testPreduslovNull() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov(null));
        assertEquals("Parametar mora biti instanca grada.", e.getMessage());
    }
    
    @Test
    public void testIzvrsiOperacijuUspesno() throws Exception {
        Grad grad = new Grad();
        Korisnik k = new Korisnik();
        List<OpstiDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(k);

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(any(Korisnik.class))).thenReturn(ocekivanaLista);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(grad);

            assertEquals(ocekivanaLista, so.getLista());
            verify(dbMock, times(1)).pretrazi(any(Korisnik.class));
        }
    }

    @Test
    public void testIzvrsiOperacijuPraznaLista() throws Exception {
        Grad grad = new Grad();
        List<OpstiDomenskiObjekat> praznaLista = new ArrayList<>();

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(any(Korisnik.class))).thenReturn(praznaLista);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(grad);

            assertTrue(so.getLista().isEmpty());
            verify(dbMock, times(1)).pretrazi(any(Korisnik.class));
        }
    }

    @Test
    public void testIzvrsiOperacijuBacaIzuzetak() throws SQLException {
        Grad grad = new Grad();

        DBBroker dbMock = mock(DBBroker.class);
        when(dbMock.pretrazi(any(Korisnik.class))).thenThrow(new SQLException("Greška u bazi!"));

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(grad));
            assertEquals("Greška u bazi!", e.getMessage());
        }
    }
}
