package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Poslovnica implements Serializable, OpstiDomenskiObjekat {

    private int idPoslovnica;
    private String adresa;
    private String brojTelefona;

    public Poslovnica() {
    }

    public Poslovnica(int idPoslovnica, String adresa, String brojTelefona) {
        this.idPoslovnica = idPoslovnica;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getIdPoslovnica() {
        return idPoslovnica;
    }

    public void setIdPoslovnica(int idPoslovnica) {
        this.idPoslovnica = idPoslovnica;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    @Override
    public String toString() {
        return adresa;
    }

     @Override
    public String getNazivTabele() {
        return "poslovnica";
    }

    @Override
    public String alies() {
        return "p";
    }

    @Override
    public String getJoinUslov() {
        return "";
    }

    @Override
    public String getKoloneZaInsert() {
        return "adresa, brojTelefona";
    }

    @Override
    public String getVrednostiZaInsert() {
        return "'" + adresa + "', '" + brojTelefona + "'";
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "adresa = '" + adresa + "', brojTelefona = '" + brojTelefona + "'";
    }

    @Override
    public String requeredUslov() {
        return "p.idPoslovnica = " + idPoslovnica;
    }

    @Override
    public String getUslovZaPretragu() {
        if (adresa != null && !adresa.isEmpty()) {
            return "p.adresa LIKE '%" + adresa + "%'";
        }
        if (brojTelefona != null && !brojTelefona.isEmpty()) {
            return "p.brojTelefona LIKE '%" + brojTelefona + "%'";
        }
        return "";
    }

    @Override
    public String getUslov() {
        return "*";
    }

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

    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
    
}