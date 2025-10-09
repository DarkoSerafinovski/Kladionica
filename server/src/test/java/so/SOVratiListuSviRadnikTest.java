/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
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
public class SOVratiListuSviRadnikTest {
    
    private SOVratiListuSviRadnik so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuSviRadnik();
    }
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        List<OpstiDomenskiObjekat> radnici = new ArrayList<>();
        radnici.add(new Radnik(1, "Darko", "Serafinovski", "darko.s", "lozinka1", "069/111-111"));
        radnici.add(new Radnik(2, "Petar", "Petrovic", "petar.p", "lozinka2", "069/222-222"));
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Radnik.class))).thenReturn(radnici);

            so.izvrsiOperaciju(null);
            
            verify(dbMock, times(1)).pretrazi(any(Radnik.class));
            assertEquals(2, so.getLista().size());
            assertEquals("darko.s", ((Radnik) so.getLista().get(0)).getKorisnickoIme());
        }
    }
    
    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Radnik.class))).thenThrow(new SQLException("Greska u bazi"));

            assertThrows(SQLException.class, () -> {
                so.izvrsiOperaciju(new Radnik());
            });
        }
    }
}
