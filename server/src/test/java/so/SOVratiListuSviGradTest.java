/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import baza.DBBroker;
import domen.Grad;
import domen.OpstiDomenskiObjekat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

/**
 *
 * @author Darko
 */
public class SOVratiListuSviGradTest {
    
    private SOVratiListuSviGrad so;

    @BeforeEach
    void setUp() {
        so = new SOVratiListuSviGrad();
    }
    
    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        List<OpstiDomenskiObjekat> gradovi = new ArrayList<>();
        gradovi.add(new Grad(1, "Beograd", "11000"));
        gradovi.add(new Grad(2, "Novi Sad", "21000"));
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Grad.class))).thenReturn(gradovi);

            so.izvrsiOperaciju(null);
            
            verify(dbMock, times(1)).pretrazi(any(Grad.class));
            assertEquals(2, so.getLista().size());
            assertEquals("Beograd", ((Grad) so.getLista().get(0)).getNaziv());
        }
    }
    
    @Test
    void testIzvrsiOperacijuNeuspesno() throws SQLException {
        DBBroker dbMock = mock(DBBroker.class);

        try (MockedStatic<DBBroker> dbMockStatic = mockStatic(DBBroker.class)) {
            dbMockStatic.when(() -> DBBroker.getInstance()).thenReturn(dbMock);
            when(dbMock.pretrazi(any(Grad.class))).thenThrow(new SQLException("Greska u bazi"));

            assertThrows(SQLException.class, () -> {
                so.izvrsiOperaciju(new Grad());
            });
        }
    }

}
