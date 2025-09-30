package domen;

import domen.Grad;
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
 * Test klasa za domenski objekat Grad.
 * Sadrzi detaljne testove za konstruktore, gettere, settere,
 * i sve metode iz OpstiDomenskiObjekat interfejsa.
 * Svi scenariji, ukljucujuci i rubne slucajeve, su testirani.
 * @author Darko
 * @version 1.0
 */
public class GradTest {
    
    private Grad grad;
    
    /**
     * Postavlja pocetno stanje za svaki test.
     * Kreira novu instancu klase Grad pre svakog testa kako bi se osiguralo
     * da su testovi nezavisni jedan od drugog.
     */
    @BeforeEach
    public void setUp() {
        grad = new Grad();
    }
    
    /**
     * Cisti resurse nakon svakog testa.
     * Postavlja instancu Grada na null.
     */
    @AfterEach
    public void tearDown() {
        grad = null;
    }

    /**
     * Testira podrazumevani konstruktor klase Grad.
     * Proverava da li su svi atributi inicijalizovani na podrazumevane vrednosti.
     */
    @Test
    public void testKonstruktor_BezParametara() {
        assertNotNull(grad);
        assertEquals(0, grad.getIdGrad());
        assertNull(grad.getNaziv());
        assertNull(grad.getPostanskiBroj());
    }

    /**
     * Testira parametrizovani konstruktor klase Grad.
     * Proverava da li su atributi ispravno postavljeni.
     */
    @Test
    public void testKonstruktor_SaParametrima() {
        Grad noviGrad = new Grad(10, "Beograd", "11000");
        assertNotNull(noviGrad);
        assertEquals(10, noviGrad.getIdGrad());
        assertEquals("Beograd", noviGrad.getNaziv());
        assertEquals("11000", noviGrad.getPostanskiBroj());
    }
    
    /**
     * Testira setter i getter metodu za ID grada.
     */
    @Test
    public void testSetIdGrad() {
        grad.setIdGrad(5);
        assertEquals(5, grad.getIdGrad());
    }
    
    /**
     * Testira setter i getter metodu za naziv grada sa ispravnim podacima.
     */
    @Test
    public void testSetNaziv_Ispravan() {
        grad.setNaziv("Novi Sad");
        assertEquals("Novi Sad", grad.getNaziv());
    }

    /**
     * Testira setter i getter metodu za naziv grada sa praznim stringom.
     */
    @Test
    public void testSetNaziv_PrazanString() {
        grad.setNaziv("");
        assertEquals("", grad.getNaziv());
    }
    
    /**
     * Testira setter i getter metodu za postanski broj sa ispravnim podacima.
     */
    @Test
    public void testSetPostanskiBroj_Ispravan() {
        grad.setPostanskiBroj("21000");
        assertEquals("21000", grad.getPostanskiBroj());
    }
    
    /**
     * Testira setter i getter metodu za postanski broj sa praznim stringom.
     */
    @Test
    public void testSetPostanskiBroj_PrazanString() {
        grad.setPostanskiBroj("");
        assertEquals("", grad.getPostanskiBroj());
    }

    /**
     * Testira toString metodu klase Grad.
     * Proverava da li metoda vraca ispravan naziv grada.
     */
    @Test
    public void testToString() {
        grad.setNaziv("Beograd");
        assertEquals("Beograd", grad.toString());
    }

    /**
     * Testira da li getVrednostiZaInsert vraca ispravan SQL format za insert.
     */
    @Test
    public void testGetVrednostiZaInsert() {
        grad.setNaziv("Nis");
        grad.setPostanskiBroj("18000");
        String expected = "'Nis', '18000'";
        assertEquals(expected, grad.getVrednostiZaInsert());
    }

    /**
     * Testira getVrednostiZaInsert kada su nazivi null.
     */
    @Test
    public void testGetVrednostiZaInsert_NullVrednosti() {
        String expected = "null, null";
        // Grad je inicijalizovan sa null vrednostima u setup metodi
        assertEquals(expected, grad.getVrednostiZaInsert().replace("'", ""));
    }
    
