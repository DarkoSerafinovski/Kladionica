package domen;

import domen.Grad;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Detaljna test klasa za domenski objekat {@link Korisnik}.
 * <p>
 * Testira:
 * <ul>
 *   <li>Konstruktore (prazan i sa parametrima)</li>
 *   <li>Gettere i settere</li>
 *   <li>Metode iz interfejsa {@link OpstiDomenskiObjekat}</li>
 *   <li>Konverziju iz {@link ResultSet}-a u objekte</li>
 *   <li>Dinamicko kreiranje uslova za pretragu</li>
 * </ul>
 *
 * @author Darko
 * @version 1.0
 */
public class KorisnikTest {
    
    private Korisnik korisnik;

    /**
     * Inicijalizacija objekta pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        korisnik = new Korisnik();
    }

    /**
     * Resetovanje reference posle svakog testa.
     */
    @AfterEach
    void tearDown() {
        korisnik = null;
    }

    /**
     * Testira konstruktor bez parametara.
     */
    @Test
    void testKonstruktorBezParametara() {
        assertNotNull(korisnik);
        assertEquals(0, korisnik.getIdKorisnik());
        assertNull(korisnik.getIme());
        assertNull(korisnik.getPrezime());
        assertNull(korisnik.getJmbg());
        assertNull(korisnik.getGrad());
    }

    /**
     * Testira konstruktor sa parametrima.
     */
    @Test
    void testKonstruktorSaParametrima() {
        Grad grad = new Grad(10, "Beograd", "11000");
        Korisnik noviKorisnik = new Korisnik(1, "Petar", "Petrovic", "1111111111111", grad);
        
        assertNotNull(noviKorisnik);
        assertEquals(1, noviKorisnik.getIdKorisnik());
        assertEquals("Petar", noviKorisnik.getIme());
        assertEquals("Petrovic", noviKorisnik.getPrezime());
        assertEquals("1111111111111", noviKorisnik.getJmbg());
        assertEquals(grad, noviKorisnik.getGrad());
    }

    /** Testira set/get za idKorisnik. */
    @Test
    void testSetAndGetIdKorisnik() {
        korisnik.setIdKorisnik(5);
        assertEquals(5, korisnik.getIdKorisnik());
    }

    /** Testira set/get za ime. */
    @Test
    void testSetAndGetIme() {
        korisnik.setIme("Marko");
        assertEquals("Marko", korisnik.getIme());
    }

    /** Testira set/get za prezime. */
    @Test
    void testSetAndGetPrezime() {
        korisnik.setPrezime("Markovic");
        assertEquals("Markovic", korisnik.getPrezime());
    }

    /** Testira set/get za JMBG. */
    @Test
    void testSetAndGetJmbg() {
        korisnik.setJmbg("2222222222222");
        assertEquals("2222222222222", korisnik.getJmbg());
    }

    /** Testira set/get za grad. */
    @Test
    void testSetAndGetGrad() {
        Grad grad = new Grad(15, "Novi Sad", "21000");
        korisnik.setGrad(grad);
        assertEquals(grad, korisnik.getGrad());
    }

    /** Testira ispravan izlaz metode toString(). */
    @Test
    void testToString() {
        korisnik.setIme("Ana");
        korisnik.setPrezime("Anic");
        assertEquals("Ana Anic", korisnik.toString());
    }

    /** Testira naziv tabele. */
    @Test
    void testGetNazivTabele() {
        assertEquals("korisnik", korisnik.getNazivTabele());
    }

    /** Testira alias. */
    @Test
    void testAlies() {
        assertEquals("k", korisnik.alies());
    }

    /** Testira join uslov. */
    @Test
    void testGetJoinUslov() {
        assertEquals(" JOIN grad g ON k.idGrad = g.idGrad", korisnik.getJoinUslov());
    }

