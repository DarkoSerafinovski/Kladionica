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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOVratiListuSviKorisnikTest {
    
    private SOVratiListuSviKorisnik so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuSviKorisnik();
    }
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        List<OpstiDomenskiObjekat> korisnici = new ArrayList<>();
        korisnici.add(new Korisnik(1, "Darko", "Serafinovski", "2010001860014", new Grad(1, "Pancevo", "26000")));
        korisnici.add(new Korisnik(2, "Petar", "Petrovic", "1234567890123", new Grad(2, "Beograd", "11000")));
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Korisnik.class))).thenReturn(korisnici);

            so.izvrsiOperaciju(null);
            
            verify(dbMock, times(1)).pretrazi(any(Korisnik.class));
            assertEquals(2, so.getLista().size());
            assertEquals("Darko", ((Korisnik) so.getLista().get(0)).getIme());
        }
    }
    
    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Korisnik.class))).thenThrow(new SQLException("Greska u bazi"));

            assertThrows(SQLException.class, () -> {
                so.izvrsiOperaciju(new Korisnik());
            });
        }
    }
}
