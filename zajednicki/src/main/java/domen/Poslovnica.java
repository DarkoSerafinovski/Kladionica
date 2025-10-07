package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa predstavlja poslovnicu u okviru sistema. 
 * Poslovnica sadrzi podatke o identifikatoru, adresi i broju telefona. 
 * Koristi se za mapiranje poslovnice iz baze podataka i omogucava rad sa SQL upitima.
 * 
 * @author Darko
 */
public class Poslovnica implements Serializable, OpstiDomenskiObjekat {

    private int idPoslovnica;
    private String adresa;
    private String brojTelefona;

    /**
     * Prazan konstruktor koji kreira novi objekat klase Poslovnica.
     */
    public Poslovnica() {
    }

    /**
     * Parametrizovani konstruktor koji inicijalizuje sve atribute poslovnice.
     * 
     * @param idPoslovnica jedinstveni identifikator poslovnice
     * @param adresa adresa poslovnice
     * @param brojTelefona broj telefona poslovnice
     */
    public Poslovnica(int idPoslovnica, String adresa, String brojTelefona) {
        setIdPoslovnica(idPoslovnica);
        setAdresa(adresa);
        setBrojTelefona(brojTelefona);
    }

    /**
     * Vraca ID poslovnice.
     * 
     * @return identifikator poslovnice
     */
    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    /**
     * Postavlja ID poslovnice.
     * 
     * @param idPoslovnica nova vrednost identifikatora poslovnice
     */
    public void setIdPoslovnica(int idPoslovnica) {
        this.idPoslovnica = idPoslovnica;
    }

    /**
     * Vraca adresu poslovnice.
     * 
     * @return adresa poslovnice
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Postavlja adresu poslovnice.
     * 
     * @param adresa nova adresa poslovnice
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * Vraca broj telefona poslovnice.
     * 
     * @return broj telefona poslovnice
     */
    public String getBrojTelefona() {
        return brojTelefona;
    }

    /**
     * Postavlja broj telefona poslovnice.
     * 
     * @param brojTelefona novi broj telefona poslovnice
     */
    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    /**
     * Vraca string reprezentaciju poslovnice, tj. samo adresu.
     * 
     * @return adresa poslovnice
     */
    @Override
    public String toString() {
        return adresa;
    }

    /**
     * Vraca naziv tabele u bazi podataka koja odgovara ovoj klasi.
     * 
     * @return naziv tabele "poslovnica"
     */
    @Override
    public String getNazivTabele() {
        return "poslovnica";
    }

    /**
     * Vraca alias koji se koristi u SQL upitima za tabelu poslovnica.
     * 
     * @return alias "p"
     */
    @Override
    public String alies() {
        return "p";
    }

    /**
     * Vraca uslov za spajanje sa drugim tabelama. 
     * Posto poslovnica nije povezana direktno sa drugim tabelama, vraca prazan string.
     * 
     * @return prazan string
     */
    @Override
    public String getJoinUslov() {
        return "";
    }

    /**
     * Vraca imena kolona koje se koriste prilikom INSERT upita.
     * 
     * @return string "adresa, brojTelefona"
     */
    @Override
    public String getKoloneZaInsert() {
        return "adresa, brojTelefona";
    }

    /**
     * Vraca vrednosti koje ce biti unete u tabelu prilikom INSERT upita.
     * 
     * @return string sa vrednostima formata 'adresa', 'brojTelefona'
     */
    @Override
    public String getVrednostiZaInsert() {
        return "'" + adresa + "', '" + brojTelefona + "'";
    }

    /**
     * Vraca string koji predstavlja deo SQL upita za azuriranje podataka (UPDATE).
     * 
     * @return string u formatu "adresa = '...', brojTelefona = '...'"
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "adresa = '" + adresa + "', brojTelefona = '" + brojTelefona + "'";
    }

    /**
     * Vraca osnovni uslov koji se koristi u SQL upitima za pronalazenje konkretne poslovnice.
     * 
     * @return string u formatu "p.idPoslovnica = [idPoslovnica]"
     */
    @Override
    public String requeredUslov() {
        return "p.idPoslovnica = " + idPoslovnica;
    }

    /**
     * Vraca dinamicki uslov za pretragu poslovnica prema adresi i/ili broju telefona.
     * Ako su oba polja prazna, vraca prazan string.
     * 
     * @return SQL uslov za pretragu u formatu "p.adresa LIKE ... AND p.brojTelefona LIKE ..."
     */
    @Override
    public String getUslovZaPretragu() {
        StringBuilder uslov = new StringBuilder();
        boolean prviUslov = true;

        if (adresa != null && !adresa.isEmpty()) {
            uslov.append("p.adresa LIKE '%").append(adresa).append("%'");
            prviUslov = false;
        }

        if (brojTelefona != null && !brojTelefona.isEmpty()) {
            if (!prviUslov) {
                uslov.append(" AND ");
            }
            uslov.append("p.brojTelefona LIKE '%").append(brojTelefona).append("%'");
        }

        return uslov.toString();
    }

    /**
     * Vraca naziv kolona koje ce biti izabrane u SELECT upitu.
     * Ovde se vracaju sve kolone (zvezdica).
     * 
     * @return string "*"
     */
    @Override
    public String getUslov() {
        return "*";
    }

    /**
     * Konvertuje ResultSet objekat dobijen iz baze u listu poslovnica.
     * 
     * @param rs rezultat SQL upita
     * @return lista objekata tipa Poslovnica
     * @throws SQLException ako dodje do greske prilikom citanja iz baze
     */
    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Poslovnica poslovnica = new Poslovnica();
            poslovnica.setIdPoslovnica(rs.getInt("idPoslovnica"));
            poslovnica.setAdresa(rs.getString("adresa"));
            poslovnica.setBrojTelefona(rs.getString("brojTelefona"));
            lista.add(poslovnica);
        }
        return lista;
    }

    /**
     * Metoda koja bi trebalo da konvertuje jedan red iz ResultSet-a u objekat Poslovnica.
     * Trenutno nije implementirana i baca izuzetak.
     * 
     * @param rs rezultat SQL upita
     * @return objekat tipa Poslovnica
     * @throws SQLException ako dodje do greske u citanju podataka iz baze
     * @throws UnsupportedOperationException ako metoda nije implementirana
     */
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
