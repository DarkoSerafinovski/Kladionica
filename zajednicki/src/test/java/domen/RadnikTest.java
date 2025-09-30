package domen;

import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test klasa za domenski objekat Radnik.
 * Testira konstruktore, gettere/settere, SQL metode i logiku pretrage.
 * Takođe pokriva metode za konverziju ResultSet-a u objekat i listu objekata.
 * 
 * @author Darko
 * @version 1.0
 */
public class RadnikTest {

    private Radnik radnik;

    /**
     * Priprema test objekat Radnik pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        radnik = new Radnik();
    }

    /**
     * Oslobađa resurse nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        radnik = null;
    }

    //
    // Testiranje osnovne funkcionalnosti
    //

    /**
     * Testira konstruktor sa parametrima.
     */
    @Test
    void testKonstruktor() {
        Radnik r = new Radnik(1, "Marko", "Markovic", "marko.m", "lozinka123", "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }

    /**
     * Testira settere i gettere.
     */
    @Test
    void testSetteri() {
        radnik.setIdRadnik(1);
        radnik.setIme("TestIme");
        radnik.setPrezime("TestPrezime");
        radnik.setKorisnickoIme("test.korisnik");
        radnik.setLozinka("test.lozinka");
        radnik.setBrojTelefona("987654321");
        
        assertEquals(1, radnik.getIdRadnik());
        assertEquals("TestIme", radnik.getIme());
        assertEquals("TestPrezime", radnik.getPrezime());
        assertEquals("test.korisnik", radnik.getKorisnickoIme());
        assertEquals("test.lozinka", radnik.getLozinka());
        assertEquals("987654321", radnik.getBrojTelefona());
    }

    /**
     * Testira toString metodu.
     */
    @Test
    void testToString() {
        radnik.setIme("Pera");
        radnik.setPrezime("Peric");
        assertEquals("Pera Peric", radnik.toString());
    }
    
    //
    // Testiranje implementacije OpstiDomenskiObjekat
    //

    /**
     * Testira getNazivTabele metodu.
     */
    @Test
    void testGetNazivTabele() {
        assertEquals("radnik", radnik.getNazivTabele());
    }

    /**
     * Testira alies metodu.
     */
    @Test
    void testAlies() {
        assertEquals("r", radnik.alies());
    }

    /**
     * Testira getJoinUslov metodu.
     */
    @Test
    void testGetJoinUslov() {
        assertEquals("", radnik.getJoinUslov());
    }

    /**
     * Testira getKoloneZaInsert metodu.
     */
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ime, prezime, korisnickoIme, lozinka, brojTelefona", radnik.getKoloneZaInsert());
    }

    /**
     * Testira getVrednostiZaInsert metodu.
     */
    @Test
    void testGetVrednostiZaInsert() {
        radnik.setIme("TestIme");
        radnik.setPrezime("TestPrezime");
        radnik.setKorisnickoIme("test.korisnik");
        radnik.setLozinka("test.lozinka");
        radnik.setBrojTelefona("123456789");
        String ocekivano = "'TestIme', 'TestPrezime', 'test.korisnik', 'test.lozinka', '123456789'";
        assertEquals(ocekivano, radnik.getVrednostiZaInsert());
    }

    /**
     * Testira getVrednostiZaUpdate metodu.
     */
    @Test
    void testGetVrednostiZaUpdate() {
        radnik.setIme("NovoIme");
        radnik.setPrezime("NovoPrezime");
        radnik.setBrojTelefona("999999999");
        String ocekivano = "ime = 'NovoIme', prezime = 'NovoPrezime', brojTelefona = '999999999'";
        assertEquals(ocekivano, radnik.getVrednostiZaUpdate());
    }

    /**
     * Testira requeredUslov metodu.
     */
    @Test
    void testRequeredUslov() {
        radnik.setIdRadnik(10);
        assertEquals("r.idRadnik = 10", radnik.requeredUslov());
    }
    
    /**
     * Testira getUslovZaPretragu metodu sa parametrizovanim podacima.
     * @param korIme korisničko ime za pretragu
     * @param lozinka lozinka za pretragu
     * @param ime ime radnika za pretragu
     * @param prezime prezime radnika za pretragu
     * @param ocekivaniUslov očekivani SQL uslov
     */
    @ParameterizedTest
    @CsvSource({
        "marko.m, lozinka123, null, null, r.korisnickoIme = 'marko.m' AND r.lozinka = 'lozinka123'",
        "null, null, Petar, null, r.ime LIKE '%Petar%'",
        "null, null, null, Peric, r.prezime LIKE '%Peric%'",
        "test, null, null, null, ''",
        "null, test, null, null, ''"
    })
    void testGetUslovZaPretragu(String korIme, String lozinka, String ime, String prezime, String ocekivaniUslov) {
        radnik.setKorisnickoIme("null".equals(korIme) ? null : korIme);
        radnik.setLozinka("null".equals(lozinka) ? null : lozinka);
        radnik.setIme("null".equals(ime) ? null : ime);
        radnik.setPrezime("null".equals(prezime) ? null : prezime);

        assertEquals(ocekivaniUslov, radnik.getUslovZaPretragu());
    }
    
    /**
     * Testira getUslov metodu.
     */
    @Test
    void testGetUslov() {
        assertEquals("*", radnik.getUslov());
    }

    //
    // Testiranje konverzije iz ResultSet
    //

    /**
     * Testira konverziju ResultSet-a u listu Radnik objekata.
     * @throws SQLException ukoliko ResultSet baca izuzetak
     */
    @Test
    void testKonvertujResultSetUListu() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(1, 2);
        Mockito.when(rs.getString("ime")).thenReturn("Marko", "Petar");
        Mockito.when(rs.getString("prezime")).thenReturn("Markovic", "Petrovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m", "petar.p");
        Mockito.when(rs.getString("lozinka")).thenReturn("loz1", "loz2");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("060-1", "060-2");

        List<OpstiDomenskiObjekat> lista = radnik.konvertujResultSetUListu(rs);
        
        assertEquals(2, lista.size());
        
        Radnik r1 = (Radnik) lista.get(0);
        assertEquals(1, r1.getIdRadnik());
        assertEquals("Marko", r1.getIme());
        
        Radnik r2 = (Radnik) lista.get(1);
        assertEquals(2, r2.getIdRadnik());
        assertEquals("Petar", r2.getIme());
    }

    /**
     * Testira konverziju ResultSet-a u pojedinačan Radnik objekat.
     * @throws SQLException ukoliko ResultSet baca izuzetak
     */
    @Test
    void testKonvertujResultSetUObjekat() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(1);
        Mockito.when(rs.getString("ime")).thenReturn("Marko");
        Mockito.when(rs.getString("prezime")).thenReturn("Markovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m");
        Mockito.when(rs.getString("lozinka")).thenReturn("loz1");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("060-123");

        Radnik rezultat = (Radnik) radnik.konvertujResultSetUObjekat(rs);
        
        assertNotNull(rezultat);
        assertEquals(1, rezultat.getIdRadnik());
        assertEquals("Marko", rezultat.getIme());
    }
}
