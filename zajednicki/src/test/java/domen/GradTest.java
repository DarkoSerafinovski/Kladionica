package domen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GradTest {

    private Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad();
    }

    // ---------- KONSTRUKTORI ----------
    @Test
    void testKonstruktor_BezParametara() {
        assertNotNull(grad, "Grad ne bi trebalo da bude null");
        assertEquals(0, grad.getIdGrad(), "ID grada po defaultu treba da bude 0");
        assertNull(grad.getNaziv(), "Naziv grada po defaultu treba da bude null");
        assertNull(grad.getPostanskiBroj(), "Postanski broj po defaultu treba da bude null");
    }

    @Test
    void testParametarskiKonstruktorIspravan() {
        Grad noviGrad = new Grad(1, "Beograd", "11000");
        assertEquals(1, noviGrad.getIdGrad());
        assertEquals("Beograd", noviGrad.getNaziv());
        assertEquals("11000", noviGrad.getPostanskiBroj());
    }
    
    @Test
    void testParametarskiKonstruktorBacaIzuzetakAkoJeIdNegativan() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Grad(-1, "Beograd", "11000");
        });
    }

    // ----------  SETTERI SA VALIDACIJOM ----------
    
    @Test
    void testSetIDValidan(){
        grad.setIdGrad(1);
        assertEquals(1, grad.getIdGrad());
    }
    
    @Test
    void testSetIDNegativan(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> grad.setIdGrad(-1));
        assertEquals("ID Grada ne sme da bude negativan.", ex.getMessage());
    }
    
    @Test
    void testSetNazivValidan() {
        grad.setNaziv("Novi Sad");
        assertEquals("Novi Sad", grad.getNaziv());
    }

    @Test
    void testSetPostanskiBrojValidan() {
        grad.setPostanskiBroj("21000");
        assertEquals("21000", grad.getPostanskiBroj());
    }

    
    // ---------- TO STRING ----------
    
    @Test
    void testToString() {
        grad.setNaziv("Beograd");
        assertEquals("Beograd", grad.toString());
    }

    // ---------- SQL METODE ----------
    @Test
    void testGetVrednostiZaInsert() {
        grad.setNaziv("Nis");
        grad.setPostanskiBroj("18000");
        assertEquals("'Nis', '18000'", grad.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        grad.setNaziv("Subotica");
        grad.setPostanskiBroj("24000");
        assertEquals("naziv = 'Subotica', postanskiBroj = '24000'", grad.getVrednostiZaUpdate());
    }
    
    @ParameterizedTest
    @CsvSource({
        "Novi Sad, g.naziv LIKE '%Novi Sad%'",
        "Be, g.naziv LIKE '%Be%'",
        "null, ''",
        "'', ''"
    })
    void testGetUslovZaPretragu(String naziv, String expected) {
        if ("null".equals(naziv)) {
            naziv = null;
        }
        grad.setNaziv(naziv);
        assertEquals(expected, grad.getUslovZaPretragu());
    }

    // ---------- RESULTSET METODE ----------
    @Test
    void testKonvertujResultSetUListuValidanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idGrad")).thenReturn(1, 2);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd", "Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000", "21000");

        ArrayList<OpstiDomenskiObjekat> lista = grad.konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Grad grad1 = (Grad) lista.get(0);
        assertEquals("Beograd", grad1.getNaziv());
        assertEquals("11000", grad1.getPostanskiBroj());
        Grad grad2 = (Grad) lista.get(1);
        assertEquals("Novi Sad", grad2.getNaziv());
        assertEquals("21000", grad2.getPostanskiBroj());
    }

    @Test
    void testKonvertujResultSetUListuJedanResultSet() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idGrad")).thenReturn(1);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");
        
        ArrayList<OpstiDomenskiObjekat> lista = grad.konvertujResultSetUListu(rs);
        
        assertNotNull(lista);
        assertEquals(1, lista.size());
        
        Grad grad = (Grad) lista.get(0);
        assertEquals("Beograd", grad.getNaziv());
        assertEquals("11000", grad.getPostanskiBroj());
        
    }
            
    @Test
    void testKonvertujResultSetUListu_PrazanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);
        ArrayList<OpstiDomenskiObjekat> lista = grad.konvertujResultSetUListu(rs);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
    
    @Test
    void testKonvertujResultSetUListu_BacaSQLException() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greska u ResultSet-u"));
        
        assertThrows(SQLException.class, () -> {
            grad.konvertujResultSetUListu(rs);
        });
    }

    @Test
    void testKonvertujResultSetUObjekat_BacaUnsupportedOperationException() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        assertThrows(UnsupportedOperationException.class, () -> grad.konvertujResultSetUObjekat(rs));
    }

    // ---------- OSTALO ----------
    @Test
    void testGetNazivTabele() {
        assertEquals("grad", grad.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertEquals("g", grad.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals("", grad.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("naziv, postanskiBroj", grad.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        grad.setIdGrad(15);
        assertEquals("g.gradID = 15", grad.requeredUslov());
    }

    @Test
    void testGetUslov() {
        assertEquals("*", grad.getUslov());
    }
}