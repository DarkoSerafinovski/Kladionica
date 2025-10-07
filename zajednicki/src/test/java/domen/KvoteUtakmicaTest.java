package domen;

import domen.KvoteUtakmica;
import domen.TipOpklade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class KvoteUtakmicaTest {

    private KvoteUtakmica kvoteUtakmica;

    @BeforeEach
    void setUp() {
        kvoteUtakmica = new KvoteUtakmica();
    }
    
    // ----- KONSTRUKTOR -----
    
    @Test
    void testKonstruktorNeVracaNull(){
        assertNotNull(kvoteUtakmica, "Objekat kvoteUtakmica ne sme biti null");
    }
    
    @Test
    void testKvoteZaPostojecuUtakmicuNisuNull(){
        Map<TipOpklade, Double> kvota = kvoteUtakmica.getKvoteZaUtakmicu(1);
        assertNotNull(kvota, "Kvote za utakmicu 1 ne smeju biti null");
    }
    @Test
    void testBrojTipovaZaUtakmicu(){
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(1);
        assertEquals(10, kvote.size(), "Utakmica 1 mora imati 10 kvota / tipova opklade");
    }
    
    @Test
    void testTacneVrednostiKvoteUtakmice1() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(1);

        assertEquals(1.5, kvote.get(TipOpklade.POBEDA_DOMACIN), 0.001);
        assertEquals(2.8, kvote.get(TipOpklade.POBEDA_GOST), 0.001);
        assertEquals(3.2, kvote.get(TipOpklade.NERESENO), 0.001);
        assertEquals(1.8, kvote.get(TipOpklade.ILI_1X), 0.001);
        assertEquals(2.1, kvote.get(TipOpklade.ILI_X2), 0.001);
        assertEquals(1.6, kvote.get(TipOpklade.ILI_12), 0.001);
        assertEquals(1.9, kvote.get(TipOpklade.GG), 0.001);
        assertEquals(1.85, kvote.get(TipOpklade.NG), 0.001);
        assertEquals(2.0, kvote.get(TipOpklade.VISE_OD_2_5), 0.001);
        assertEquals(2.3, kvote.get(TipOpklade.GG_VISE_OD_2_5), 0.001);
    }

    @Test
    void testNepostojecaUtakmicaVracaNull() {
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(25);
        assertNull(kvote, "Nepostojeća utakmica treba da vrati null");
    }
    
    // ----- DODAVANJE KVOTE -----
    
    @Test
    void testDodavanjeKvoteZaNovuUtakmicu(){
        kvoteUtakmica.dodajKvote(21, TipOpklade.GG, 2.5);
        
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(21);
        assertNotNull(kvote, "Kvote za utakmicu ne smeju biti null");
        assertEquals(1, kvote.size(), "Treba da postoji samo jedna kvota u novoj utakmici");
        assertEquals(2.5, kvote.get(TipOpklade.GG), 0.001);
    }
    
    @Test
    void testAzuriranjeKvote(){
        kvoteUtakmica.dodajKvote(1, TipOpklade.GG, 2.1);
        Map<TipOpklade, Double> kvote = kvoteUtakmica.getKvoteZaUtakmicu(1);
        assertEquals(2.1, kvote.get(TipOpklade.GG), 0.001);
    }
    
    @Test
    void testDodavanjeKvoteSaNevazecimId() {
        assertThrows(IllegalArgumentException.class, () -> {
            kvoteUtakmica.dodajKvote(0, TipOpklade.GG, 2.0);
        });
    }

    @Test
    void testDodavanjeKvoteSaNullTipom() {
        assertThrows(IllegalArgumentException.class, () -> {
            kvoteUtakmica.dodajKvote(1, null, 2.0);
        });
    }

    @Test
    void testDodavanjeKvoteSaNevalidnomVrednoscu() {
        assertThrows(IllegalArgumentException.class, () -> {
            kvoteUtakmica.dodajKvote(1, TipOpklade.POBEDA_DOMACIN, 1.0);
        });
    }
}