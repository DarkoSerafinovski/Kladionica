package domen;

import domen.OpstiDomenskiObjekat;
import domen.Zaposlenje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit testovi za klasu {@link Zaposlenje}.
 * Testira osnovnu funkcionalnost konstruktora, settere i gettere,
 * kao i da sve metode interfejsa {@link OpstiDomenskiObjekat} bacaju
 * {@link UnsupportedOperationException}.
 */
public class ZaposlenjeTest {

    private Zaposlenje zaposlenje;
    private Date datumZaposlenja;
    private Date datumOtpustanja;

    /**
     * Inicijalizacija objekata pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        zaposlenje = new Zaposlenje();
        datumZaposlenja = new Date();
        datumOtpustanja = new Date(datumZaposlenja.getTime() + 86400000); // Datum sutra
    }

    /**
     * Oslobađanje resursa nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        zaposlenje = null;
        datumZaposlenja = null;
        datumOtpustanja = null;
    }

    // =========================
    // TESTOVI OSNOVNE FUNKCIONALNOSTI
    // =========================

    /**
     * Testira default konstruktor.
     */
    @Test
    void testPrazanKonstruktor() {
        assertNotNull(zaposlenje);
    }

    /**
     * Testira konstruktor sa datumom zaposlenja.
     */
    @Test
    void testKonstruktor_DatumZaposlenja() {
        Zaposlenje z = new Zaposlenje(1, 1, datumZaposlenja);
        assertEquals(1, z.getIdRadnik());
        assertEquals(1, z.getIdPoslovnica());
        assertEquals(datumZaposlenja, z.getDatumZaposlenja());
        assertNull(z.getDatumOtpustanja());
    }

    /**
     * Testira konstruktor sa datumom zaposlenja i datumom otpuštanja.
     */
    @Test
    void testKonstruktor_SaSvima() {
        Zaposlenje z = new Zaposlenje(1, 1, datumZaposlenja, datumOtpustanja);
        assertEquals(1, z.getIdRadnik());
        assertEquals(1, z.getIdPoslovnica());
        assertEquals(datumZaposlenja, z.getDatumZaposlenja());
        assertEquals(datumOtpustanja, z.getDatumOtpustanja());
    }

    /**
     * Testira settere i gettere za sve atribute.
     */
    @Test
    void testSetteri() {
        zaposlenje.setIdRadnik(5);
        zaposlenje.setIdPoslovnica(10);
        zaposlenje.setDatumZaposlenja(datumZaposlenja);
        zaposlenje.setDatumOtpustanja(datumOtpustanja);

        assertEquals(5, zaposlenje.getIdRadnik());
        assertEquals(10, zaposlenje.getIdPoslovnica());
        assertEquals(datumZaposlenja, zaposlenje.getDatumZaposlenja());
        assertEquals(datumOtpustanja, zaposlenje.getDatumOtpustanja());
    }

    // =========================
    // TESTOVI METODA INTERFEJSA KOJE BACAJU IZUZETKE
    // =========================

    /**
     * Testira da {@link Zaposlenje#getNazivTabele()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetNazivTabele() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getNazivTabele());
    }

    /**
     * Testira da {@link Zaposlenje#alies()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testAlies() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.alies());
    }

    /**
     * Testira da {@link Zaposlenje#getJoinUslov()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetJoinUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getJoinUslov());
    }

    /**
     * Testira da {@link Zaposlenje#getKoloneZaInsert()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetKoloneZaInsert() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getKoloneZaInsert());
    }

    /**
     * Testira da {@link Zaposlenje#getVrednostiZaInsert()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetVrednostiZaInsert() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getVrednostiZaInsert());
    }

    /**
     * Testira da {@link Zaposlenje#getVrednostiZaUpdate()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetVrednostiZaUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getVrednostiZaUpdate());
    }

    /**
     * Testira da {@link Zaposlenje#requeredUslov()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testRequeredUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.requeredUslov());
    }

    /**
     * Testira da {@link Zaposlenje#getUslovZaPretragu()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetUslovZaPretragu() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getUslovZaPretragu());
    }

    /**
     * Testira da {@link Zaposlenje#getUslov()} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testGetUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getUslov());
    }

    /**
     * Testira da {@link Zaposlenje#konvertujResultSetUListu(java.sql.ResultSet)} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testKonvertujResultSetUListu() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.konvertujResultSetUListu(null));
    }

    /**
     * Testira da {@link Zaposlenje#konvertujResultSetUObjekat(java.sql.ResultSet)} baca {@link UnsupportedOperationException}.
     */
    @Test
    void testKonvertujResultSetUObjekat() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.konvertujResultSetUObjekat(null));
    }
}
