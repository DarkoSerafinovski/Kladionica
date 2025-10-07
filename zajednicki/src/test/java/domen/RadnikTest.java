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
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RadnikTest {

    private Radnik radnik;

    @BeforeEach
    void setUp() {
        radnik = new Radnik();
    }

    @AfterEach
    void tearDown() {
        radnik = null;
    }

// ----- KONSTRUKTORI -----
    
    @Test
    void testKonstruktorBezParametara(){
        assertNotNull(radnik);
        assertEquals(0, radnik.getIdRadnik());
        assertNull(radnik.getIme());
        assertNull(radnik.getPrezime());
        assertNull(radnik.getKorisnickoIme());
        assertNull(radnik.getLozinka());
        assertNull(radnik.getBrojTelefona());
    }
    
    @Test
    void testKonstruktorIspravniParametri() {
        Radnik r = new Radnik(1, "Marko", "Markovic", "marko.m", "lozinka123", "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }
    
    @Test
    void testKonstruktorImeNull(){
        Radnik r = new Radnik(1, null, "Markovic", "marko.m", "lozinka123", "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertNull(r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }
    
    @Test 
    void testKonstruktorPrezimeNull(){
        Radnik r = new Radnik(1, "Marko", null, "marko.m", "lozinka123", "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertNull(r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }
    
    @Test
    void testKonstruktorKorisnickoImeNull(){
        Radnik r = new Radnik(1, "Marko", "Markovic", null, "lozinka123", "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertNull(r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }
    
    @Test
    void testKonstruktorLozinkaNull(){
        Radnik r = new Radnik(1, "Marko", "Markovic", "marko.m", null, "060-1234567");
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertNull(r.getLozinka());
        assertEquals("060-1234567", r.getBrojTelefona());
    }
    
    @Test
    void testKonstruktorBrojTelefonaNull(){
        Radnik r = new Radnik(1, "Marko", "Markovic", "marko.m", "lozinka123", null);
        assertEquals(1, r.getIdRadnik());
        assertEquals("Marko", r.getIme());
        assertEquals("Markovic", r.getPrezime());
        assertEquals("marko.m", r.getKorisnickoIme());
        assertEquals("lozinka123", r.getLozinka());
        assertNull(r.getBrojTelefona());
    }

    // ----- SETTERI -----

    @Test
    public void testSetIdRadnikValid() {
        radnik.setIdRadnik(5);
        assertEquals(5, radnik.getIdRadnik());
    }

    @Test
    public void testSetIdRadnikNula() {
        radnik.setIdRadnik(0);
        assertEquals(0, radnik.getIdRadnik());
    }

    @Test
    public void testSetImeValid() {
        radnik.setIme("Marko");
        assertEquals("Marko", radnik.getIme());
    }

    @Test
    public void testSetImeNull() {
        radnik.setIme(null);
        assertNull(radnik.getIme());
    }

    @Test
    public void testSetPrezimeValid() {
        radnik.setPrezime("Markovic");
        assertEquals("Markovic", radnik.getPrezime());
    }

    @Test
    public void testSetPrezimeNull() {
        radnik.setPrezime(null);
        assertNull(radnik.getPrezime());
    }

    @Test
    public void testSetKorisnickoImeValid() {
        radnik.setKorisnickoIme("m.marko");
        assertEquals("m.marko", radnik.getKorisnickoIme());
    }

    @Test
    public void testSetKorisnickoImeNull() {
        radnik.setKorisnickoIme(null);
        assertNull(radnik.getKorisnickoIme());
    }

    @Test
    public void testSetLozinkaValidno() {
        radnik.setLozinka("lozinka123");
        assertEquals("lozinka123", radnik.getLozinka());
    }

    @Test
    public void testSetLozinkaNull() {
        radnik.setLozinka(null);
        assertNull(radnik.getLozinka());
    }

    @Test
    public void testSetBrojTelefonaValidno() {
        radnik.setBrojTelefona("0641234567");
        assertEquals("0641234567", radnik.getBrojTelefona());
    }

    @Test
    public void testSetBrojTelefonaNull() {
        radnik.setBrojTelefona(null);
        assertNull(radnik.getBrojTelefona());
    }
    
    // ----- TO STRING -----
    
    @Test
    void testToString() {
        radnik.setIdRadnik(1);
        radnik.setIme("Pera");
        radnik.setPrezime("Peric");
        radnik.setKorisnickoIme("pera.peric");
        radnik.setLozinka("loznika123");
        radnik.setBrojTelefona("060/334-456");
        
        assertEquals("Pera Peric", radnik.toString());
    }
    
    // ----- SQL METODE -----
    
    @Test
    void testGetVrednostiZaInsert() {
        radnik.setIme("Petar");
        radnik.setPrezime("Petrovic");
        radnik.setKorisnickoIme("petar.petrovic");
        radnik.setLozinka("lozinka123");
        radnik.setBrojTelefona("123456789");
        String expected = "'Petar', 'Petrovic', 'petar.petrovic', 'lozinka123', '123456789'";
        assertEquals(expected, radnik.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        radnik.setIme("Marko");
        radnik.setPrezime("Markovic");
        radnik.setBrojTelefona("069/234-234");
        String expected = "ime = 'Marko', prezime = 'Markovic', brojTelefona = '069/234-234'";
        assertEquals(expected, radnik.getVrednostiZaUpdate());
    }

    @ParameterizedTest
    @CsvSource(
        value = {
           // korisnicko ime, lozinka, ime, prezime, expected
            "marko.m, lozinka123, null, null, r.korisnickoIme = 'marko.m' AND r.lozinka = 'lozinka123'",
            "null, null, Petar, null, r.ime LIKE '%Petar%'",
            "null, null, null, Peric, r.prezime LIKE '%Peric%'",
            "test, null, null, null, ''",
            "null, test, null, null, ''"
        },
        nullValues = "null"
    )
            
    void testGetUslovZaPretragu(String korIme, String lozinka, String ime, String prezime, String expected) {

        radnik.setKorisnickoIme(korIme);
        radnik.setLozinka(lozinka);
        radnik.setIme(ime);
        radnik.setPrezime(prezime);
        
        assertEquals(expected, radnik.getUslovZaPretragu());
    }
    
    // ----- RESULT SET METODE -----


    @Test
    public void testKonvertujResultSetUListu_TriReda() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, true, false);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(1, 2, 3);
        Mockito.when(rs.getString("ime")).thenReturn("Marko", "Petar", "Jovan");
        Mockito.when(rs.getString("prezime")).thenReturn("Markovic", "Petrovic", "Jovanovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m", "petar.p", "jovan.j");
        Mockito.when(rs.getString("lozinka")).thenReturn("loz1", "loz2", "loz3");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("064111222", "065333444", "066555666");

        ArrayList<OpstiDomenskiObjekat> lista = radnik.konvertujResultSetUListu(rs);

        assertEquals(3, lista.size());
    }

    @Test
    public void testKonvertujResultSetUListu_JedanRed() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(5);
        Mockito.when(rs.getString("ime")).thenReturn("Nikola");
        Mockito.when(rs.getString("prezime")).thenReturn("Nikolic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("nikola.n");
        Mockito.when(rs.getString("lozinka")).thenReturn("123");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("066789456");

        ArrayList<OpstiDomenskiObjekat> lista = radnik.konvertujResultSetUListu(rs);

        assertEquals(1, lista.size());
    }

    @Test
    public void testKonvertujResultSetUListu_PrazanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(false);

        ArrayList<OpstiDomenskiObjekat> lista = radnik.konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testKonvertujResultSetUListu_SQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenThrow(new SQLException("Greška u čitanju"));

        assertThrows(SQLException.class, () -> radnik.konvertujResultSetUListu(rs));
    }

    @Test
    public void testKonvertujResultSetUObjekatValidniPodaci() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(10);
        Mockito.when(rs.getString("ime")).thenReturn("Milan");
        Mockito.when(rs.getString("prezime")).thenReturn("Milic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("milan.m");
        Mockito.when(rs.getString("lozinka")).thenReturn("lozinka123");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("065555666");

        Radnik rezultat = (Radnik) radnik.konvertujResultSetUObjekat(rs);

        assertNotNull(rezultat);
        assertEquals(10, rezultat.getIdRadnik());
        assertEquals("Milan", rezultat.getIme());
        assertEquals("Milic", rezultat.getPrezime());
        assertEquals("milan.m", rezultat.getKorisnickoIme());
        assertEquals("lozinka123", rezultat.getLozinka());
        assertEquals("065555666", rezultat.getBrojTelefona());
    }

    @Test
    public void testKonvertujResultSetUObjekatNullVrednosti() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(0);
        Mockito.when(rs.getString("ime")).thenReturn(null);
        Mockito.when(rs.getString("prezime")).thenReturn(null);
        Mockito.when(rs.getString("korisnickoIme")).thenReturn(null);
        Mockito.when(rs.getString("lozinka")).thenReturn(null);
        Mockito.when(rs.getString("brojTelefona")).thenReturn(null);

        Radnik rezultat = (Radnik) radnik.konvertujResultSetUObjekat(rs);

        assertNotNull(rezultat);
        assertEquals(0, rezultat.getIdRadnik());
        assertNull(rezultat.getIme());
        assertNull(rezultat.getPrezime());
        assertNull(rezultat.getKorisnickoIme());
        assertNull(rezultat.getLozinka());
        assertNull(rezultat.getBrojTelefona());
    }

    @Test
    public void testKonvertujResultSetUObjekat_SQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getInt("idRadnik")).thenThrow(new SQLException("Greška pri čitanju"));

        assertThrows(SQLException.class, () -> radnik.konvertujResultSetUObjekat(rs));
    }

    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele() {
        assertEquals("radnik", radnik.getNazivTabele());
    }

    @Test
    void testAlies() {
        assertEquals("r", radnik.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals("", radnik.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ime, prezime, korisnickoIme, lozinka, brojTelefona", radnik.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        radnik.setIdRadnik(10);
        assertEquals("r.idRadnik = 10", radnik.requeredUslov());
    }

    @Test
    void testGetUslov() {
        assertEquals("*", radnik.getUslov());
    }
}
