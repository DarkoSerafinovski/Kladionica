package domen;

import domen.OpstiDomenskiObjekat;
import domen.Poslovnica;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test klasa za domenski objekat Poslovnica.
 * Testira konstruktore, gettere/settere, SQL metode i logiku pretrage.
 * Takođe pokriva metode za konverziju ResultSet-a u listu objekata.
 * 
 * @author Darko
 * @version 1.0
 */
public class PoslovnicaTest {

    private Poslovnica poslovnica;

    /**
     * Priprema test objekat Poslovnica pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        poslovnica = new Poslovnica();
    }

    /**
     * Oslobađa resurse nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        poslovnica = null;
    }

    /**
     * Testira konstruktor sa parametrima.
     */
    @Test
    void testKonstruktor() {
        Poslovnica p = new Poslovnica(1, "Adresa 1", "060-1234567");
        assertEquals(1, p.getIdPoslovnica());
        assertEquals("Adresa 1", p.getAdresa());
        assertEquals("060-1234567", p.getBrojTelefona());
    }

    /**
     * Testira settere i gettere.
     */
    @Test
    void testSetteri() {
        poslovnica.setIdPoslovnica(1);
        poslovnica.setAdresa("Test Adresa");
        poslovnica.setBrojTelefona("123456789");
        assertEquals(1, poslovnica.getIdPoslovnica());
        assertEquals("Test Adresa", poslovnica.getAdresa());
        assertEquals("123456789", poslovnica.getBrojTelefona());
    }

    /**
     * Testira toString metodu.
     */
    @Test
    void testToString() {
        poslovnica.setAdresa("Adresa 1");
        assertEquals("Adresa 1", poslovnica.toString());
    }

    /**
     * Testira getNazivTabele metodu.
     */
    @Test
    void testGetNazivTabele() {
        assertEquals("poslovnica", poslovnica.getNazivTabele());
    }

    /**
     * Testira alies metodu.
     */
    @Test
    void testAlies() {
        assertEquals("p", poslovnica.alies());
    }

    /**
     * Testira getJoinUslov metodu.
     */
    @Test
    void testGetJoinUslov() {
        assertEquals("", poslovnica.getJoinUslov());
    }

    /**
     * Testira getKoloneZaInsert metodu.
     */
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("adresa, brojTelefona", poslovnica.getKoloneZaInsert());
    }

    /**
     * Testira getVrednostiZaInsert metodu.
     */
    @Test
    void testGetVrednostiZaInsert() {
        poslovnica.setAdresa("Adresa A");
        poslovnica.setBrojTelefona("060-111222");
        String ocekivano = "'Adresa A', '060-111222'";
        assertEquals(ocekivano, poslovnica.getVrednostiZaInsert());
    }

    /**
     * Testira getVrednostiZaUpdate metodu.
     */
    @Test
    void testGetVrednostiZaUpdate() {
        poslovnica.setAdresa("Adresa B");
        poslovnica.setBrojTelefona("060-333444");
        String ocekivano = "adresa = 'Adresa B', brojTelefona = '060-333444'";
        assertEquals(ocekivano, poslovnica.getVrednostiZaUpdate());
    }

    /**
     * Testira requeredUslov metodu.
     */
    @Test
    void testRequeredUslov() {
        poslovnica.setIdPoslovnica(5);
        assertEquals("p.idPoslovnica = 5", poslovnica.requeredUslov());
    }

    /**
     * Testira getUslovZaPretragu metodu sa parametrizovanim podacima.
     * @param adresaParam adresa poslovnice za pretragu
     * @param brTelefonaParam broj telefona za pretragu
     * @param ocekivaniUslov očekivani SQL uslov
     */
    @ParameterizedTest
    @CsvSource({
        "Adresa, null, p.adresa LIKE '%Adresa%'",
        "null, 1234567, p.brojTelefona LIKE '%1234567%'",
        "Adresa, 1234567, p.adresa LIKE '%Adresa%' AND p.brojTelefona LIKE '%1234567%'"
    })
    void testGetUslovZaPretragu(String adresaParam, String brTelefonaParam, String ocekivaniUslov) {
        poslovnica.setAdresa("null".equals(adresaParam) ? null : adresaParam);
        poslovnica.setBrojTelefona("null".equals(brTelefonaParam) ? null : brTelefonaParam);
        
        assertEquals(ocekivaniUslov, poslovnica.getUslovZaPretragu());
    }
    
    /**
     * Testira getUslov metodu.
     */
    @Test
    void testGetUslov() {
        assertEquals("*", poslovnica.getUslov());
    }

    /**
     * Testira konverziju ResultSet-a u listu Poslovnica objekata.
     * @throws SQLException ukoliko ResultSet baca izuzetak
     */
    @Test
    void testKonvertujResultSetUListu() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idPoslovnica")).thenReturn(1, 2);
        Mockito.when(rs.getString("adresa")).thenReturn("Adresa 1", "Adresa 2");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("060-1", "060-2");

        List<OpstiDomenskiObjekat> lista = poslovnica.konvertujResultSetUListu(rs);
        
        assertEquals(2, lista.size());
        
        Poslovnica p1 = (Poslovnica) lista.get(0);
        assertEquals(1, p1.getIdPoslovnica());
        assertEquals("Adresa 1", p1.getAdresa());
        
        Poslovnica p2 = (Poslovnica) lista.get(1);
        assertEquals(2, p2.getIdPoslovnica());
        assertEquals("Adresa 2", p2.getAdresa());
    }

    /**
     * Testira da li metoda konvertujResultSetUObjekat baca UnsupportedOperationException.
     */
    @Test
    void testKonvertujResultSetUObjekat() {
        assertThrows(UnsupportedOperationException.class, () -> {
            poslovnica.konvertujResultSetUObjekat(Mockito.mock(ResultSet.class));
        });
    }
}
