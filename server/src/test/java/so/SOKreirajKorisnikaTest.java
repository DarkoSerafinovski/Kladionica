/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.Korisnik;
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
public class SOKreirajKorisnikaTest {
    
    private SOKreirajKorisnika so;
    private Korisnik validanKorisnik;
    private Grad grad;
    
    @BeforeEach
            
    void setUp() {
        so = new SOKreirajKorisnika();
    }
    
     @Test
    void testIzvrsiOperacijuPostavljaIdKorisnika() throws Exception {
        DBBroker dbMock = Mockito.mock(DBBroker.class);
        Mockito.when(dbMock.kreiraj(any(Korisnik.class))).thenReturn(10); 

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            so.izvrsiOperaciju(validanKorisnik);

            verify(dbMock, times(1)).kreiraj(any(Korisnik.class));
            assertEquals(10, so.getKorisnik().getIdKorisnik());
        }
    }
    
    @Test
    void testIzvrsiOperacijuBacaException() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);
        doThrow(new SQLException("Greška u kreiranju korisnika"))
                .when(dbMock).kreiraj(any(Korisnik.class));

        try (MockedStatic<DBBroker> dbStatic = mockStatic(DBBroker.class)) {
            dbStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);

            SQLException e = assertThrows(SQLException.class, () -> so.izvrsiOperaciju(validanKorisnik));
            assertEquals("Greška u kreiranju korisnika", e.getMessage());
        }
    }

}
