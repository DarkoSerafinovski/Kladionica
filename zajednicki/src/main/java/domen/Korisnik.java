package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja Korisnika sa osnovnim informacijama i metodama za rad sa bazom podataka.
 */
public class Korisnik implements Serializable, OpstiDomenskiObjekat {

    private int idKorisnik;
    private String ime;
    private String prezime;
    private String jmbg;
    private Grad grad;

    /**
     * Bezparametarski konstruktor.
     */
    public Korisnik() {
    }

    /**
     * Parametarski konstruktor.
     *
     * @param idKorisnik ID korisnika
     * @param ime ime korisnika
     * @param prezime prezime korisnika
     * @param jmbg jedinstveni maticni broj gradjana
     * @param grad grad korisnika
     */
    public Korisnik(int idKorisnik, String ime, String prezime, String jmbg, Grad grad) {
        setIdKorisnik(idKorisnik);
        setIme(ime);
        setPrezime(prezime);
        setJmbg(jmbg);
        setGrad(grad);
    }

    /**
     * Vraca ID korisnika.
     *
     * @return ID korisnika
     */
    public int getIdKorisnik() {
        return idKorisnik;
    }

    /**
     * Postavlja ID korisnika.
     *
     * @param idKorisnik ID korisnika
     */
    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    /**
     * Vraca ime korisnika.
     *
     * @return ime korisnika
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime korisnika.
     *
     * @param ime ime korisnika
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraca prezime korisnika.
     *
     * @return prezime korisnika
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime korisnika.
     *
     * @param prezime prezime korisnika
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraca JMBG korisnika.
     *
     * @return JMBG korisnika
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Postavlja JMBG korisnika.
     *
     * @param jmbg jedinstveni maticni broj
     */
    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    /**
     * Vraca grad korisnika.
     *
     * @return Grad korisnika
     */
    public Grad getGrad() {
        return grad;
    }

    /**
     * Postavlja grad korisnika.
     *
     * @param grad Grad korisnika
     */
    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    /**
     * Vraca string sa imenom i prezimenom korisnika.
     *
     * @return ime i prezime korisnika
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Vraca naziv tabele u bazi podataka.
     *
     * @return naziv tabele
     */
    @Override
    public String getNazivTabele() {
        return "korisnik";
    }

    /**
     * Vraca alias tabele za SQL upite.
     *
     * @return alias tabele
     */
    @Override
    public String alies() {
        return "k";
    }

    /**
     * Vraca JOIN uslov sa tabelom Grad.
     *
     * @return string JOIN uslova
     */
    @Override
    public String getJoinUslov() {
        return " JOIN grad g ON k.idGrad = g.idGrad";
    }

    /**
     * Vraca kolone za INSERT upit.
     *
     * @return string kolona
     */
    @Override
    public String getKoloneZaInsert() {
        return "ime, prezime, jmbg, idGrad";
    }

    /**
     * Vraca vrednosti za INSERT upit.
     *
     * @return string vrednosti
     */
    @Override
    public String getVrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', '" + jmbg + "', " + grad.getIdGrad();
    }

    /**
     * Vraca string za UPDATE upit.
     *
     * @return string sa vrednostima za UPDATE
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "ime = '" + ime + "', prezime = '" + prezime + "', jmbg = '" + jmbg + "', idGrad = " + grad.getIdGrad();
    }

    /**
     * Vraca uslov za identifikaciju objekta po ID-u.
     *
     * @return string uslova
     */
    @Override
    public String requeredUslov() {
        return "idKorisnik = " + idKorisnik;
    }

    /**
     * Vraca SQL uslov za pretragu po imenu, prezimenu, JMBG-u i ID-u grada.
     *
     * @return string sa uslovima pretrage
     */
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

    /**
     * Vraca standardni SQL uslov (*).
     *
     * @return string "*"
     */
    @Override
    public String getUslov() {
        return "*";
    }

    /**
     * Konvertuje ResultSet u listu korisnika.
     *
     * @param rs ResultSet iz baze podataka
     * @return lista Korisnik objekata
     * @throws SQLException ako dodje do greske pri citanju ResultSet-a
     */
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

    /**
     * Konvertuje ResultSet u jedan objekat korisnika.
     *
     * @param rs ResultSet iz baze podataka
     * @return Korisnik objekat
     * @throws SQLException ako dodje do greske pri citanju ResultSet-a
     */
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
