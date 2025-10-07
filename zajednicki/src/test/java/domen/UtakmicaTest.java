package domen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.Mockito.*;

public class UtakmicaTest {

    private Utakmica utakmica;
    
    @BeforeEach
    void setUp(){
        utakmica = new Utakmica();
    }
    
    // ----- KONSTRUKTORI -----
    
    @Test
    public void testBezparametarskiKonstruktor() {
        assertNotNull(utakmica);
        assertEquals(0, utakmica.getIdUtakmica());
        assertNull(utakmica.getDomacin());
        assertNull(utakmica.getGost());
        assertNull(utakmica.getTermin());
    }

    @Test
    public void testParametarskiKonstruktorSveValidno() {
        Date datum = new Date();
        utakmica = new Utakmica(1, "Partizan", "Crvena Zvezda", datum);

        assertEquals(1, utakmica.getIdUtakmica());
        assertEquals("Partizan", utakmica.getDomacin());
        assertEquals("Crvena Zvezda", utakmica.getGost());
        assertEquals(datum, utakmica.getTermin());
    }

    @Test
    public void testParametarskiKonstruktorDomacinNull() {
        Date datum = new Date();
        utakmica = new Utakmica(2, null, "Crvena Zvezda", datum);

        assertEquals(2, utakmica.getIdUtakmica());
        assertNull(utakmica.getDomacin());
        assertEquals("Crvena Zvezda", utakmica.getGost());
        assertEquals(datum, utakmica.getTermin());
    }

    @Test
    public void testParametarskiKonstruktorGostNull() {
        Date datum = new Date();
        utakmica = new Utakmica(3, "Partizan", null, datum);

        assertEquals(3, utakmica.getIdUtakmica());
        assertEquals("Partizan", utakmica.getDomacin());
        assertNull(utakmica.getGost());
        assertEquals(datum, utakmica.getTermin());
    }

    @Test
    public void testParametarskiKonstruktorTerminNull() {
        utakmica = new Utakmica(4, "Partizan", "Crvena Zvezda", null);

        assertEquals(4, utakmica.getIdUtakmica());
        assertEquals("Partizan", utakmica.getDomacin());
        assertEquals("Crvena Zvezda", utakmica.getGost());
        assertNull(utakmica.getTermin());
    }
    
    // ----- SETTERI -----
    
    @Test
    public void testSetIdUtakmicaValid() {
        utakmica.setIdUtakmica(10);
        assertEquals(10, utakmica.getIdUtakmica());
    }

    @Test
    public void testSetIdUtakmicaNula() {
        utakmica.setIdUtakmica(0);
        assertEquals(0, utakmica.getIdUtakmica());
    }

    @Test
    public void testSetDomacinValid() {
        utakmica.setDomacin("Partizan");
        assertEquals("Partizan", utakmica.getDomacin());
    }

    @Test
    public void testSetDomacinNull() {
        utakmica.setDomacin(null);
        assertNull(utakmica.getDomacin());
    }

    @Test
    public void testSetGostValid() {
        utakmica.setGost("Crvena Zvezda");
        assertEquals("Crvena Zvezda", utakmica.getGost());
    }

    @Test
    public void testSetGostNull() {
        utakmica.setGost(null);
        assertNull(utakmica.getGost());
    }

    @Test
    public void testSetTerminValid() {
        Date datum = new Date();
        utakmica.setTermin(datum);
        assertEquals(datum, utakmica.getTermin());
    }

    @Test
    public void testSetTerminNull() {
        utakmica.setTermin(null);
        assertNull(utakmica.getTermin());
    }
    
    // ----- TO STRING -----
    
    @Test
    void testToString() {
        utakmica.setIdUtakmica(1);
        utakmica.setDomacin("Partizan");
        utakmica.setGost("Zvezda");
        utakmica.setTermin(new Date());
        
        assertEquals("Partizan-Zvezda", utakmica.toString());
    }

    // ----- SQL METODE -----
    
    @Test
    void testGetVrednostiZaInsert() {
        utakmica.setDomacin("Barselona");
        utakmica.setGost("Real Madrid");
        Date termin = new Date();
        utakmica.setTermin(termin);
        
        String expected = "'Barselona', 'Real Madrid', '" + new java.sql.Timestamp(termin.getTime()) + "'";
        assertEquals(expected, utakmica.getVrednostiZaInsert());
    }
    
    @Test
    public void testGetVrednostiZaInsertTerminNull() {
        utakmica.setDomacin("Bajern Minhen");
        utakmica.setGost("Borusija Dortmund");
        utakmica.setTermin(null);

        assertThrows(NullPointerException.class, () -> utakmica.getVrednostiZaInsert());
    }

