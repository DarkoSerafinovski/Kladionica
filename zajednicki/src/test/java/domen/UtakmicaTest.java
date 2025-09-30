package domen;

import domen.OpstiDomenskiObjekat;
import domen.Utakmica;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit testovi za klasu {@link Utakmica}.
 * Testira osnovnu funkcionalnost, settere, konstruktore,
 * metode {@link Object#toString()}, {@link Object#equals(Object)}, {@link Object#hashCode()},
 * kao i implementaciju {@link OpstiDomenskiObjekat}.
 */
public class UtakmicaTest {

    private Utakmica utakmica;

    /**
     * Inicijalizacija objekta pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        utakmica = new Utakmica();
    }

    /**
     * Oslobađanje resursa nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        utakmica = null;
    }

    // =========================
    // OSNOVNI TESTOVI
    // =========================

    /**
     * Testira da li default konstruktor pravilno kreira objekat.
     */
    @Test
    void testPrazanKonstruktor() {
        assertNotNull(utakmica);
    }

    /**
     * Testira pun konstruktor sa parametrima.
     */
    @Test
    void testKonstruktor() {
        Date termin = new Date();
        Utakmica u = new Utakmica(1, "Partizan", "Crvena Zvezda", termin);
        assertEquals(1, u.getIdUtakmica());
        assertEquals("Partizan", u.getDomacin());
        assertEquals("Crvena Zvezda", u.getGost());
        assertEquals(termin, u.getTermin());
    }

    /**
     * Testira seter i geter za ID utakmice.
     */
    @Test
    void testSetIdUtakmica() {
        utakmica.setIdUtakmica(5);
        assertEquals(5, utakmica.getIdUtakmica());
    }

    /**
     * Testira seter i geter za domacin.
     */
    @Test
    void testSetDomacin() {
        utakmica.setDomacin("TimA");
        assertEquals("TimA", utakmica.getDomacin());
    }

    /**
     * Testira seter i geter za gosta.
     */
    @Test
    void testSetGost() {
        utakmica.setGost("TimB");
        assertEquals("TimB", utakmica.getGost());
    }

    /**
     * Testira seter i geter za termin utakmice.
     */
    @Test
    void testSetTermin() {
        Date termin = new Date();
        utakmica.setTermin(termin);
        assertEquals(termin, utakmica.getTermin());
    }

    /**
     * Testira {@link Utakmica#toString()} metodu.
     */
    @Test
    void testToString() {
        utakmica.setDomacin("Arsenal");
        utakmica.setGost("Man. Utd.");
        assertEquals("Arsenal-Man. Utd.", utakmica.toString());
    }

    /**
     * Testira {@link Utakmica#equals(Object)} sa istim objektom.
     */
    @Test
    void testEquals_IstiObjekat() {
        assertTrue(utakmica.equals(utakmica));
    }

    /**
     * Testira {@link Utakmica#equals(Object)} sa null objektom.
     */
    @Test
    void testEquals_NullObjekat() {
        assertFalse(utakmica.equals(null));
    }

    /**
     * Testira {@link Utakmica#equals(Object)} sa objektom druge klase.
     */
    @Test
    void testEquals_RazlicitaKlasa() {
        assertFalse(utakmica.equals(new Object()));
    }

    /**
     * Testira {@link Utakmica#equals(Object)} sa istim ID.
     */
    @Test
    void testEquals_IstiId() {
        utakmica.setIdUtakmica(1);
        Utakmica drugaUtakmica = new Utakmica();
        drugaUtakmica.setIdUtakmica(1);
        assertTrue(utakmica.equals(drugaUtakmica));
    }

    /**
     * Testira {@link Utakmica#equals(Object)} sa različitim ID.
     */
    @Test
    void testEquals_RazlicitId() {
        utakmica.setIdUtakmica(1);
        Utakmica drugaUtakmica = new Utakmica();
        drugaUtakmica.setIdUtakmica(2);
        assertFalse(utakmica.equals(drugaUtakmica));
    }

    /**
     * Testira {@link Utakmica#hashCode()} sa istim ID.
     */
    @Test
    void testHashCode_IstiId() {
        utakmica.setIdUtakmica(1);
        Utakmica drugaUtakmica = new Utakmica();
        drugaUtakmica.setIdUtakmica(1);
        assertEquals(utakmica.hashCode(), drugaUtakmica.hashCode());
    }

    // =========================
    // TESTOVI ZA OPSTI DOMENSKI OBJEKAT
    // =========================

    /**
     * Testira naziv tabele za SQL upite.
     */
    @Test
    void testGetNazivTabele() {
        assertEquals("utakmica", utakmica.getNazivTabele());
    }

    /**
     * Testira alias tabele za SQL upite.
     */
    @Test
    void testAlies() {
        assertEquals("u", utakmica.alies());
    }

    /**
     * Testira JOIN uslove (prazan string jer nema join).
     */
    @Test
    void testGetJoinUslov() {
        assertEquals("", utakmica.getJoinUslov());
    }

    /**
     * Testira kolone za INSERT SQL upit.
     */
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("domacin, gost, termin", utakmica.getKoloneZaInsert());
    }

    /**
     * Testira vrednosti za INSERT SQL upit.
     */
    @Test
    void testGetVrednostiZaInsert() {
        utakmica.setDomacin("Tim A");
        utakmica.setGost("Tim B");
        Date termin = new Date(1672531200000L);
        utakmica.setTermin(termin);

        String ocekivanaVrednost = "'" + "Tim A" + "', '" + "Tim B" + "', '" + termin.toString() + "'";
        assertEquals(ocekivanaVrednost, utakmica.getVrednostiZaInsert());
    }

    /**
     * Testira vrednosti za UPDATE SQL upit.
     */
    @Test
    void testGetVrednostiZaUpdate() {
        utakmica.setDomacin("Tim A");
        utakmica.setGost("Tim B");
        Date termin = new Date(1672531200000L);
        utakmica.setTermin(termin);

        String ocekivanaVrednost = "domacin = '" + "Tim A" + "', gost = '" + "Tim B" + "', termin = '" + termin.toString() + "'";
        assertEquals(ocekivanaVrednost, utakmica.getVrednostiZaUpdate());
    }

    /**
     * Testira uslov po primarnom ključu.
     */
    @Test
    void testRequeredUslov() {
        utakmica.setIdUtakmica(1);
        assertEquals("u.utakmica = 1", utakmica.requeredUslov());
    }

    /**
     * Parametrizovan test za pretragu po domacinu i gostu.
     */
    @ParameterizedTest
    @CsvSource({
        "Partizan, null, null, u.domacin LIKE '%Partizan%'",
        "null, Crvena Zvezda, null, u.gost LIKE '%Crvena Zvezda%'",
        "Partizan, Crvena Zvezda, null, u.domacin LIKE '%Partizan%' AND u.gost LIKE '%Crvena Zvezda%'"
    })
    void testGetUslovZaPretragu(String domacinParam, String gostParam, String terminParam, String ocekivaniUslov) {
        if ("null".equals(domacinParam)) {
            utakmica.setDomacin(null);
        } else {
            utakmica.setDomacin(domacinParam);
        }

        if ("null".equals(gostParam)) {
            utakmica.setGost(null);
        } else {
            utakmica.setGost(gostParam);
        }

        if ("null".equals(terminParam)) {
            utakmica.setTermin(null);
        }

        assertEquals(ocekivaniUslov, utakmica.getUslovZaPretragu());
    }

    /**
     * Testira pretragu po terminu utakmice.
     */
    @Test
    void testGetUslovZaPretragu_Termin() {
        Date termin = new Date(1672531200000L);
        utakmica.setTermin(termin);
        String ocekivaniUslov = "u.termin = '" + new java.sql.Date(termin.getTime()) + "'";
        assertEquals(ocekivaniUslov, utakmica.getUslovZaPretragu());
    }

    /**
     * Testira getUslov() metodu koja vraća sve kolone sa aliasom.
     */
    @Test
    void testGetUslov() {
        assertEquals("*", utakmica.getUslov());
    }

    /**
     * Testira konverziju ResultSet-a u listu utakmica.
     */
    @Test
    void testKonvertujResultSetUListu() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1, 2);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan", "Crvena Zvezda");
        Mockito.when(rs.getString("gost")).thenReturn("Crvena Zvezda", "Partizan");
        Mockito.when(rs.getDate("termin")).thenReturn(new java.sql.Date(new Date().getTime()), new java.sql.Date(new Date().getTime()));

        ArrayList<OpstiDomenskiObjekat> lista = utakmica.konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Utakmica u1 = (Utakmica) lista.get(0);
        assertEquals(1, u1.getIdUtakmica());
        assertEquals("Partizan", u1.getDomacin());

        Utakmica u2 = (Utakmica) lista.get(1);
        assertEquals(2, u2.getIdUtakmica());
        assertEquals("Crvena Zvezda", u2.getDomacin());
    }

    /**
     * Testira konverziju ResultSet-a u jedan objekat utakmice.
     */
    @Test
    void testKonvertujResultSetUObjekat() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan");
        Mockito.when(rs.getString("gost")).thenReturn("Crvena Zvezda");
        Mockito.when(rs.getDate("termin")).thenReturn(new java.sql.Date(new Date().getTime()));

        Utakmica rezultat = (Utakmica) utakmica.konvertujResultSetUObjekat(rs);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.getIdUtakmica());
        assertEquals("Partizan", rezultat.getDomacin());
    }
}