    /** Testira kolone za INSERT upit. */
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ime, prezime, jmbg, idGrad", korisnik.getKoloneZaInsert());
    }
    
    /**
     * Testira generisanje vrednosti za INSERT upit.
     * <p>
     * Napomena: implementacija trenutno vraća hardkodovane vrednosti,
     * što je potencijalna greška, ali test potvrđuje postojeće ponašanje.
     */
    @Test
    void testGetVrednostiZaInsert() {
        String expected = "'Ime', 'Prezime', '0000000000000', 1";
        assertEquals(expected, korisnik.getVrednostiZaInsert());
    }

    /** Testira generisanje vrednosti za UPDATE upit. */
    @Test
    void testGetVrednostiZaUpdate() {
        Grad grad = new Grad(3, "Subotica", "24000");
        korisnik.setIme("Pera");
        korisnik.setPrezime("Peric");
        korisnik.setJmbg("3333333333333");
        korisnik.setGrad(grad);
        
        String expected = "ime = 'Pera', prezime = 'Peric', jmbg = '3333333333333', idGrad = 3";
        assertEquals(expected, korisnik.getVrednostiZaUpdate());
    }

    /** Testira required uslov. */
    @Test
    void testRequeredUslov() {
        korisnik.setIdKorisnik(100);
        assertEquals("idKorisnik = 100", korisnik.requeredUslov());
    }

    /** Testira default uslov (uvek *). */
    @Test
    void testGetUslov() {
        assertEquals("*", korisnik.getUslov());
    }

    /**
     * Parametarski test za kreiranje uslova za pretragu.
     *
     * @param ime ime korisnika
     * @param prezime prezime korisnika
     * @param jmbg JMBG korisnika
     * @param gradId id grada
     * @param expected očekivani uslov kao string
     */
    @ParameterizedTest
    @CsvSource({
        "null, null, null, -1, ''",
        "Pera, null, null, -1, 'k.ime LIKE ''%Pera%'''",
        "null, Peric, null, -1, 'k.prezime LIKE ''%Peric%'''",
        "null, null, 123, -1, 'k.jmbg LIKE ''%123%'''",
        "null, null, null, 5, 'k.idGrad = 5'",
        "Milos, null, 456, -1, 'k.ime LIKE ''%Milos%'' AND k.jmbg LIKE ''%456%'''",
        "Milos, Milosevic, null, 10, 'k.ime LIKE ''%Milos%'' AND k.prezime LIKE ''%Milosevic%'' AND k.idGrad = 10'"
    })
    void testGetUslovZaPretragu(String ime, String prezime, String jmbg, int gradId, String expected) {
        korisnik = new Korisnik();
        
        if ("null".equals(ime)) ime = null;
        if ("null".equals(prezime)) prezime = null;
        if ("null".equals(jmbg)) jmbg = null;

        if (ime != null) korisnik.setIme(ime);
        if (prezime != null) korisnik.setPrezime(prezime);
        if (jmbg != null) korisnik.setJmbg(jmbg);
        
        if (gradId > 0) {
            korisnik.setGrad(new Grad(gradId, null, null));
        } else {
            korisnik.setGrad(null);
        }
        
        assertEquals(expected, korisnik.getUslovZaPretragu());
    }

    /**
     * Testira konverziju validnog {@link ResultSet}-a u listu korisnika.
     */
    @Test
    void testKonvertujResultSetUListu_ValidanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1, 2);
        Mockito.when(rs.getString("ime")).thenReturn("Petar", "Marko");
        Mockito.when(rs.getString("prezime")).thenReturn("Petrovic", "Markovic");
        Mockito.when(rs.getString("jmbg")).thenReturn("1111111111111", "2222222222222");

        Mockito.when(rs.getInt("idGrad")).thenReturn(10, 20);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd", "Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000", "21000");

        ArrayList<OpstiDomenskiObjekat> lista = korisnik.konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertEquals(2, lista.size());

        Korisnik k1 = (Korisnik) lista.get(0);
        assertEquals(1, k1.getIdKorisnik());
        assertEquals("Petar", k1.getIme());
        assertEquals("Petrovic", k1.getPrezime());
        assertEquals("1111111111111", k1.getJmbg());
        assertEquals(10, k1.getGrad().getIdGrad());
        assertEquals("Beograd", k1.getGrad().getNaziv());

        Korisnik k2 = (Korisnik) lista.get(1);
        assertEquals(2, k2.getIdKorisnik());
        assertEquals("Marko", k2.getIme());
        assertEquals("Markovic", k2.getPrezime());
        assertEquals("2222222222222", k2.getJmbg());
        assertEquals(20, k2.getGrad().getIdGrad());
        assertEquals("Novi Sad", k2.getGrad().getNaziv());
    }

    /**
     * Testira konverziju jednog reda {@link ResultSet}-a u korisnika.
     */
    @Test
    void testKonvertujResultSetUObjekat_ValidanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idGrad")).thenReturn(10);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getString("ime")).thenReturn("Petar");
        Mockito.when(rs.getString("prezime")).thenReturn("Petrovic");
        Mockito.when(rs.getString("jmbg")).thenReturn("1111111111111");

        Korisnik rezultat = (Korisnik) korisnik.konvertujResultSetUObjekat(rs);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.getIdKorisnik());
        assertEquals("Petar", rezultat.getIme());
        assertEquals("Petrovic", rezultat.getPrezime());
        assertEquals("1111111111111", rezultat.getJmbg());
        assertEquals(10, rezultat.getGrad().getIdGrad());
        assertEquals("Beograd", rezultat.getGrad().getNaziv());
        assertEquals("11000", rezultat.getGrad().getPostanskiBroj());
    }
}
