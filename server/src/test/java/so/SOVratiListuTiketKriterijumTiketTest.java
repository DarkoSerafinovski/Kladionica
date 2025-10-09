/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

/**
 *
 * @author Darko
 */
public class SOVratiListuTiketKriterijumTiketTest {
    
    private SOVratiListuTiketKriterijumTiket so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuTiketKriterijumTiket();
    }

    // ----- PREDUSLOV -----
    
    @Test
    void testPreduslovNeispravanParametar() {
        Exception e = assertThrows(Exception.class, () -> so.preduslov("String"));
        assertEquals("Parametar mora biti instanca tiket.", e.getMessage());
    }
    
    // ----- IZVRSI OPERACIJU -----
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        Tiket t = new Tiket();
        List<OpstiDomenskiObjekat> tiketi = new ArrayList<>();
        tiketi.add(new Tiket());
        tiketi.add(new Tiket());
        tiketi.add(new Tiket());

        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Tiket.class))).thenReturn(tiketi);

            so.izvrsiOperaciju(t);

            assertEquals(3, so.getLista().size());
            verify(dbMock, times(1)).pretrazi(any(Tiket.class));
        }
    }

    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        Tiket t = new Tiket();
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Tiket.class))).thenThrow(new SQLException("Greska u bazi"));

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(t));
            assertEquals("Greska u bazi", e.getMessage());
            
        }
    }
}
