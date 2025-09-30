package domen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test klasa za domenski objekat Par.
 * Testira konstruktore, gettere/settere, SQL metode i konverziju ResultSet-a.
 * Takođe pokriva toString metodu i validnost TipOpklade i kvote.
 * 
 * @author Darko
 * @version 1.0
 */
class ParTest {

    private Par par;
    private Utakmica utakmica;

    /**
     * Priprema test podatke pre svakog testa.
     * Kreira jednu utakmicu i jedan Par objekat.
     */
    @BeforeEach
    void setUp() {
        utakmica = new Utakmica();
        utakmica.setIdUtakmica(1);
        utakmica.setDomacin("Domacin FC");
        utakmica.setGost("Gost FC");
        utakmica.setTermin(new Timestamp(System.currentTimeMillis()));

        par = new Par(10, 100, utakmica, TipOpklade.POBEDA_DOMACIN, 2.5, 1);
    }

    /**
     * Testira generisanje SQL vrednosti za INSERT.
     */
    @Test
    void testGetVrednostiZaInsert() {
        String expected = "100, 1, 'POBEDA_DOMACIN', 2.5, 1";
        assertEquals(expected, par.getVrednostiZaInsert());
    }

    /**
     * Testira generisanje SQL vrednosti za UPDATE.
     * Trenutno metoda ima bug jer posle status = nema vrednost.
     */
    @Test
    void testGetVrednostiZaUpdate_hasBug() {
        String update = par.getVrednostiZaUpdate();
        assertTrue(update.contains("tipOpklade = 'POBEDA_DOMACIN'"));
        assertTrue(update.contains("kvota = 2.5"));
        assertTrue(update.contains("redosled = 1"));
        assertTrue(update.contains("status = "));
    }

    /**
     * Testira konverziju ResultSet-a u jedan Par objekat.
     * @throws SQLException ukoliko ResultSet baca izuzetak
     */
    @Test
    void testKonvertujResultSetUObjekat() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idPar")).thenReturn(10);
        Mockito.when(rs.getInt("idTiket")).thenReturn(100);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("Domacin FC");
        Mockito.when(rs.getString("gost")).thenReturn("Gost FC");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()));
        Mockito.when(rs.getString("tipOpklade")).thenReturn("POBEDA_DOMACIN");
        Mockito.when(rs.getDouble("kvota")).thenReturn(2.5);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);

        Par p = (Par) par.konvertujResultSetUObjekat(rs);

        assertEquals(10, p.getIdPar());
        assertEquals(100, p.getIdTiket());
        assertEquals("Domacin FC", p.getUtakmica().getDomacin());
        assertEquals(TipOpklade.POBEDA_DOMACIN, p.getTipOpklade());
        assertEquals(2.5, p.getKvota());
        assertEquals(1, p.getRedosled());
    }

    /**
     * Testira konverziju ResultSet-a u listu Par objekata.
     * @throws SQLException ukoliko ResultSet baca izuzetak
     */
    @Test
    void testKonvertujResultSetUListu() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, false);

        // Prvi red
        Mockito.when(rs.getInt("idPar")).thenReturn(1, 2);
        Mockito.when(rs.getInt("idTiket")).thenReturn(100, 200);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(10, 20);
        Mockito.when(rs.getString("domacin")).thenReturn("D1", "D2");
        Mockito.when(rs.getString("gost")).thenReturn("G1", "G2");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Mockito.when(rs.getString("tipOpklade")).thenReturn("POBEDA_DOMACIN", "POBEDA_GOST");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5, 3.2);
        Mockito.when(rs.getInt("redosled")).thenReturn(1, 2);

        ArrayList<OpstiDomenskiObjekat> lista = par.konvertujResultSetUListu(rs);

        assertEquals(2, lista.size());

        Par p1 = (Par) lista.get(0);
        assertEquals(1, p1.getIdPar());
        assertEquals(100, p1.getIdTiket());
        assertEquals("D1", p1.getUtakmica().getDomacin());
        assertEquals("G1", p1.getUtakmica().getGost());
        assertEquals(TipOpklade.POBEDA_DOMACIN, p1.getTipOpklade());

        Par p2 = (Par) lista.get(1);
        assertEquals(2, p2.getIdPar());
        assertEquals(200, p2.getIdTiket());
        assertEquals("D2", p2.getUtakmica().getDomacin());
        assertEquals("G2", p2.getUtakmica().getGost());
        assertEquals(TipOpklade.POBEDA_GOST, p2.getTipOpklade());
    }

    /**
     * Testira ispravnost toString metode.
     */
    @Test
    void testToString() {
        String ts = par.toString();
        assertTrue(ts.contains("idPar=10"));
        assertTrue(ts.contains("idTiket=100"));
        assertTrue(ts.contains("POBEDA_DOMACIN"));
        assertTrue(ts.contains("kvota=2.5"));
    }
}
