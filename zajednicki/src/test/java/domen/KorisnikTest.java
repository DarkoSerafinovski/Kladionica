package domen;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class KorisnikTest {

    private Korisnik korisnik;
    private Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad(1, "Beograd", "11000");
        korisnik = new Korisnik();
    }

    @AfterEach
    void tearDown() {
        korisnik = null;
        grad = null;
    }

    // ----- KONSTRUKTORI -----
    
    
    @Test
    void testKonstruktor_BezParametara() {
        assertNotNull(korisnik);
        assertEquals(0, korisnik.getIdKorisnik());
        assertNull(korisnik.getIme());
        assertNull(korisnik.getPrezime());
        assertNull(korisnik.getJmbg());
        assertNull(korisnik.getGrad());
    }

    @Test
    void testKonstruktor_SviParametri() {
        Korisnik k = new Korisnik(1, "Petar", "Petrovic", "1234567890123", grad);
        assertEquals(1, k.getIdKorisnik());
        assertEquals("Petar", k.getIme());
        assertEquals("Petrovic", k.getPrezime());
        assertEquals("1234567890123", k.getJmbg());
        assertEquals(grad, k.getGrad());
    }
    
    @Test
    void testParametarskiKonstruktor_NullIme() {
        Korisnik korisnik = new Korisnik(1, null, "Petrovic", "1234567890123", grad);

        assertEquals(1, korisnik.getIdKorisnik());
        assertNull(korisnik.getIme());
        assertEquals("Petrovic", korisnik.getPrezime());
        assertEquals("1234567890123", korisnik.getJmbg());
        assertEquals(grad, korisnik.getGrad());
    }

    @Test
    void testParametarskiKonstruktor_NullPrezime() {
        Korisnik korisnik = new Korisnik(1, "Petar", null, "1234567890123", grad);

        assertEquals(1, korisnik.getIdKorisnik());
        assertEquals("Petar", korisnik.getIme());
        assertNull(korisnik.getPrezime());
        assertEquals("1234567890123", korisnik.getJmbg());
        assertEquals(grad, korisnik.getGrad());
    }

    @Test
    void testParametarskiKonstruktor_NullJmbg() {
        Korisnik korisnik = new Korisnik(1, "Petar", "Petrovic", null, grad);

        assertEquals(1, korisnik.getIdKorisnik());
        assertEquals("Petar", korisnik.getIme());
        assertEquals("Petrovic", korisnik.getPrezime());
        assertNull(korisnik.getJmbg());
        assertEquals(grad, korisnik.getGrad());
    }

    @Test
    void testParametarskiKonstruktor_NullGrad() {
        Korisnik korisnik = new Korisnik(1, "Petar", "Petrovic", "1234567890123", null);

        assertEquals(1, korisnik.getIdKorisnik());
        assertEquals("Petar", korisnik.getIme());
        assertEquals("Petrovic", korisnik.getPrezime());
        assertEquals("1234567890123", korisnik.getJmbg());
        assertNull(korisnik.getGrad());
    }

    @Test
    void testParametarskiKonstruktor_SviNull() {
        Korisnik korisnik = new Korisnik(0, null, null, null, null);

        assertEquals(0, korisnik.getIdKorisnik());
        assertNull(korisnik.getIme());
        assertNull(korisnik.getPrezime());
        assertNull(korisnik.getJmbg());
        assertNull(korisnik.getGrad());
    }

    // ----- SETTERI -----
    
    @Test 
    void testSetIdKorisnikVrednost(){
        korisnik.setIdKorisnik(1);
        assertEquals(1, korisnik.getIdKorisnik());
    }

    @Test
    void testSetImeVrednost(){
        korisnik.setIme("Petar");
        assertEquals("Petar", korisnik.getIme());
    }
    
    @Test
    void testSetImeNull(){
        korisnik.setIme(null);
        assertNull(korisnik.getIme());
    }
    
    @Test
    void testSetPrezimeVrednost(){
        korisnik.setPrezime("Petrovic");
        assertEquals("Petrovic", korisnik.getPrezime());
    }
    
    @Test
    void testSetPrezimeNull(){
        korisnik.setPrezime(null);
        assertNull(korisnik.getPrezime());
    }
    
    @Test
    void testSetJMBGVrednost(){
        korisnik.setJmbg("1234567890123");
        assertEquals("1234567890123", korisnik.getJmbg());
    }
    
    @Test
    void testSetJMBGNull(){
        korisnik.setJmbg(null);
        assertNull(korisnik.getJmbg());
    }
    
    @Test
    void testSetGradVrednost(){
        korisnik.setGrad(grad);
        assertEquals(grad, korisnik.getGrad());
    }
    
    @Test
    void testSetGradNull(){
        korisnik.setGrad(null);
        assertNull(korisnik.getGrad());
    }
    
    // ----- TO STRING -----
    @Test
    void testToString() {
        korisnik.setIme("Petar");
        korisnik.setPrezime("Petrovic");
        assertEquals("Petar Petrovic", korisnik.toString());
    }

    // ----- SQL METODE -----
    
    @Test
    void testGetVrednostiZaInsert() {
        korisnik.setIme("Petar");
        korisnik.setPrezime("Petrovic");
        korisnik.setJmbg("1234567890123");
        korisnik.setGrad(grad);
        String expected = "'Petar', 'Petrovic', '1234567890123', 1";
        assertEquals(expected, korisnik.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        korisnik.setIme("Petar");
        korisnik.setPrezime("Petrovic");
        korisnik.setJmbg("1234567890123");
        korisnik.setGrad(grad);
        String expected = "ime = 'Petar', prezime = 'Petrovic', jmbg = '1234567890123', idGrad = 1";
        assertEquals(expected, korisnik.getVrednostiZaUpdate());
    }
    
    @ParameterizedTest
    @CsvSource({
        // ime, prezime, jmbg, gradId, expected
        "'', '', '', 0, ''",                                  
        "Petar, '', '', 0, 'k.ime LIKE ''%Petar%'''",           
        "'', Petrovic, '', 0, 'k.prezime LIKE ''%Petrovic%'''",  
        "'', '', 1234567890123, 0, 'k.jmbg LIKE ''%1234567890123%'''",
        "'', '', '', 1, 'k.idGrad = 1'",                            
        "Petar, Petrovic, '', 0, 'k.ime LIKE ''%Petar%'' AND k.prezime LIKE ''%Petrovic%'''",
        "Petar, '', 1234567890123, 0, 'k.ime LIKE ''%Petar%'' AND k.jmbg LIKE ''%1234567890123%'''",
        "Petar, '', '', 1, 'k.ime LIKE ''%Petar%'' AND k.idGrad = 1'",
        "'', Petrovic, 1234567890123, 0, 'k.prezime LIKE ''%Petrovic%'' AND k.jmbg LIKE ''%1234567890123%''' ",
        "Petar, Petrovic, 1234567890123, 1, 'k.ime LIKE ''%Petar%'' AND k.prezime LIKE ''%Petrovic%'' AND k.jmbg LIKE ''%1234567890123%'' AND k.idGrad = 1'" // svi popunjeni
    })
    void testGetUslovZaPretragu(String ime, String prezime, String jmbg, int gradId, String expected) {
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setJmbg(jmbg);
        korisnik.setGrad(gradId > 0 ? new Grad(gradId, "Beograd", "11000") : null);
        assertEquals(expected, korisnik.getUslovZaPretragu());
    }
    
    // ----- RESULT SET -----
    
    @Test
    void testKonvertujResultSetUListuDvaResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1, 2);
        Mockito.when(rs.getString("ime")).thenReturn("Pera", "Mika");
        Mockito.when(rs.getString("prezime")).thenReturn("Peric", "Mikic");
        Mockito.when(rs.getString("jmbg")).thenReturn("1234567890123", "9876543210987");
        Mockito.when(rs.getInt("idGrad")).thenReturn(1, 2);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd", "Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000", "21000");

        ArrayList<OpstiDomenskiObjekat> lista = korisnik.konvertujResultSetUListu(rs);
        assertEquals(2, lista.size());
    }
    
    @Test
    void testKonvertujResultSetUListuJedanResultSet() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getString("ime")).thenReturn("Pera");
        Mockito.when(rs.getString("prezime")).thenReturn("Peric");
        Mockito.when(rs.getString("jmbg")).thenReturn("1234567890123");
        Mockito.when(rs.getInt("idGrad")).thenReturn(1);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");
        
        ArrayList<OpstiDomenskiObjekat> lista = korisnik.konvertujResultSetUListu(rs);
        assertEquals(1, lista.size());
    }
    
    @Test
    void testKonvertujResultSetUListuPrazanResultSet() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);
        ArrayList<OpstiDomenskiObjekat> lista = korisnik.konvertujResultSetUListu(rs);
        assertNotNull(lista);
        assertEquals(0, lista.size());
    }
    
    @Test
    void testKonvertujResultSetUListuBacaSQLException() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greska u ResultSet-u"));
        
        assertThrows(SQLException.class, () -> {
            korisnik.konvertujResultSetUListu(rs);
        });
    }

    @Test
    void testKonvertujResultSetUObjekat() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getString("ime")).thenReturn("Pera");
        Mockito.when(rs.getString("prezime")).thenReturn("Peric");
        Mockito.when(rs.getString("jmbg")).thenReturn("1234567890123");
        Mockito.when(rs.getInt("idGrad")).thenReturn(1);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");

        Korisnik k = (Korisnik) korisnik.konvertujResultSetUObjekat(rs);
        assertEquals(1, k.getIdKorisnik());
        assertEquals("Pera", k.getIme());
        assertEquals("Peric", k.getPrezime());
        assertEquals("1234567890123", k.getJmbg());
        assertNotNull(k.getGrad());
        assertEquals(1, k.getGrad().getIdGrad());
        assertEquals("Beograd", k.getGrad().getNaziv());
        assertEquals("11000", k.getGrad().getPostanskiBroj());
    }
    
    @Test
    void testKonvertujResultSetUObjekat_NekeNullVrednosti() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(2);
        Mockito.when(rs.getString("ime")).thenReturn(null);
        Mockito.when(rs.getString("prezime")).thenReturn("Markovic");
        Mockito.when(rs.getString("jmbg")).thenReturn(null);
        Mockito.when(rs.getInt("idGrad")).thenReturn(2);
        Mockito.when(rs.getString("naziv")).thenReturn("Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("21000");

        Korisnik k = (Korisnik) korisnik.konvertujResultSetUObjekat(rs);

        assertEquals(2, k.getIdKorisnik());
        assertNull(k.getIme());
        assertEquals("Markovic", k.getPrezime());
        assertNull(k.getJmbg());
        assertNotNull(k.getGrad());
        assertEquals(2, k.getGrad().getIdGrad());
        assertEquals("Novi Sad", k.getGrad().getNaziv());
        assertEquals("21000", k.getGrad().getPostanskiBroj());
    }
    
    @Test
    void testKonvertujResultSetUObjekat_SveNullVrednosti() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(0);
        Mockito.when(rs.getString("ime")).thenReturn(null);
        Mockito.when(rs.getString("prezime")).thenReturn(null);
        Mockito.when(rs.getString("jmbg")).thenReturn(null);
        Mockito.when(rs.getInt("idGrad")).thenReturn(0);
        Mockito.when(rs.getString("naziv")).thenReturn(null);
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("26202");

        Korisnik k = (Korisnik) korisnik.konvertujResultSetUObjekat(rs);

        assertEquals(0, k.getIdKorisnik());
        assertNull(k.getIme());
        assertNull(k.getPrezime());
        assertNull(k.getJmbg());
        assertNotNull(k.getGrad()); 
        assertEquals(0, k.getGrad().getIdGrad());
        assertEquals("26202", k.getGrad().getPostanskiBroj());
        assertNull(k.getGrad().getNaziv());
    }
    
    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele() {
        assertEquals("korisnik", korisnik.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertEquals("k", korisnik.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals(" JOIN grad g ON k.idGrad = g.idGrad", korisnik.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ime, prezime, jmbg, idGrad", korisnik.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        korisnik.setIdKorisnik(5);
        assertEquals("idKorisnik = 5", korisnik.requeredUslov());
    }
}