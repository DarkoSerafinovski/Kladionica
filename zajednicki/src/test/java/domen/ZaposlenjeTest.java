package domen;

import domen.OpstiDomenskiObjekat;
import domen.Zaposlenje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ZaposlenjeTest {

    private Zaposlenje zaposlenje;
    private Date datumZaposlenja;
    private Date datumOtpustanja;

    @BeforeEach
    void setUp() {
        zaposlenje = new Zaposlenje();
        datumZaposlenja = new Date();
        datumOtpustanja = new Date(datumZaposlenja.getTime() + 86400000); // Datum sutra
    }

    @AfterEach
    void tearDown() {
        zaposlenje = null;
        datumZaposlenja = null;
        datumOtpustanja = null;
    }

    // ----- KONSTRUKTORI -----

    // -----  SETTERI -----

    // ----- SQL METODE -----
    
    @Test
    void testGetVrednostiZaInsert() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getVrednostiZaUpdate());
    }
    
    @Test
    void testGetUslovZaPretragu() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getUslovZaPretragu());
    }
    
    // ----- RESULT SET METODE -----

    @Test
    void testKonvertujResultSetUListu() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.konvertujResultSetUListu(null));
    }

    @Test
    void testKonvertujResultSetUObjekat() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.konvertujResultSetUObjekat(null));
    }
    
    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.requeredUslov());
    }

    @Test
    void testGetUslov() {
        assertThrows(UnsupportedOperationException.class, () -> zaposlenje.getUslov());
    }
}
