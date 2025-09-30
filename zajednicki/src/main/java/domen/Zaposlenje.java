package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Zaposlenje implements Serializable, OpstiDomenskiObjekat {
    
    private int idRadnik;
    private int idPoslovnica;
    private Date datumZaposlenja;
    private Date datumOtpustanja;

    public Zaposlenje() {
    }

    public Zaposlenje(int idRadnik, int idPoslovnica, Date datumZaposlenja) {
        this.idRadnik = idRadnik;
        this.idPoslovnica = idPoslovnica;
        this.datumZaposlenja = datumZaposlenja;
    }

    public Zaposlenje(int idRadnik, int idPoslovnica, Date datumZaposlenja, Date datumOtpustanja) {
        this.idRadnik = idRadnik;
        this.idPoslovnica = idPoslovnica;
        this.datumZaposlenja = datumZaposlenja;
        this.datumOtpustanja = datumOtpustanja;
    }
    
    // getteri i setteri ovde...
    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    public void setIdPoslovnica(int idPoslovnica) {
        this.idPoslovnica = idPoslovnica;
    }

    public Date getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(Date datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

    public Date getDatumOtpustanja() {
        return datumOtpustanja;
    }

    public void setDatumOtpustanja(Date datumOtpustanja) {
        this.datumOtpustanja = datumOtpustanja;
    }

    @Override
    public String getNazivTabele() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String alies() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getJoinUslov() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getKoloneZaInsert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getVrednostiZaInsert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getVrednostiZaUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String requeredUslov() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getUslovZaPretragu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getUslov() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

    