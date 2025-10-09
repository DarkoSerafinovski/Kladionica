/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.OpstiDomenskiObjekat;
import domen.Utakmica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOVratiListuSviUtakmicaTest {
    
    private SOVratiListuSviUtakmica so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuSviUtakmica();
    }
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        List<OpstiDomenskiObjekat> utakmice = new ArrayList<>();
        utakmice.add(new Utakmica(1, "Barselona", "Real Madrid", new Date()));
        utakmice.add(new Utakmica(2, "Atletiko Madrid", "Viljareal", new Date()));
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Utakmica.class))).thenReturn(utakmice);

            so.izvrsiOperaciju(null);
            
            verify(dbMock, times(1)).pretrazi(any(Utakmica.class));
            assertEquals(2, so.getLista().size());
            assertEquals("Barselona", ((Utakmica) so.getLista().get(0)).getDomacin());
        }
    }
    
    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Utakmica.class))).thenThrow(new SQLException("Greska u bazi"));

            assertThrows(SQLException.class, () -> {
                so.izvrsiOperaciju(new Utakmica());
            });
        }
    }
}
