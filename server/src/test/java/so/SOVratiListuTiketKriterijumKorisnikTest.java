/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.Tiket;
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
public class SOVratiListuTiketKriterijumKorisnikTest {
    
    private SOVratiListuTiketKriterijumKorisnik so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuTiketKriterijumKorisnik();
    }

    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovNeispravanParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov("String"));
        assertEquals("Parametar mora biti instanca korisnik.", e.getMessage());
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        Korisnik k = new Korisnik();
        List<OpstiDomenskiObjekat> tiketi = new ArrayList<>();
        tiketi.add(new Tiket());
        tiketi.add(new Tiket());

        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Tiket.class))).thenReturn(tiketi);

            so.izvrsiOperaciju(k);

            assertEquals(2, so.getLista().size());
            verify(dbMock, times(1)).pretrazi(any(Tiket.class));
        }
    }

    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        Korisnik k = new Korisnik();
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Tiket.class))).thenThrow(new SQLException("Greska u bazi"));

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(k));
            assertEquals("Greska u bazi", e.getMessage());
            
        }
    }
}
