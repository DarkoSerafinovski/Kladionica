package domen;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParTest {

    private Par par;
    private Utakmica utakmica;

    @BeforeEach
    void setUp() {
        utakmica = new Utakmica();
        utakmica.setIdUtakmica(1);
        utakmica.setDomacin("Domacin FC");
        utakmica.setGost("Gost FC");
        utakmica.setTermin(new Date());

        par = new Par(10, 100, utakmica, TipOpklade.POBEDA_DOMACIN, 2.5, 1);
    }
    
    // ----- KONSTRUKTORI -----
    @Test
    void testParametarskiKonstruktor_IspravanUnos() {
        Utakmica utakmica = new Utakmica(1, "Partizan", "Crvena Zvezda", new Date());
        TipOpklade tip = TipOpklade.POBEDA_DOMACIN;

        Par par = new Par(10, 5, utakmica, tip, 2.35, 1);

        assertEquals(10, par.getIdPar());
        assertEquals(5, par.getIdTiket());
        assertEquals(utakmica, par.getUtakmica());
        assertEquals(tip, par.getTipOpklade());
        assertEquals(2.35, par.getKvota());
        assertEquals(1, par.getRedosled());
    }

    @Test
    void testParametarskiKonstruktor_NegativneVrednosti() {
        Utakmica utakmica = new Utakmica(2, "Barcelona", "Real Madrid", new java.util.Date());
        TipOpklade tip = TipOpklade.NERESENO;

        Par par = new Par(-1, -5, utakmica, tip, -3.0, -2);

        assertEquals(-1, par.getIdPar());
        assertEquals(-5, par.getIdTiket());
        assertEquals(-3.0, par.getKvota());
        assertEquals(-2, par.getRedosled());
    }

    @Test
    void testParametarskiKonstruktor_NullVrednosti() {
        Par par = new Par(1, 2, null, null, 0.0, 3);

        assertNull(par.getUtakmica());
        assertNull(par.getTipOpklade());
        assertEquals(0.0, par.getKvota());
    }

    // ----- SETTERI -----
    
    // ----- SETIDPAR -----
    
    @Test
    void testSetIdPar_NormalnaVrednost() {
        Par par = new Par();
        par.setIdPar(5);
        assertEquals(5, par.getIdPar());
    }

    @Test
    void testSetIdPar_PromenjenaVrednost() {
        Par par = new Par();
        par.setIdPar(1);
        par.setIdPar(9);
        assertEquals(9, par.getIdPar());
    }
    

    // ----- SETIDTIKET -----
    
    @Test
    void testSetIdTiket_NormalnaVrednost() {
        Par par = new Par();
        par.setIdTiket(50);
        assertEquals(50, par.getIdTiket());
    }

    @Test
    void testSetIdTiket_PromenjenaVrednost() {
        Par par = new Par();
        par.setIdTiket(20);
        par.setIdTiket(25);
        assertEquals(25, par.getIdTiket());
    }
    
    // ----- SETUTAKMICA -----
    
     @Test
    void testSetUtakmica_NormalnaVrednost() {
        Par par = new Par();
        Utakmica utakmica = new Utakmica(1, "Chelsea", "Liverpool", new Date());
        par.setUtakmica(utakmica);
        assertEquals(utakmica, par.getUtakmica());
    }

    @Test
    void testSetUtakmica_NullVrednost() {
        Par par = new Par();
        par.setUtakmica(null);
        assertNull(par.getUtakmica());
    }

    @Test
    void testSetUtakmica_PromenjenaVrednost() {
        Par par = new Par();
        Utakmica u1 = new Utakmica(1, "Inter", "Milan", new Date());
        Utakmica u2 = new Utakmica(2, "Napoli", "Roma", new Date());
        par.setUtakmica(u1);
        par.setUtakmica(u2);
        assertEquals(u2, par.getUtakmica());
    }
    
    // ----- setTipOpklade -----
    
     @Test
    void testSetTipOpklade_NormalnaVrednost() {
        Par par = new Par();
        par.setTipOpklade(TipOpklade.GG);
        assertEquals(TipOpklade.GG, par.getTipOpklade());
    }

    @Test
    void testSetTipOpklade_NullVrednost() {
        Par par = new Par();
        par.setTipOpklade(null);
        assertNull(par.getTipOpklade());
    }

    @Test
    void testSetTipOpklade_PromenjenaVrednost() {
        Par par = new Par();
        par.setTipOpklade(TipOpklade.POBEDA_DOMACIN);
        par.setTipOpklade(TipOpklade.NERESENO);
        assertEquals(TipOpklade.NERESENO, par.getTipOpklade());
    }

    // ----- setKvota -----
    
    @Test
    void testSetKvota_NormalnaVrednost() {
        Par par = new Par();
        par.setKvota(2.5);
        assertEquals(2.5, par.getKvota());
    }

    @Test
    void testSetKvota_PromenjenaVrednost() {
        Par par = new Par();
        par.setKvota(1.8);
        par.setKvota(2.3);
        assertEquals(2.3, par.getKvota());
    }
    
    // ----- setRedosled -----
    
    @Test
    void testSetRedosled_NormalnaVrednost() {
        Par par = new Par();
        par.setRedosled(3);
        assertEquals(3, par.getRedosled());
    }

    @Test
    void testSetRedosled_PromenjenaVrednost() {
        Par par = new Par();
        par.setRedosled(1);
        par.setRedosled(2);
        assertEquals(2, par.getRedosled());
    }
    
    // ----- toString -----
    
     @Test
    void testToString_SviAtributiPopunjeni() {
        Utakmica utakmica = new Utakmica(1, "Partizan", "Zvezda", new Date());
        Par par = new Par(10, 20, utakmica, TipOpklade.POBEDA_DOMACIN, 2.3, 1);

        String rezultat = par.toString();

        assertTrue(rezultat.contains("idPar=10"));
        assertTrue(rezultat.contains("idTiket=20"));
        assertTrue(rezultat.contains("utakmica=Partizan-Zvezda"));
        assertTrue(rezultat.contains("tipOpklade='POBEDA_DOMACIN'"));
        assertTrue(rezultat.contains("kvota=2.3"));
        assertTrue(rezultat.contains("redosled=1"));
    }

    @Test
    void testToString_NullVrednosti() {
        Par par = new Par(5, 7, null, null, 1.9, 3);

        String rezultat = par.toString();

        assertTrue(rezultat.contains("idPar=5"));
        assertTrue(rezultat.contains("idTiket=7"));
        assertTrue(rezultat.contains("utakmica=null"));
        assertTrue(rezultat.contains("tipOpklade='null'"));
        assertTrue(rezultat.contains("kvota=1.9"));
        assertTrue(rezultat.contains("redosled=3"));
    }

    @Test
    void testToString_PoslePromeneAtributa() {
        Par par = new Par();
        par.setIdPar(1);
        par.setIdTiket(2);
        par.setKvota(3.0);
        par.setRedosled(4);

        String rezultat = par.toString();
        assertTrue(rezultat.contains("idPar=1"));
        assertTrue(rezultat.contains("idTiket=2"));
        assertTrue(rezultat.contains("kvota=3.0"));
        assertTrue(rezultat.contains("redosled=4"));
    }

    // ----- Testiranje SQL metoda -----
    @Test
    void testGetVrednostiZaInsert() {
        String expected = "100, 1, 'POBEDA_DOMACIN', 2.5, 1";
        assertEquals(expected, par.getVrednostiZaInsert());
    }

    @Test
    void testGetVrednostiZaUpdate() {
        String expected = "tipOpklade = 'POBEDA_DOMACIN', kvota = 2.5, redosled = 1";
        assertEquals(expected, par.getVrednostiZaUpdate());
    }
    
    @Test
    void testGetUslovZaPretragu(){
        assertEquals("p.idTiket = 100", par.getUslovZaPretragu());
    }
    
    // ----- RESULT SET -----

    @Test
    void testResultSetUListuDvaResultSeta() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true,true,false);
        Mockito.when(rs.getInt("idPar")).thenReturn(1,2);
        Mockito.when(rs.getInt("idTiket")).thenReturn(1,1);
        Mockito.when(rs.getInt("redosled")).thenReturn(1,2);
        Mockito.when(rs.getDouble("kvota")).thenReturn(2.3, 5.4);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(100, 200);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan", "Real Madrid");
        Mockito.when(rs.getString("gost")).thenReturn("Zvezda", "Barselona");
        Mockito.when(rs.getDate("termin")).thenReturn(
            new java.sql.Date(System.currentTimeMillis()),
            new java.sql.Date(System.currentTimeMillis() + 100000)
        );
        Mockito.when(rs.getString("tipOpklade")).thenReturn("POBEDA_DOMACIN", "NERESENO");
        
        ArrayList<OpstiDomenskiObjekat> lista = par.konvertujResultSetUListu(rs);
        assertEquals(2, lista.size());
    }
    
    @Test 
    void testResultSetUListuJedanResultSet() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(true, false);
        Mockito.when(rs.getInt("idPar")).thenReturn(1);
        Mockito.when(rs.getInt("idTiket")).thenReturn(1);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);
        Mockito.when(rs.getDouble("kvota")).thenReturn(2.5);
        Mockito.when(rs.getInt("idUtakmica")).thenReturn(100);
        Mockito.when(rs.getString("domacin")).thenReturn("Liverpul");
        Mockito.when(rs.getString("gost")).thenReturn("Mancester Siti");
        Mockito.when(rs.getDate("termin")).thenReturn(
            new java.sql.Date(System.currentTimeMillis())
        );
        Mockito.when(rs.getString("tipOpklade")).thenReturn("GG");
        
        ArrayList<OpstiDomenskiObjekat> lista = par.konvertujResultSetUListu(rs);
        assertEquals(1, lista.size());
    }
    
    @Test
    void testResultSetUListuPrazanResultSet() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenReturn(false);
        ArrayList<OpstiDomenskiObjekat> lista = par.konvertujResultSetUListu(rs);
        assertNotNull(lista);
        assertEquals(0, lista.size());
    }
    
    @Test
    public void testResultSetUListuBacaSQLException() throws SQLException{
        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.next()).thenThrow(new SQLException("Greska u ResultSet-u"));
        
        assertThrows(SQLException.class, () -> {
            par.konvertujResultSetUListu(rs);
        });
    }
    
    @Test
    void testKonvertujResultSetUObjekat_SveVrednostiValidne() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(10);
        Mockito.when(rs.getString("domacin")).thenReturn("Partizan");
        Mockito.when(rs.getString("gost")).thenReturn("Zvezda");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(new Timestamp(System.currentTimeMillis()));
        Mockito.when(rs.getInt("idPar")).thenReturn(1);
        Mockito.when(rs.getInt("idTiket")).thenReturn(5);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("POBEDA_DOMACIN");
        Mockito.when(rs.getDouble("kvota")).thenReturn(2.5);
        Mockito.when(rs.getInt("redosled")).thenReturn(3);

        Par par = (Par) new Par().konvertujResultSetUObjekat(rs);

        assertEquals(1, par.getIdPar());
        assertEquals(5, par.getIdTiket());
        assertEquals(10, par.getUtakmica().getIdUtakmica());
        assertEquals("Partizan", par.getUtakmica().getDomacin());
        assertEquals("Zvezda", par.getUtakmica().getGost());
        assertEquals(TipOpklade.POBEDA_DOMACIN, par.getTipOpklade());
        assertEquals(2.5, par.getKvota());
        assertEquals(3, par.getRedosled());
    }

    @Test
    void testKonvertujResultSetUObjekat_NekeVrednostiNull() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt("idUtakmica")).thenReturn(0);
        Mockito.when(rs.getString("domacin")).thenReturn(null);
        Mockito.when(rs.getString("gost")).thenReturn("Barselona");
        Mockito.when(rs.getTimestamp("termin")).thenReturn(null);
        Mockito.when(rs.getInt("idPar")).thenReturn(2);
        Mockito.when(rs.getInt("idTiket")).thenReturn(7);
        Mockito.when(rs.getString("tipOpklade")).thenReturn("NERESENO");
        Mockito.when(rs.getDouble("kvota")).thenReturn(0.0);
        Mockito.when(rs.getInt("redosled")).thenReturn(1);

        Par par = (Par) new Par().konvertujResultSetUObjekat(rs);

        assertEquals(2, par.getIdPar());
        assertEquals(7, par.getIdTiket());
        assertEquals(0, par.getUtakmica().getIdUtakmica());
        assertNull(par.getUtakmica().getDomacin());
        assertEquals("Barselona", par.getUtakmica().getGost());
        assertNull(par.getUtakmica().getTermin());
        assertEquals(TipOpklade.NERESENO, par.getTipOpklade());
        assertEquals(0.0, par.getKvota());
    }

    @Test
    void testKonvertujResultSetUObjekat_SveNullVrednosti() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.getInt(Mockito.anyString())).thenReturn(0);
        Mockito.when(rs.getString(Mockito.anyString())).thenReturn(null);
        Mockito.when(rs.getTimestamp(Mockito.anyString())).thenReturn(null);
        Mockito.when(rs.getDouble(Mockito.anyString())).thenReturn(0.0);

        Par par = (Par) new Par().konvertujResultSetUObjekat(rs);

        assertEquals(0, par.getIdPar());
        assertEquals(0, par.getIdTiket());
        assertNotNull(par.getUtakmica(), "Utakmica objekat ne sme biti null");
        assertNull(par.getUtakmica().getDomacin());
        assertNull(par.getUtakmica().getGost());
        assertNull(par.getUtakmica().getTermin());
        assertNull(par.getTipOpklade(), "Tip opklade treba da bude null");
        assertEquals(0.0, par.getKvota());
    }

    // ----- OSTALO -----
    
    @Test
    void testGetNazivTabele(){
        assertEquals("par", par.getNazivTabele());
    }
    
    @Test
    void testAlies() {
        assertEquals("p", par.alies());
    }

    @Test
    void testGetJoinUslov() {
        assertEquals(" JOIN tiket t ON p.idTiket = t.tiketID JOIN utakmica u ON p.idUtakmica = u.utakmicaID", par.getJoinUslov());
    }

    @Test
    void testGetKoloneZaInsert() {
        assertEquals("idTiket, idUtakmica, tipOpklade, kvota, redosled", par.getKoloneZaInsert());
    }

    @Test
    void testRequeredUslov() {
        par.setIdPar(5);
        assertEquals("p.idPar = 5", par.requeredUslov());
    }
}
