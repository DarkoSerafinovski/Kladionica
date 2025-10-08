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
public class SOKreirajTiketTest {
    
    private SOKreirajTiket so;
    private Tiket validanTiket;

    @BeforeEach
    void setUp() {
        so = new SOKreirajTiket();
        validanTiket = new Tiket();
    }
    
    @Test
    void testIzvrsiOperacijuPostavljaIdTiket() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.kreiraj(any(Tiket.class))).thenReturn(5); 

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validanTiket);

            verify(dbMock, times(1)).kreiraj(validanTiket);

            assertEquals(5, validanTiket.getIdTiket());
            assertEquals(so.getTiket(), validanTiket);

            assertEquals(0.0, validanTiket.getUlog());
            assertEquals(0, validanTiket.getStatus());
            assertEquals(0, validanTiket.getMoguciDobitak());
            assertEquals(1, validanTiket.getUkupnaKvota());
        }
    }
    
    @Test
    void testIzvrsiOperacijuBacaSQLException() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.doThrow(new SQLException("Greska u kreiranju tiketa"))
                .when(dbMock).kreiraj(any(Tiket.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanTiket));
            assertEquals("Greska u kreiranju tiketa", e.getMessage());
        }
    }
}