    /**
     * Testira da li getVrednostiZaUpdate vraca ispravan SQL format za update.
     */
    @Test
    public void testGetVrednostiZaUpdate() {
        grad.setNaziv("Subotica");
        grad.setPostanskiBroj("24000");
        String expected = "naziv = 'Subotica', postanskiBroj = '24000'";
        assertEquals(expected, grad.getVrednostiZaUpdate());
    }
    
    /**
     * Testira getUslovZaPretragu za razlicite scenarije naziva.
     * @param naziv Naziv grada.
     * @param expected Ocekivani rezultat.
     */
    @ParameterizedTest
    @CsvSource({
        "Novi Sad, g.naziv LIKE '%Novi Sad%'",
        "'', ''",
        "null, ''"
    })
    public void testGetUslovZaPretragu(String naziv, String expected) {
        grad.setNaziv(naziv);
        if (naziv == null || naziv.equals("null")) {
            grad.setNaziv(null);
        }
        assertEquals(expected, grad.getUslovZaPretragu());
    }

    /**
     * Testira da li konvertujResultSetUListu ispravno konvertuje ResultSet u listu Grad objekata.
     * Koristi Mockito za simulaciju ResultSet-a.
     * @throws SQLException ako dodje do greske sa bazom.
     */
    @Test
    public void testKonvertujResultSetUListu_ValidanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idGrad")).thenReturn(1, 2);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd", "Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000", "21000");

        ArrayList<OpstiDomenskiObjekat> lista = grad.konvertujResultSetUListu(rs);
        
        assertNotNull(lista);
        assertEquals(2, lista.size());

        Grad grad1 = (Grad) lista.get(0);
        assertEquals(1, grad1.getIdGrad());
        assertEquals("Beograd", grad1.getNaziv());
        assertEquals("11000", grad1.getPostanskiBroj());

        Grad grad2 = (Grad) lista.get(1);
        assertEquals(2, grad2.getIdGrad());
        assertEquals("Novi Sad", grad2.getNaziv());
        assertEquals("21000", grad2.getPostanskiBroj());
    }
    
    /**
     * Testira slucaj kada je ResultSet prazan.
     * @throws SQLException ako dodje do greske sa bazom.
     */
    @Test
    public void testKonvertujResultSetUListu_PrazanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);

        ArrayList<OpstiDomenskiObjekat> lista = grad.konvertujResultSetUListu(rs);
        
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    /**
     * Testira da li konvertujResultSetUObjekat uvek baca UnsupportedOperationException.
     */
    @Test
    public void testKonvertujResultSetUObjekat_BacaUnsupportedOperationException() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        assertThrows(UnsupportedOperationException.class, () -> {
            grad.konvertujResultSetUObjekat(rs);
        });
    }

    /**
     * Testira da li getNazivTabele vraca ispravan naziv.
     */
    @Test
    public void testGetNazivTabele() {
        assertEquals("grad", grad.getNazivTabele());
    }

    /**
     * Testira da li alies() vraca ispravan alias.
     */
    @Test
    public void testAlies() {
        assertEquals("g", grad.alies());
    }

    /**
     * Testira da li getJoinUslov vraca prazan string.
     */
    @Test
    public void testGetJoinUslov() {
        assertEquals("", grad.getJoinUslov());
    }

    /**
     * Testira da li getKoloneZaInsert vraca ispravne kolone.
     */
    @Test
    public void testGetKoloneZaInsert() {
        assertEquals("naziv, postanskiBroj", grad.getKoloneZaInsert());
    }

    /**
     * Testira da li requeredUslov vraca ispravan uslov na osnovu ID-a.
     */
    @Test
    public void testRequeredUslov() {
        grad.setIdGrad(15);
        assertEquals("g.gradID = 15", grad.requeredUslov());
    }

    /**
     * Testira da li getUslov vraca ispravan uslov.
     */
    @Test
    public void testGetUslov() {
        assertEquals("*", grad.getUslov());
    }
}