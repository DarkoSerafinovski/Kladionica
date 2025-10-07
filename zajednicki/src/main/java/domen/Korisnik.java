package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Korisnik implements Serializable, OpstiDomenskiObjekat {

    private int idKorisnik;
    private String ime;
    private String prezime;
    private String jmbg;
    private Grad grad;

    public Korisnik() {
    }

    public Korisnik(int idKorisnik, String ime, String prezime, String jmbg, Grad grad) {
        setIdKorisnik(idKorisnik);
        setIme(ime);
        setPrezime(prezime);
        setJmbg(jmbg);
        setGrad(grad);
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
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

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String getNazivTabele() {
        return "korisnik";
    }

    @Override
    public String alies() {
        return "k";
    }

    @Override
    public String getJoinUslov() {
        return " JOIN grad g ON k.idGrad = g.idGrad";
    }

    @Override
    public String getKoloneZaInsert() {
        return "ime, prezime, jmbg, idGrad";
    }

    @Override
    public String getVrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', '" + jmbg + "', " + grad.getIdGrad();
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "ime = '" + ime + "', prezime = '" + prezime + "', jmbg = '" + jmbg + "', idGrad = " + grad.getIdGrad();
    }

    @Override
    public String requeredUslov() {
        return "idKorisnik = " + idKorisnik;
    }

    @Override
    public String getUslovZaPretragu() {
        List<String> uslovi = new ArrayList<>();
        if (ime != null && !ime.isEmpty()) {
            uslovi.add("k.ime LIKE '%" + ime + "%'");
        }
        if (prezime != null && !prezime.isEmpty()) {
            uslovi.add("k.prezime LIKE '%" + prezime + "%'");
        }
        if (jmbg != null && !jmbg.isEmpty()) {
            uslovi.add("k.jmbg LIKE '%" + jmbg + "%'");
        }
        if (grad != null && grad.getIdGrad() > 0) {
            uslovi.add("k.idGrad = " + grad.getIdGrad());
        }
        return uslovi.isEmpty() ? "" : String.join(" AND ", uslovi);
    }

    @Override
    public String getUslov() {
        return "*";
    }

    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Grad g = new Grad();
            g.setIdGrad(rs.getInt("idGrad"));
            g.setNaziv(rs.getString("naziv"));
            g.setPostanskiBroj(rs.getString("postanskiBroj"));

            Korisnik k = new Korisnik();
            k.setIdKorisnik(rs.getInt("idKorisnik"));
            k.setIme(rs.getString("ime"));
            k.setPrezime(rs.getString("prezime"));
            k.setJmbg(rs.getString("jmbg"));
            k.setGrad(g);

            lista.add(k);
        }
        return lista;
    }

    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Grad g = new Grad();
        g.setIdGrad(rs.getInt("idGrad"));
        g.setNaziv(rs.getString("naziv"));
        g.setPostanskiBroj(rs.getString("postanskiBroj"));

        Korisnik k = new Korisnik();
        k.setIdKorisnik(rs.getInt("idKorisnik"));
        k.setIme(rs.getString("ime"));
        k.setPrezime(rs.getString("prezime"));
        k.setJmbg(rs.getString("jmbg"));
        k.setGrad(g);

        return k;
    }
}
