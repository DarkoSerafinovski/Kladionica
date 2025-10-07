package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;

/**
 * Klasa koja predstavlja zaposlenje radnika u poslovnici sa datumom pocetka i eventualnog prestanka zaposlenja.
 */
public class Zaposlenje implements Serializable, OpstiDomenskiObjekat {
    
    private int idRadnik;
    private int idPoslovnica;
    private Date datumZaposlenja;
    private Date datumOtpustanja;

    /**
     * Prazan konstruktor.
     */
    public Zaposlenje() {
    }

    /**
     * Konstruktor koji postavlja radnika, poslovnicu i datum zaposlenja.
     *
     * @param idRadnik identifikator radnika
     * @param idPoslovnica identifikator poslovnice
     * @param datumZaposlenja datum pocetka zaposlenja
     */
    public Zaposlenje(int idRadnik, int idPoslovnica, Date datumZaposlenja) {
        this.idRadnik = idRadnik;
        this.idPoslovnica = idPoslovnica;
        this.datumZaposlenja = datumZaposlenja;
    }

    /**
     * Konstruktor koji postavlja radnika, poslovnicu, datum zaposlenja i datum otpustanja.
     *
     * @param idRadnik identifikator radnika
     * @param idPoslovnica identifikator poslovnice
     * @param datumZaposlenja datum pocetka zaposlenja
     * @param datumOtpustanja datum otpustanja radnika
     */
    public Zaposlenje(int idRadnik, int idPoslovnica, Date datumZaposlenja, Date datumOtpustanja) {
        this.idRadnik = idRadnik;
        this.idPoslovnica = idPoslovnica;
        this.datumZaposlenja = datumZaposlenja;
        this.datumOtpustanja = datumOtpustanja;
    }

    /**
     * @return identifikator radnika
     */
    public int getIdRadnik() {
        return idRadnik;
    }

    /**
     * @param idRadnik identifikator radnika
     */
    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    /**
     * @return identifikator poslovnice
     */
    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    /**
     * @param idPoslovnica identifikator poslovnice
     */
    public void setIdPoslovnica(int idPoslovnica) {
        this.idPoslovnica = idPoslovnica;
    }

    /**
     * @return datum zaposlenja
     */
    public Date getDatumZaposlenja() {
        return datumZaposlenja;
    }

    /**
     * @param datumZaposlenja datum zaposlenja
     */
    public void setDatumZaposlenja(Date datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

    /**
     * @return datum otpustanja
     */
    public Date getDatumOtpustanja() {
        return datumOtpustanja;
    }

    /**
     * @param datumOtpustanja datum otpustanja radnika
     */
    public void setDatumOtpustanja(Date datumOtpustanja) {
        this.datumOtpustanja = datumOtpustanja;
    }

    @Override
    public String getNazivTabele() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String alies() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getJoinUslov() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKoloneZaInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVrednostiZaInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVrednostiZaUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String requeredUslov() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUslovZaPretragu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUslov() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @param rs skup rezultata iz baze podataka
     * @return lista objekata tipa Zaposlenje
     * @throws SQLException ako dodje do greske pri citanju podataka iz baze
     */
    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @param rs skup rezultata iz baze podataka
     * @return objekat klase Zaposlenje
     * @throws SQLException ako dodje do greske pri citanju podataka iz baze
     */
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
