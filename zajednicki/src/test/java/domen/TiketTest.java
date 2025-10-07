package domen;

import domen.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

public class TiketTest {

    private Tiket tiket;
    private Radnik radnik;
    private Korisnik korisnik;
    private Grad grad;
    private Par par;
    private Utakmica utakmica;

    @BeforeEach
    public void setUp() {
        tiket = new Tiket();
        korisnik = new Korisnik();
        radnik = new Radnik();
    }
    
    // ----- KONSTRUKTORI -----
    
    @Test
    public void testBezparametarskiKonstruktor() {
        assertEquals(0, tiket.getIdTiket());
        assertEquals(0, tiket.getUlog());
        assertEquals(1, tiket.getUkupnaKvota());
        assertEquals(0, tiket.getMoguciDobitak());
        assertEquals(-1, tiket.getStatus());
        assertNull(tiket.getRadnik());
        assertNull(tiket.getKorisnik());
        assertNotNull(tiket.getParovi());
        assertEquals(0, tiket.getParovi().size());
    }

    @Test
    public void testParametarskiKonstruktorValid() {
        Radnik radnik = new Radnik();
        Korisnik korisnik = new Korisnik();
        List<Par> parovi = new ArrayList<>();

        tiket = new Tiket(1, 100.0, 2.5, 250.0, 0, radnik, korisnik, parovi);

        assertEquals(1, tiket.getIdTiket());
        assertEquals(100.0, tiket.getUlog());
        assertEquals(2.5, tiket.getUkupnaKvota());
        assertEquals(250.0, tiket.getMoguciDobitak());
        assertEquals(0, tiket.getStatus());
        assertEquals(radnik, tiket.getRadnik());
        assertEquals(korisnik, tiket.getKorisnik());
        assertEquals(parovi, tiket.getParovi());
    }

    @Test
    public void testParametarskiKonstruktorRadnikNull() {
        Korisnik korisnik = new Korisnik();
        List<Par> parovi = new ArrayList<>();

        tiket = new Tiket(1, 100.0, 2.5, 250.0, 0, null, korisnik, parovi);

        assertNull(tiket.getRadnik());
        assertEquals(korisnik, tiket.getKorisnik());
        assertEquals(0, tiket.getParovi().size());
    }

    @Test
    public void testParametarskiKonstruktorKorisnikNull() {
        Radnik radnik = new Radnik();
        List<Par> parovi = new ArrayList<>();

        tiket = new Tiket(1, 100.0, 2.5, 250.0, 0, radnik, null, parovi);

        assertEquals(radnik, tiket.getRadnik());
        assertNull(tiket.getKorisnik());
        assertEquals(0, tiket.getParovi().size());
    }

    @Test
    public void testParametarskiKonstruktorParoviNull() {
        Radnik radnik = new Radnik();
        Korisnik korisnik = new Korisnik();

        tiket = new Tiket(1, 100.0, 2.5, 250.0, 0, radnik, korisnik, null);

        assertEquals(radnik, tiket.getRadnik());
        assertEquals(korisnik, tiket.getKorisnik());
        assertNull(tiket.getParovi());
    }   

    // ----- SETTERI -----

    @Test
    public void testSetIdTiketValid() {
        tiket.setIdTiket(10);
        assertEquals(10, tiket.getIdTiket());
    }

    @Test
    public void testSetIdTiketNull() {
        tiket.setIdTiket(0); 
        assertEquals(0, tiket.getIdTiket());
    }

    @Test
    public void testSetUlogValid() {
        tiket.setUlog(150.5);
        assertEquals(150.5, tiket.getUlog());
    }

    @Test
    public void testSetUlogNull() {
        tiket.setUlog(0);
        assertEquals(0.0, tiket.getUlog());
    }

    @Test
    public void testSetUkupnaKvotaValid() {
        tiket.setUkupnaKvota(2.3);
        assertEquals(2.3, tiket.getUkupnaKvota());
    }

    @Test
    public void testSetUkupnaKvotaNull() {
        tiket.setUkupnaKvota(0.0);
        assertEquals(0.0, tiket.getUkupnaKvota());
    }

    @Test
    public void testSetMoguciDobitakValid() {
        tiket.setMoguciDobitak(500.0);
        assertEquals(500.0, tiket.getMoguciDobitak());
    }

    @Test
    public void testSetMoguciDobitakNull() {
        tiket.setMoguciDobitak(0.0);
        assertEquals(0.0, tiket.getMoguciDobitak());
    }