     @Test
    public void testGetVrednostiZaUpdateValid() {
        Date datum = new Date(); 
        utakmica.setDomacin("Boka Juniors");
        utakmica.setGost("River Plate");
        utakmica.setTermin(datum);

        String expected = "domacin = 'Boka Juniors', gost = 'River Plate', termin = '" 
                          + new java.sql.Timestamp(datum.getTime()) + "'";
        assertEquals(expected, utakmica.getVrednostiZaUpdate());
    }

    @Test
    public void testGetVrednostiZaUpdateTerminNull() {
        utakmica.setDomacin("Inter");
        utakmica.setGost("Milan");
        utakmica.setTermin(null);

        assertThrows(NullPointerException.class, () -> utakmica.getVrednostiZaUpdate());
    }
    
    
    @ParameterizedTest
    @CsvSource({
        // domacin, gost, termin, expected
        "Partizan, Crvena Zvezda, 2025-10-07, u.domacin LIKE '%Partizan%' AND u.gost LIKE '%Crvena Zvezda%' AND u.termin = '2025-10-07'",
        "Partizan, , , u.domacin LIKE '%Partizan%'",
        ", Crvena Zvezda, , u.gost LIKE '%Crvena Zvezda%'",
        ", , 2025-10-07, u.termin = '2025-10-07'",
        ", , , ''"
    })
    void testGetUslovZaPretragu(String domacin, String gost, String terminStr, String expected) throws Exception {
        utakmica.setDomacin(domacin);
        utakmica.setGost(gost);

        if (terminStr == null) {
            utakmica.setTermin(null);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            utakmica.setTermin(sdf.parse(terminStr));
        }

        assertEquals(expected, utakmica.getUslovZaPretragu());
    }
    
    // ----- RESULT SET METODE -----
    
    @Test
    public void testKonvertujResultSetUListuTriReda() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, true, false);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1, 2, 3);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan", "Crvena Zvezda", "Radnicki");
        Mockito.when(rs.getString("gost")).thenReturn("Crvena Zvezda", "Partizan", "Napredak");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(
                Timestamp.valueOf("2025-10-07 15:00:00"),
                Timestamp.valueOf("2025-10-08 18:00:00"),
                Timestamp.valueOf("2025-10-09 20:00:00")
        );

        ArrayList<OpstiDomenskiObjekat> lista = utakmica.konvertujResultSetUListu(rs);

        assertEquals(3, lista.size());
    }

    @Test
    public void testKonvertujResultSetUListuJedanRed() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(5);
        Mockito.when(rs.getString("domacin")).thenReturn("Vojvodina");
        Mockito.when(rs.getString("gost")).thenReturn("Spartak");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(Timestamp.valueOf("2025-11-01 17:00:00"));

        ArrayList<OpstiDomenskiObjekat> lista = utakmica.konvertujResultSetUListu(rs);
        assertEquals(1, lista.size());
    }

    @Test
    public void testKonvertujResultSetUListuPrazanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(false);

        ArrayList<OpstiDomenskiObjekat> lista = utakmica.konvertujResultSetUListu(rs);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testKonvertujResultSetUListuSQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenThrow(new SQLException("Greška u čitanju"));

        assertThrows(SQLException.class, () -> utakmica.konvertujResultSetUListu(rs));
    }
    
    @Test
    public void testKonvertujResultSetUObjekatValid() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan");
        Mockito.when(rs.getString("gost")).thenReturn("Crvena Zvezda");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(Timestamp.valueOf("2025-10-07 15:00:00"));

        Utakmica u = (Utakmica) utakmica.konvertujResultSetUObjekat(rs);

        assertEquals(1, u.getIdUtakmica());
        assertEquals("Partizan", u.getDomacin());
        assertEquals("Crvena Zvezda", u.getGost());
        assertEquals(Timestamp.valueOf("2025-10-07 15:00:00"), u.getTermin());
    }

    @Test
    public void testKonvertujResultSetUObjekatSveNull() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(0);
        Mockito.when(rs.getString("domacin")).thenReturn(null);
        Mockito.when(rs.getString("gost")).thenReturn(null);
        Mockito.when(rs.getTimestamp("termin")).thenReturn(null);

        Utakmica u = (Utakmica) utakmica.konvertujResultSetUObjekat(rs);

        assertEquals(0, u.getIdUtakmica());
        assertNull(u.getDomacin());
        assertNull(u.getGost());
        assertNull(u.getTermin());
    }

    @Test
    public void testKonvertujResultSetUObjekatSQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idUtakmica")).thenThrow(new SQLException("Greška pri čitanju"));

        assertThrows(SQLException.class, () -> utakmica.konvertujResultSetUObjekat(rs));
    }
    
    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele() {
        assertEquals("utakmica", utakmica.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertEquals("u", utakmica.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals("", utakmica.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("domacin, gost, termin", utakmica.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        utakmica.setIdUtakmica(1);
        assertEquals("u.idUtakmica = 1", utakmica.requeredUslov());
    }

    @Test
    void testGetUslov() {
        assertEquals("*", utakmica.getUslov());
    }
}
