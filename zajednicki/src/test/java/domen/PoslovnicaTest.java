package domen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PoslovnicaTest {

    private Poslovnica poslovnica;

    @BeforeEach
    void setUp() {
        poslovnica = new Poslovnica(1, "Bulevar Oslobodjenja 10", "0111234567");
    }

    // ----- KONSTRUKTORI -----
    
    @Test
    void testKonstruktor_ValidneVrednosti() {
        Poslovnica p = new Poslovnica(1, "Bulevar Kralja Aleksandra 10", "0111234567");

        assertEquals(1, p.getIdPoslovnica());
        assertEquals("Bulevar Kralja Aleksandra 10", p.getAdresa());
        assertEquals("0111234567", p.getBrojTelefona());
    }
    
    // ----- SETTERI -----
    
    // setIdPoslovnica
    
    @Test
    void testSetIdPoslovnica_Valid() {
        poslovnica.setIdPoslovnica(10);
        assertEquals(10, poslovnica.getIdPoslovnica());
    }

    // setAdresa
    
    @Test
    void testSetAdresa_Valid() {
        poslovnica.setAdresa("Nova Adresa 10");
        assertEquals("Nova Adresa 10", poslovnica.getAdresa());
    }

    // setBrojTelefona
    
    @Test
    void testSetBrojTelefona_Valid() {
        poslovnica.setBrojTelefona("0659876543");
        assertEquals("0659876543", poslovnica.getBrojTelefona());
    }
    
    // ----- toString -----
    
    @Test
    void testToString() {
        assertEquals("Bulevar Oslobodjenja 10", poslovnica.toString());
    }
    
    // ----- SQL METODE -----
    
    @Test
    void testGetVrednostiZaInsert() {
        assertEquals("'Bulevar Oslobodjenja 10', '0111234567'", poslovnica.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        assertEquals("adresa = 'Bulevar Oslobodjenja 10', brojTelefona = '0111234567'", poslovnica.getVrednostiZaUpdate());
    }
    
    @ParameterizedTest
    @CsvSource({
        "'Bulevar Oslobodjenja 10', '', 'p.adresa LIKE ''%Bulevar Oslobodjenja 10%'''",
        "'', '0111234567', 'p.brojTelefona LIKE ''%0111234567%'''",
        "'Bulevar Oslobodjenja 10', '0111234567', 'p.adresa LIKE ''%Bulevar Oslobodjenja 10%'' AND p.brojTelefona LIKE ''%0111234567%'''"
    })
    void testGetUslovZaPretragu_Parametrizovano(String adresa, String brojTelefona, String expected) {
        poslovnica.setAdresa(adresa);
        poslovnica.setBrojTelefona(brojTelefona);
        assertEquals(expected, poslovnica.getUslovZaPretragu());
    }
    
    // ----- RESULT SET -----
    
    @Test
    void testKonvertujResultSetUListuDvaReda() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idPoslovnica")).thenReturn(1, 2);
        Mockito.when(rs.getString("adresa")).thenReturn("Adresa 1", "Adresa 2");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("0111234567", "0659876543");

        ArrayList<OpstiDomenskiObjekat> lista = new Poslovnica().konvertujResultSetUListu(rs);

        assertEquals(2, lista.size());
    }

    @Test
    void testKonvertujResultSetUListuJedanRed() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idPoslovnica")).thenReturn(5);
        Mockito.when(rs.getString("adresa")).thenReturn("Glavna 10");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("0111111111");

        ArrayList<OpstiDomenskiObjekat> lista = new Poslovnica().konvertujResultSetUListu(rs);

        assertEquals(1, lista.size());
    }

    @Test
    void testKonvertujResultSetUListuPrazan() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);

        ArrayList<OpstiDomenskiObjekat> lista = new Poslovnica().konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    void testKonvertujResultSetUListu_SQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greska u ResultSet-u"));
        
        assertThrows(SQLException.class, () -> {
            poslovnica.konvertujResultSetUListu(rs);
        });
    }
    
    @Test
    void testKonvertujResultSetUObjekat_BacaUnsupportedOperationException() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        assertThrows(UnsupportedOperationException.class, () -> poslovnica.konvertujResultSetUObjekat(rs));
    }
    
    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele() {
        assertEquals("poslovnica", poslovnica.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertEquals("p", poslovnica.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals("", poslovnica.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("adresa, brojTelefona", poslovnica.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        assertEquals("p.idPoslovnica = 1", poslovnica.requeredUslov());
    }

    @Test
    void testGetUslov() {
        assertEquals("*", poslovnica.getUslov());
    }

    
}