    @Test
    public void testSetStatusValid() {
        tiket.setStatus(1);
        assertEquals(1, tiket.getStatus());
    }

    @Test
    public void testSetStatusNullEquivalent() {
        tiket.setStatus(-1); 
        assertEquals(-1, tiket.getStatus());
    }

    @Test
    public void testSetRadnikValid() {
        Radnik radnik = new Radnik();
        tiket.setRadnik(radnik);
        assertEquals(radnik, tiket.getRadnik());
    }

    @Test
    public void testSetRadnikNull() {
        tiket.setRadnik(null);
        assertNull(tiket.getRadnik());
    }

    @Test
    public void testSetKorisnikValid() {
        Korisnik korisnik = new Korisnik();
        tiket.setKorisnik(korisnik);
        assertEquals(korisnik, tiket.getKorisnik());
    }

    @Test
    public void testSetKorisnikNull() {
        tiket.setKorisnik(null);
        assertNull(tiket.getKorisnik());
    }

    @Test
    public void testSetParoviValid() {
        List<Par> parovi = new ArrayList<>();
        tiket.setParovi(parovi);
        assertEquals(parovi, tiket.getParovi());
    }

    @Test
    public void testSetParoviNull() {
        tiket.setParovi(null);
        assertNull(tiket.getParovi());
    }

    @Test
    public void testDodajParValid() {
        Par par = new Par();
        tiket.dodajPar(par);
        assertNotNull(tiket.getParovi());
        assertEquals(1, tiket.getParovi().size());
        assertEquals(par, tiket.getParovi().get(0));
    }

    @Test
    public void testDodajParParoviNull() {
        tiket.setParovi(null);
        Par par = new Par();
        tiket.dodajPar(par);
        assertNotNull(tiket.getParovi());
        assertEquals(1, tiket.getParovi().size());
        assertEquals(par, tiket.getParovi().get(0));
    }

    // ----- TO STRING -----
    
    @Test
    public void testToStringDefaultVrednosti() {
        String result = tiket.toString();

        assertTrue(result.contains("idTiket=0"));
        assertTrue(result.contains("ulog=0.0"));
        assertTrue(result.contains("ukupnaKvota=1.0"));
        assertTrue(result.contains("moguciDobitak=0.0"));
        assertTrue(result.contains("status=-1"));
        assertTrue(result.contains("radnik=null"));
        assertTrue(result.contains("korisnik=null"));
        assertTrue(result.contains("parovi=0 parova") || result.contains("parovi=null"));
    }

    @Test
    public void testToStringSveVrednostiValidne() {
        Radnik radnik = new Radnik();
        radnik.setIme("Marko");
        radnik.setPrezime("Markovic");

        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Petar");
        korisnik.setPrezime("Petrovic");

        List<Par> parovi = new ArrayList<>();
        parovi.add(new Par());
        parovi.add(new Par());

        tiket.setIdTiket(1);
        tiket.setUlog(100.0);
        tiket.setUkupnaKvota(2.5);
        tiket.setMoguciDobitak(250.0);
        tiket.setStatus(0);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);
        tiket.setParovi(parovi);

        String result = tiket.toString();

