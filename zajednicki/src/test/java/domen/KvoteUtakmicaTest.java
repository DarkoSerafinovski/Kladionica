package domen;

import domen.KvoteUtakmica;
import domen.TipOpklade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Detaljna test klasa za klasu KvoteUtakmica.
 * Testira ispravnost inicijalizacije kvota i funkcionalnost metoda za pristup kvotama.
 * 
 * Testovi obuhvataju:
 * <ul>
 *     <li>Validaciju inicijalizacije objekta.</li>
 *     <li>Proveru da su sve utakmice i njihove kvote ispravno dodate.</li>
 *     <li>Proveru kvota za pojedinačne utakmice.</li>
 *     <li>Ponašanje za nepostojeće utakmice.</li>
 *     <li>Testiranje vrednosti kvota za srednje utakmice iz niza.</li>
 * </ul>
 * 
 * @author Darko
 * @version 1.0
 */
public class KvoteUtakmicaTest {

    private KvoteUtakmica kvoteUtakmica;

    @BeforeEach
    void setUp() {
        kvoteUtakmica = new KvoteUtakmica();
    }

    @AfterEach
    void tearDown() {
        kvoteUtakmica = null;
    }

    /**
     * Testira inicijalizaciju objekta kroz konstruktor.
     */
    @Test
    void testKonstruktor() {
        assertNotNull(kvoteUtakmica, "Objekat KvoteUtakmica ne bi trebalo da bude null.");
    }

    /**
     * Testira da li su kvote za sve utakmice ispravno dodate
     * i da li nepostojeća utakmica vraća praznu mapu.
     */
    @Test
    void testSveUtakmiceDodate() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(1);
        assertEquals(10, kvote.size(), "Utakmica 1 bi trebalo da ima 10 tipova kvota.");

        kvote = kvoteUtakmica.getKvoteZaUtakmicu(20);
        assertEquals(10, kvote.size(), "Utakmica 20 bi trebalo da ima 10 tipova kvota.");
        
        Map<TipOpklade, Double> kvoteZaNepostojecuUtakmicu = kvoteUtakmica.getKvoteZaUtakmicu(21);
        assertTrue(kvoteZaNepostojecuUtakmicu.isEmpty(), "Za nepostojecu utakmicu trebalo bi da vrati praznu mapu.");
    }

    /**
     * Testira ispravnost kvota za validnu utakmicu.
     */
    @Test
    void testGetKvoteZaUtakmicu_ValidnaUtakmica() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(1);
        assertFalse(kvote.isEmpty(), "Mapa kvota ne bi trebalo da bude prazna.");

        assertEquals(1.5, kvote.get(TipOpklade.POBEDA_DOMACIN), "Kvota za pobedu domacina nije ispravna.");
        assertEquals(2.8, kvote.get(TipOpklade.POBEDA_GOST), "Kvota za pobedu gosta nije ispravna.");
        assertEquals(3.2, kvote.get(TipOpklade.NERESENO), "Kvota za nereseno nije ispravna.");
    }

    /**
     * Testira ponašanje metode kada se zatraže kvote za nepostojeću utakmicu.
     */
    @Test
    void testGetKvoteZaUtakmicu_NepostojecaUtakmica() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(99);
        assertNotNull(kvote, "Metoda bi trebalo da vrati praznu, ne null mapu.");
        assertTrue(kvote.isEmpty(), "Za nepostojecu utakmicu trebalo bi da vrati praznu mapu.");
    }

    /**
     * Testira kvote za jednu od srednjih utakmica u nizu (utakmica sa ID-jem 10).
     */
    @Test
    void testGetKvoteZaUtakmicu_SrednjaUtakmica() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(10);
        assertFalse(kvote.isEmpty(), "Mapa za utakmicu 10 ne bi trebalo da bude prazna.");
        
        assertEquals(1.6, kvote.get(TipOpklade.POBEDA_DOMACIN));
        assertEquals(2.4, kvote.get(TipOpklade.POBEDA_GOST));
        assertEquals(3.3, kvote.get(TipOpklade.NERESENO));
    }
}
