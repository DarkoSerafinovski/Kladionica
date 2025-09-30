package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Radnik implements Serializable, OpstiDomenskiObjekat {

    private int idRadnik;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String brojTelefona;

    public Radnik() {
    }

    public Radnik(int idRadnik, String ime, String prezime, String korisnickoIme, String lozinka, String brojTelefona) {
        this.idRadnik = idRadnik;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.brojTelefona = brojTelefona;
    }

    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String getNazivTabele() {
        return "radnik";
    }

    @Override
    public String alies() {
        return "r";
    }

    @Override
    public String getJoinUslov() {
        return "";
    }

    @Override
    public String getKoloneZaInsert() {
        return "ime, prezime, korisnickoIme, lozinka, brojTelefona";
    }

    @Override
    public String getVrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', '" + korisnickoIme + "', '" + lozinka + "', '" + brojTelefona + "'";
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "ime = '" + ime + "', prezime = '" + prezime + "', brojTelefona = '" + brojTelefona + "'";
    }

    @Override
    public String requeredUslov() {
        return "r.idRadnik = " + idRadnik;
    }

    @Override
    public String getUslovZaPretragu() {
        if (korisnickoIme != null && !korisnickoIme.isEmpty() && lozinka != null && !lozinka.isEmpty()) {
            return "r.korisnickoIme = '" + korisnickoIme + "' AND r.lozinka = '" + lozinka + "'";
        }
        if (ime != null && !ime.isEmpty()) {
            return "r.ime LIKE '%" + ime + "%'";
        }
        if (prezime != null && !prezime.isEmpty()) {
            return "r.prezime LIKE '%" + prezime + "%'";
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
            Radnik radnik = new Radnik();
            radnik.setIdRadnik(rs.getInt("idRadnik"));
            radnik.setIme(rs.getString("ime"));
            radnik.setPrezime(rs.getString("prezime"));
            radnik.setKorisnickoIme(rs.getString("korisnickoIme"));
            radnik.setLozinka(rs.getString("lozinka"));
            radnik.setBrojTelefona(rs.getString("brojTelefona"));
            lista.add(radnik);
        }
        return lista;
    }

    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Radnik radnik = new Radnik();
            radnik.setIdRadnik(rs.getInt("idRadnik"));
            radnik.setIme(rs.getString("ime"));
            radnik.setPrezime(rs.getString("prezime"));
            radnik.setKorisnickoIme(rs.getString("korisnickoIme"));
            radnik.setLozinka(rs.getString("lozinka"));
            radnik.setBrojTelefona(rs.getString("brojTelefona"));
            
            return radnik;
    }
}