        assertTrue(result.contains("idTiket=1"));
        assertTrue(result.contains("ulog=100.0"));
        assertTrue(result.contains("ukupnaKvota=2.5"));
        assertTrue(result.contains("moguciDobitak=250.0"));
        assertTrue(result.contains("status=0"));
        assertTrue(result.contains("radnik=Marko Markovic"));
        assertTrue(result.contains("korisnik=Petar Petrovic"));
        assertTrue(result.contains("parovi=2 parova"));
    }

    @Test
    public void testToStringRadnikNull() {
        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Petar");
        korisnik.setPrezime("Petrovic");

        tiket.setRadnik(null);
        tiket.setKorisnik(korisnik);

        String result = tiket.toString();

        assertTrue(result.contains("radnik=null"));
        assertTrue(result.contains("korisnik=Petar Petrovic"));
    }

    @Test
    public void testToStringKorisnikNull() {
        Radnik radnik = new Radnik();
        radnik.setIme("Marko");
        radnik.setPrezime("Markovic");

        tiket.setRadnik(radnik);
        tiket.setKorisnik(null);

        String result = tiket.toString();

        assertTrue(result.contains("radnik=Marko Markovic"));
        assertTrue(result.contains("korisnik=null"));
    }

    @Test
    public void testToStringParoviNull() {
        tiket.setParovi(null);
        String result = tiket.toString();
        assertTrue(result.contains("parovi=null"));
    }

    // ----- SQL METODE -----
    
    @Test
    public void testGetVrednostiZaInsert() {
        tiket.setUlog(100);
        tiket.setUkupnaKvota(2);
        tiket.setMoguciDobitak(200);
        tiket.setStatus(1);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);

        String expected = "100.0, 1, 2.0, 200.0, 0, 0";
        assertEquals(expected, tiket.getVrednostiZaInsert());
    }

    @Test
    public void testGetVrednostiZaUpdateSveVrednosti() {
        tiket.setUlog(100);
        tiket.setUkupnaKvota(2);
        tiket.setMoguciDobitak(200);
        tiket.setStatus(1);
        
        radnik.setIdRadnik(1);
        korisnik.setIdKorisnik(1);
        
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);

        String update = tiket.getVrednostiZaUpdate();
        assertTrue(update.contains("statusTiket = 1"));
        assertTrue(update.contains("ulog = 100.0"));
        assertTrue(update.contains("ukupnaKvota = 2.0"));
        assertTrue(update.contains("dobitak = 200.0"));
        assertTrue(update.contains("idRadnik = 1"));
        assertTrue(update.contains("idKorisnik = 1"));
    }
    
    @Test
    public void testGetVrednostiZaUpdateRadnikKorisnikNull() {
        tiket.setUlog(100);
        tiket.setUkupnaKvota(2);
        tiket.setMoguciDobitak(200);
        tiket.setStatus(1);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);

        String update = tiket.getVrednostiZaUpdate();
        assertTrue(update.contains("statusTiket = 1"));
        assertTrue(update.contains("ulog = 100.0"));
        assertTrue(update.contains("ukupnaKvota = 2.0"));
        assertTrue(update.contains("dobitak = 200.0"));
    }

    @ParameterizedTest
    @CsvSource({
        // radnikId, korisnikId, idUtakmica, ulog, ukupnaKvota, moguciDobitak, status, expected
        "1,0,0,0,0,0,-1, t.idRadnik = 1",
        "0,2,0,0,0,0,-1, t.idKorisnik = 2",
        "0,0,5,0,0,0,-1, u.idUtakmica = 5",
        "0,0,0,100,0,0,-1, t.ulog > 100.0",
        "0,0,0,0,2.5,0,-1, t.ukupnaKvota > 2.5",
        "0,0,0,0,0,250,-1, t.dobitak > 250.0",
        "0,0,0,0,0,0,1, t.statusTiket = '1'",
        "1,2,3,100,2.5,250,0, t.idRadnik = 1 AND t.idKorisnik = 2 AND u.idUtakmica = 3 AND t.ulog > 100.0 AND t.ukupnaKvota > 2.5 AND t.dobitak > 250.0 AND t.statusTiket = '0'"
    })
    public void testGetUslovZaPretraguParametrizovano(int radnikId, int korisnikId, int idUtakmica,
                                                      double ulog, double ukupnaKvota, double moguciDobitak,
                                                      int status, String expected) {

        
            radnik.setIdRadnik(radnikId);
            tiket.setRadnik(radnik);
        
            korisnik.setIdKorisnik(korisnikId);
            tiket.setKorisnik(korisnik);
        
            Utakmica utakmica = new Utakmica();
            utakmica.setIdUtakmica(idUtakmica);
            Par par = new Par();
            par.setUtakmica(utakmica);
            List<Par> parovi = new ArrayList<>();
            parovi.add(par);
            tiket.setParovi(parovi);
        
            tiket.setUlog(ulog);
            tiket.setUkupnaKvota(ukupnaKvota);
            tiket.setMoguciDobitak(moguciDobitak);
            tiket.setStatus(status);

        assertEquals(expected, tiket.getUslovZaPretragu());
    }
 
    // ----- RESULT SET -----
    
    @Test
    public void testKonvertujResultSetUListu_DvaTiketa() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, true, true, false); 

        Mockito.when(rs.getInt("idTiket")).thenReturn(1, 1, 2, 2);
        Mockito.when(rs.getDouble("ulog")).thenReturn(100.0, 100.0, 50.0, 50.0);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(2.0, 2.0, 1.5, 1.5);
        Mockito.when(rs.getDouble("dobitak")).thenReturn(200.0, 200.0, 75.0, 75.0);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(0, 0, 1, 1);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(1, 1, 2, 2);
        Mockito.when(rs.getString("imeRadnik")).thenReturn("Marko", "Marko", "Petar", "Petar");
        Mockito.when(rs.getString("prezimeRadnik")).thenReturn("Markovic", "Markovic", "Petrovic", "Petrovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m", "marko.m", "petar.p", "petar.p");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("064111222", "064111222", "065333444", "065333444");

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1, 1, 2, 2);
        Mockito.when(rs.getString("imeKorisnik")).thenReturn("Jovan", "Jovan", "Nikola", "Nikola");
        Mockito.when(rs.getString("prezimeKorisnik")).thenReturn("Jovanovic", "Jovanovic", "Nikolic", "Nikolic");
        Mockito.when(rs.getString("jmbg")).thenReturn("123", "123", "456", "456");

        Mockito.when(rs.getInt("idGrad")).thenReturn(1,1,2,2);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd", "Beograd", "Novi Sad", "Novi Sad");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000","11000","21000","21000");

        Mockito.when(rs.getInt("idPar")).thenReturn(1,2,3,4);
        Mockito.when(rs.getInt("redosled")).thenReturn(1,2,1,2);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("GG","NG","NERESENO","GG_VISE_OD_2_5");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5, 2.0, 1.8, 2.2);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1,2,3,4);
        Mockito.when(rs.getString("domacin")).thenReturn("Domacin1","Domacin2","Domacin3","Domacin4");
        Mockito.when(rs.getString("gost")).thenReturn("Gost1","Gost2","Gost3","Gost4");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<OpstiDomenskiObjekat> lista = tiket.konvertujResultSetUListu(rs);

        assertEquals(2, lista.size());
        
        Tiket t1 = (Tiket) lista.get(0);
        Tiket t2 = (Tiket) lista.get(1);
        
        assertEquals(2, t1.getParovi().size());
        assertEquals(2, t2.getParovi().size());
        
    }

    @Test
    public void testKonvertujResultSetUListuJedanTiket() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idTiket")).thenReturn(1);
        Mockito.when(rs.getDouble("ulog")).thenReturn(100.0);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(2.0);
        Mockito.when(rs.getDouble("dobitak")).thenReturn(200.0);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(0);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(1);
        Mockito.when(rs.getString("imeRadnik")).thenReturn("Marko");
        Mockito.when(rs.getString("prezimeRadnik")).thenReturn("Markovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m");
        Mockito.when(rs.getString("brojTelefona")).thenReturn("064111222");

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getString("imeKorisnik")).thenReturn("Jovan");
        Mockito.when(rs.getString("prezimeKorisnik")).thenReturn("Jovanovic");
        Mockito.when(rs.getString("jmbg")).thenReturn("123");

        Mockito.when(rs.getInt("idGrad")).thenReturn(1);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");

        Mockito.when(rs.getInt("idPar")).thenReturn(1);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("GG_VISE_OD_2_5");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("Domacin1");
        Mockito.when(rs.getString("gost")).thenReturn("Gost1");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()));

        List<OpstiDomenskiObjekat> lista = tiket.konvertujResultSetUListu(rs);

        assertEquals(1, lista.size());
        
        Tiket t1 = (Tiket) lista.get(0);
        
        assertEquals(1, t1.getParovi().size());
    }

    @Test
    public void testKonvertujResultSetUListuPrazanResultSet() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);

        List<OpstiDomenskiObjekat> lista = tiket.konvertujResultSetUListu(rs);

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testKonvertujResultSetUListu_SQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greška u čitanju"));

        assertThrows(SQLException.class, () -> tiket.konvertujResultSetUListu(rs));
    }
    
     @Test
    public void testKonvertujResultSetUObjekat_Validno() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);

        Mockito.when(rs.getInt("idTiket")).thenReturn(1);
        Mockito.when(rs.getDouble("ulog")).thenReturn(100.0);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(2.0);
        Mockito.when(rs.getDouble("dobitak")).thenReturn(200.0);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(0);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(1);
        Mockito.when(rs.getString("imeRadnik")).thenReturn("Marko");
        Mockito.when(rs.getString("prezimeRadnik")).thenReturn("Markovic");
        Mockito.when(rs.getString("korisnickoIme")).thenReturn("marko.m");

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getString("imeKorisnik")).thenReturn("Jovan");
        Mockito.when(rs.getString("prezimeKorisnik")).thenReturn("Jovanovic");
        Mockito.when(rs.getString("jmbg")).thenReturn("1234567890123");

        Mockito.when(rs.getInt("idGrad")).thenReturn(1);
        Mockito.when(rs.getString("naziv")).thenReturn("Beograd");
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("11000");

        Mockito.when(rs.getInt("idPar")).thenReturn(1);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("GG");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("Domacin1");
        Mockito.when(rs.getString("gost")).thenReturn("Gost1");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()));

        Tiket t = (Tiket) tiket.konvertujResultSetUObjekat(rs);

        assertNotNull(t);
        assertEquals(1, t.getIdTiket());
        assertEquals(100.0, t.getUlog());
        assertEquals(2.0, t.getUkupnaKvota());
        assertEquals(200.0, t.getMoguciDobitak());
        assertEquals(0, t.getStatus());
        assertEquals("Marko", t.getRadnik().getIme());
        assertEquals("Jovan", t.getKorisnik().getIme());
        assertEquals(1, t.getParovi().size());
        assertEquals("Domacin1", t.getParovi().get(0).getUtakmica().getDomacin());
    }

    @Test
    public void testKonvertujResultSetUObjekatSveNull() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, false);

        Mockito.when(rs.getInt("idTiket")).thenReturn(0);
        Mockito.when(rs.getDouble("ulog")).thenReturn(0.0);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(0.0);
        Mockito.when(rs.getDouble("dobitak")).thenReturn(0.0);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(-1);

        Mockito.when(rs.getInt("idRadnik")).thenReturn(0);
        Mockito.when(rs.getString("imeRadnik")).thenReturn(null);
        Mockito.when(rs.getString("prezimeRadnik")).thenReturn(null);
        Mockito.when(rs.getString("korisnickoIme")).thenReturn(null);

        Mockito.when(rs.getInt("idKorisnik")).thenReturn(0);
        Mockito.when(rs.getString("imeKorisnik")).thenReturn(null);
        Mockito.when(rs.getString("prezimeKorisnik")).thenReturn(null);
        Mockito.when(rs.getString("jmbg")).thenReturn(null);

        Mockito.when(rs.getInt("idGrad")).thenReturn(0);
        Mockito.when(rs.getString("naziv")).thenReturn(null);
        Mockito.when(rs.getString("postanskiBroj")).thenReturn("26202");

        Mockito.when(rs.getInt("idPar")).thenReturn(0);
        Mockito.when(rs.getInt("redosled")).thenReturn(0);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("GG");
        Mockito.when(rs.getDouble("kvota")).thenReturn(0.0);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(0);
        Mockito.when(rs.getString("domacin")).thenReturn(null);
        Mockito.when(rs.getString("gost")).thenReturn(null);
        Mockito.when(rs.getTimestamp("termin")).thenReturn(null);

        Tiket t = (Tiket) tiket.konvertujResultSetUObjekat(rs);

        assertNotNull(t);
        assertEquals(0, t.getIdTiket());
        assertNull(t.getRadnik().getIme());
        assertNull(t.getKorisnik().getIme());
        assertEquals(1, t.getParovi().size());
        assertNull(t.getParovi().get(0).getUtakmica().getDomacin());
    }

    @Test
    public void testKonvertujResultSetUObjekat_SQLException() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greška u čitanju"));

        assertThrows(SQLException.class, () -> tiket.konvertujResultSetUObjekat(rs));
    }
    
    // ----- OSTALO -----
    
    @Test
    public void testGetNazivTabele() {
        assertEquals("tiket", tiket.getNazivTabele());
    }

    @Test
    public void testAlies() {
        assertEquals("t", tiket.alies());
    }

    @Test
    public void testGetJoinUslov() {
        String join = tiket.getJoinUslov();
        assertTrue(join.contains("JOIN radnik r ON t.idRadnik = r.idRadnik"));
        assertTrue(join.contains("JOIN korisnik k ON t.idKorisnik = k.idKorisnik"));
    }
    
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ulog, statusTiket, ukupnaKvota, dobitak, idRadnik, idKorisnik", tiket.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        tiket.setIdTiket(5);
        assertEquals("t.idTiket = 5", tiket.requeredUslov());
    }
    
    
}
