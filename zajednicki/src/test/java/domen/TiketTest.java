package domen;

import domen.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit testovi za klasu {@link Tiket}.
 * Testira osnovnu funkcionalnost, settere, konstruktore,
 * dodavanje parova, metode {@link Object#toString()} i implementaciju {@link OpstiDomenskiObjekat}.
 */
public class TiketTest {

    private Tiket tiket;
    private Radnik radnik;
    private Korisnik korisnik;
    private Par par;
    private Utakmica utakmica;

    /**
     * Inicijalizacija objekata pre svakog testa.
     */
    @BeforeEach
    void setUp() {
        tiket = new Tiket();
        radnik = new Radnik();
        radnik.setIdRadnik(1);
        korisnik = new Korisnik();
        korisnik.setIdKorisnik(1);
        utakmica = new Utakmica();
        utakmica.setIdUtakmica(1);
        utakmica.setDomacin("Tim A");
        utakmica.setGost("Tim B");
        utakmica.setTermin(new Date());
        par = new Par();
        par.setUtakmica(utakmica);
    }

    /**
     * Oslobađanje resursa nakon svakog testa.
     */
    @AfterEach
    void tearDown() {
        tiket = null;
        radnik = null;
        korisnik = null;
        par = null;
        utakmica = null;
    }

    // =========================
    // Osnovna funkcionalnost
    // =========================

    /**
     * Testira default konstruktor i proverava da li je lista parova inicijalizovana.
     */
    @Test
    void testKonstruktor() {
        assertNotNull(tiket.getParovi());
        assertTrue(tiket.getParovi().isEmpty());
    }

    /**
     * Testira pun konstruktor sa svim parametrima.
     */
    @Test
    void testFullKonstruktor() {
        List<Par> parovi = new ArrayList<>();
        parovi.add(par);
        Tiket t = new Tiket(1, 100, 2.5, 250, 0, radnik, korisnik, parovi);
        assertEquals(1, t.getIdTiket());
        assertEquals(100, t.getUlog());
        assertEquals(2.5, t.getUkupnaKvota());
        assertEquals(250, t.getMoguciDobitak());
        assertEquals(0, t.getStatus());
        assertEquals(radnik, t.getRadnik());
        assertEquals(korisnik, t.getKorisnik());
        assertEquals(1, t.getParovi().size());
    }

    /**
     * Testira settere i gettere za sva polja tiketa.
     */
    @Test
    void testSetteri() {
        tiket.setIdTiket(5);
        tiket.setUlog(200);
        tiket.setUkupnaKvota(3.5);
        tiket.setMoguciDobitak(700);
        tiket.setStatus(1);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);
        tiket.setParovi(new ArrayList<>());

        assertEquals(5, tiket.getIdTiket());
        assertEquals(200, tiket.getUlog());
        assertEquals(3.5, tiket.getUkupnaKvota());
        assertEquals(700, tiket.getMoguciDobitak());
        assertEquals(1, tiket.getStatus());
        assertEquals(radnik, tiket.getRadnik());
        assertEquals(korisnik, tiket.getKorisnik());
        assertNotNull(tiket.getParovi());
        assertTrue(tiket.getParovi().isEmpty());
    }

    /**
     * Testira dodavanje parova u tiket.
     */
    @Test
    void testDodajPar() {
        tiket.dodajPar(par);
        assertEquals(1, tiket.getParovi().size());
        assertEquals(par, tiket.getParovi().get(0));
    }

    /**
     * Testira ispravno formiranje stringa preko {@link Tiket#toString()}.
     */
    @Test
    void testToString() {
        tiket.setIdTiket(1);
        tiket.setUlog(10.0);
        tiket.setUkupnaKvota(2.0);
        tiket.setMoguciDobitak(20.0);
        tiket.setStatus(1); // Dobitni
        radnik.setIme("Test");
        radnik.setPrezime("Radnik");
        korisnik.setIme("Test");
        korisnik.setPrezime("Korisnik");
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);

        par.setIdPar(10);
        par.setTipOpklade(TipOpklade.ILI_X2);
        par.setKvota(2.0);
        Utakmica u = new Utakmica();
        u.setDomacin("Partizan");
        u.setGost("Zvezda");
        u.setTermin(new Date(1672531200000L)); // 2023-01-01
        par.setUtakmica(u);
        tiket.dodajPar(par);

        String ocekivano = "Tiket ID: 1\n" +
                "Ulog: 10.0\n" +
                "Ukupna kvota: 2.0\n" +
                "Mogući dobitak: 20.0\n" +
                "Status: Dobitni\n" +
                "Radnik: Test Radnik\n" +
                "Korisnik: Test Korisnik\n" +
                "Parovi:\n" +
                "  Par ID: 10 | Tip opklade: ILI_X2 | Kvota: 2.0 | Utakmica: Partizan vs Zvezda (Sun Jan 01 01:00:00 CET 2023)";

        assertEquals(ocekivano, tiket.toString());
    }

    // =========================
    // OpstiDomenskiObjekat
    // =========================

    /**
     * Testira naziv tabele za SQL upite.
     */
    @Test
    void testGetNazivTabele() {
        assertEquals("tiket", tiket.getNazivTabele());
    }

    /**
     * Testira alias za SQL upite.
     */
    @Test
    void testAlies() {
        assertEquals("t", tiket.alies());
    }

    /**
     * Testira JOIN uslove za povezane tabele.
     */
    @Test
    void testGetJoinUslov() {
        String ocekivano = " JOIN radnik r ON t.idRadnik = r.idRadnik"
                + " JOIN korisnik k ON t.idKorisnik = k.idKorisnik"
                + " JOIN grad g ON k.idGrad = g.idGrad"
                + " JOIN par p ON t.idTiket = p.idTiket"
                + " JOIN utakmica u ON p.idUtakmica = u.idUtakmica";
        assertEquals(ocekivano, tiket.getJoinUslov());
    }

    /**
     * Testira kolone za INSERT SQL upit.
     */
    @Test
    void testGetKoloneZaInsert() {
        assertEquals("ulog, statusTiket, ukupnaKvota, dobitak, idRadnik, idKorisnik", tiket.getKoloneZaInsert());
    }

    /**
     * Testira vrednosti za INSERT SQL upit.
     */
    @Test
    void testGetVrednostiZaInsert() {
        tiket.setUlog(10);
        tiket.setStatus(0);
        tiket.setUkupnaKvota(2.5);
        tiket.setMoguciDobitak(25);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);
        String ocekivano = "10.0, 0, 2.5, 25.0, 1, 1";
        assertEquals(ocekivano, tiket.getVrednostiZaInsert());
    }

    /**
     * Testira vrednosti za UPDATE SQL upit kada se menja samo status.
     */
    @Test
    void testGetVrednostiZaUpdate_SamoStatus() {
        tiket.setStatus(2);
        String ocekivano = "statusTiket = 2";
        assertEquals(ocekivano, tiket.getVrednostiZaUpdate());
    }

    /**
     * Testira vrednosti za UPDATE SQL upit kada se menjaju sva polja.
     */
    @Test
    void testGetVrednostiZaUpdate_Sve() {
        tiket.setStatus(1);
        tiket.setUlog(10);
        tiket.setRadnik(radnik);
        tiket.setKorisnik(korisnik);
        tiket.setUkupnaKvota(3.0);
        tiket.setMoguciDobitak(30);
        String ocekivano = "statusTiket = 1, ulog = 10.0, idRadnik = 1, idKorisnik = 1, ukupnaKvota = 3.0, dobitak = 30.0";
        assertEquals(ocekivano, tiket.getVrednostiZaUpdate());
    }

    /**
     * Testira uslov po primarnom ključu.
     */
    @Test
    void testRequeredUslov() {
        tiket.setIdTiket(1);
        assertEquals("t.idTiket = 1", tiket.requeredUslov());
    }

    /**
     * Parametrizovan test za pretragu po različitim kriterijumima.
     */
    @ParameterizedTest
    @CsvSource({
            "1, 1, 0, 0, 0, 0, -1, t.idRadnik = 1 AND t.idKorisnik = 1",
            "1, 0, 10, 0, 0, 0, -1, t.idRadnik = 1 AND u.idUtakmica = 10",
            "0, 0, 0, 50, 0, 0, -1, t.ulog > 50.0",
            "0, 0, 0, 0, 3.5, 0, -1, t.ukupnaKvota > 3.5",
            "0, 0, 0, 0, 0, 1000, -1, t.dobitak > 1000.0",
            "0, 0, 0, 0, 0, 0, 1, t.statusTiket = '1'"
    })
    void testGetUslovZaPretragu(int radnikId, int korisnikId, int utakmicaId, double ulog, double kvota, double dobitak, int status, String ocekivaniUslov) {
        if(radnikId > 0) tiket.setRadnik(new Radnik());
        if(korisnikId > 0) tiket.setKorisnik(new Korisnik());
        if(utakmicaId > 0) {
            Utakmica u = new Utakmica();
            u.setIdUtakmica(utakmicaId);
            Par p = new Par();
            p.setUtakmica(u);
            tiket.dodajPar(p);
        }
        tiket.setUlog(ulog);
        tiket.setUkupnaKvota(kvota);
        tiket.setMoguciDobitak(dobitak);
        tiket.setStatus(status);

        if (radnikId > 0) tiket.getRadnik().setIdRadnik(radnikId);
        if (korisnikId > 0) tiket.getKorisnik().setIdKorisnik(korisnikId);

        assertEquals(ocekivaniUslov, tiket.getUslovZaPretragu());
    }

    /**
     * Testira getUslov() metodu koja vraća sve kolone sa aliasima za SELECT upit.
     */
    @Test
    void testGetUslov() {
        String ocekivano = "t.*,\n" +
                "    r.idRadnik AS idRadnik, r.ime AS imeRadnik, r.prezime AS prezimeRadnik, r.korisnickoIme AS korisnickoIme, r.brojTelefona AS brojTelefona, \n" +
                "    k.idKorisnik AS idKorisnik, k.ime AS imeKorisnik, k.prezime AS prezimeKorisnik, k.jmbg AS jmbg,\n" +
                "    p.idPar AS idPar, p.redosled AS redosled, p.tipOpklade AS tipOpklade, p.kvota AS kvota, \n" +
                "    g.idGrad AS idGrad, g.naziv AS naziv, g.postanskiBroj AS postanskiBroj, \n" +
                "    u.idUtakmica AS idUtakmica, u.domacin AS domacin, u.gost AS gost, u.termin AS termin";

        assertEquals(ocekivano, tiket.getUslov());
    }

    /**
     * Testira konverziju ResultSet-a u listu tiketa.
     */
    @Test
    void testKonvertujResultSetUListu() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("idTiket")).thenReturn(1,1);
        Mockito.when(rs.getInt("ulog")).thenReturn(100,100);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(2.5,2.5);
        Mockito.when(rs.getInt("dobitak")).thenReturn(250,250);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(0,0);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(1,1);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1,1);
        Mockito.when(rs.getInt("idPar")).thenReturn(1,2);
        Mockito.when(rs.getInt("redosled")).thenReturn(1,2);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("ILI_X2","ILI_X2");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5,2.0);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1,2);
        Mockito.when(rs.getString("domacin")).thenReturn("A","B");
        Mockito.when(rs.getString("gost")).thenReturn("C","D");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));

        List<OpstiDomenskiObjekat> lista = tiket.konvertujResultSetUListu(rs);
        assertEquals(1, lista.size());
        assertEquals(2, ((Tiket)lista.get(0)).getParovi().size());
    }

    /**
     * Testira konverziju ResultSet-a u jedan tiket objekat.
     */
    @Test
    void testKonvertujResultSetUObjekat() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true,false);
        Mockito.when(rs.getInt("idTiket")).thenReturn(1);
        Mockito.when(rs.getInt("ulog")).thenReturn(100);
        Mockito.when(rs.getDouble("ukupnaKvota")).thenReturn(2.5);
        Mockito.when(rs.getInt("dobitak")).thenReturn(250);
        Mockito.when(rs.getInt("statusTiket")).thenReturn(0);
        Mockito.when(rs.getInt("idRadnik")).thenReturn(1);
        Mockito.when(rs.getInt("idKorisnik")).thenReturn(1);
        Mockito.when(rs.getInt("idPar")).thenReturn(1);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("ILI_X2");
        Mockito.when(rs.getDouble("kvota")).thenReturn(1.5);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(1);
        Mockito.when(rs.getString("domacin")).thenReturn("A");
        Mockito.when(rs.getString("gost")).thenReturn("B");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(new Date().getTime()));

        Tiket t = (Tiket) tiket.konvertujResultSetUObjekat(rs);
        assertEquals(1, t.getParovi().size());
    }
}
