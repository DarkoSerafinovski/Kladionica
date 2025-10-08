/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Par;
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
public class SOIzmeniTiketTest {
    
    private SOIzmeniTiket so;
    private Tiket tiket;
    
    @BeforeEach
    void setUp() {
        so = new SOIzmeniTiket();

        tiket = new Tiket();
        tiket.setIdTiket(1);
        tiket.setUkupnaKvota(2.5);
        tiket.setUlog(100);
        tiket.setStatus(0);
    }
    
    @Test
    void testIzvrsiOperacijuBezParova() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            tiket.setParovi(new ArrayList<>()); 

            so.izvrsiOperaciju(tiket);

            verify(dbMock, never()).kreiraj(any());
            verify(dbMock, times(1)).izmeni(tiket);
        }
    }
    
    @Test
    void testIzvrsiOperacijuSaParovima() throws Exception {
        DBBroker dbMock = mock(DBBroker.class);

        Par p1 = new Par();
        Par p2 = new Par();
        List<Par> parovi = new ArrayList<>();
        parovi.add(p1);
        parovi.add(p2);

        tiket.setParovi(parovi);
        tiket.setStatus(1);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(tiket);

            verify(dbMock, times(2)).kreiraj(any(Par.class));
            verify(dbMock, times(1)).izmeni(tiket);

            double expectedDobitak = tiket.getUkupnaKvota() * tiket.getUlog();
            assertEquals(expectedDobitak, tiket.getMoguciDobitak(), 0.0001);
        }
    }
    
    @Test
    void testIzvrsiOperacijuDBThrow() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);
        doThrow(new SQLException("Greška prilikom izmene u bazi."))
                .when(dbMock).izmeni(tiket);

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(DBBroker::getInstance).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(tiket));
            assertEquals("Greška prilikom izmene u bazi.", e.getMessage());
        }
    }
}
