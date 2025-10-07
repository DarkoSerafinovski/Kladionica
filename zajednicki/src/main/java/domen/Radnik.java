package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa predstavlja entitet radnika u sistemu. 
 * Sadrzi podatke o identifikatoru, imenu, prezimenu, korisnickom imenu, lozinci i broju telefona. 
 * Klasa implementira interfejs OpstiDomenskiObjekat i koristi se za rad sa bazom podataka.
 * 
 * Ova klasa omogucava pripremu SQL izraza za unos, azuriranje i pretragu radnika u bazi.
 * 
 * @author Darko
 */
public class Radnik implements Serializable, OpstiDomenskiObjekat {

    private int idRadnik;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private String brojTelefona;

    /**
     * Prazan konstruktor koji kreira novi objekat klase Radnik.
     */
    public Radnik() {
    }

    /**
     * Parametrizovani konstruktor koji inicijalizuje sve atribute radnika.
     * 
     * @param idRadnik jedinstveni identifikator radnika
     * @param ime ime radnika
     * @param prezime prezime radnika
     * @param korisnickoIme korisnicko ime radnika
     * @param lozinka lozinka radnika
     * @param brojTelefona broj telefona radnika
     */
    public Radnik(int idRadnik, String ime, String prezime, String korisnickoIme, String lozinka, String brojTelefona) {
        setIdRadnik(idRadnik);
        setIme(ime);
        setPrezime(prezime);
        setKorisnickoIme(korisnickoIme);
        setLozinka(lozinka);
        setBrojTelefona(brojTelefona);
    }

    /**
     * Vraca ID radnika.
     * 
     * @return identifikator radnika
     */
    public int getIdRadnik() {
        return idRadnik;
    }

    /**
     * Postavlja ID radnika.
     * 
     * @param idRadnik nova vrednost identifikatora radnika
     */
    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    /**
     * Vraca ime radnika.
     * 
     * @return ime radnika
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime radnika.
     * 
     * @param ime novo ime radnika
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraca prezime radnika.
     * 
     * @return prezime radnika
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime radnika.
     * 
     * @param prezime novo prezime radnika
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraca korisnicko ime radnika.
     * 
     * @return korisnicko ime radnika
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisnicko ime radnika.
     * 
     * @param korisnickoIme novo korisnicko ime radnika
     */
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Vraca lozinku radnika.
     * 
     * @return lozinka radnika
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Postavlja lozinku radnika.
     * 
     * @param lozinka nova lozinka radnika
     */
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    /**
     * Vraca broj telefona radnika.
     * 
     * @return broj telefona radnika
     */
    public String getBrojTelefona() {
        return brojTelefona;
    }

    /**
     * Postavlja broj telefona radnika.
     * 
     * @param brojTelefona novi broj telefona radnika
     */
    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    /**
     * Vraca string reprezentaciju radnika u formatu "Ime Prezime".
     * 
     * @return ime i prezime radnika kao string
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Vraca naziv tabele u bazi podataka koja odgovara ovoj klasi.
     * 
     * @return string "radnik"
     */
    @Override
    public String getNazivTabele() {
        return "radnik";
    }

    /**
     * Vraca alias koji se koristi u SQL upitima za tabelu radnik.
     * 
     * @return alias "r"
     */
    @Override
    public String alies() {
        return "r";
    }

    /**
     * Vraca uslov za spajanje sa drugim tabelama. 
     * Posto radnik nije direktno povezan sa drugim tabelama, vraca prazan string.
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
     * @return string "ime, prezime, korisnickoIme, lozinka, brojTelefona"
     */
    @Override
    public String getKoloneZaInsert() {
        return "ime, prezime, korisnickoIme, lozinka, brojTelefona";
    }

    /**
     * Vraca vrednosti koje ce biti unete u tabelu prilikom INSERT upita.
     * 
     * @return string sa vrednostima u formatu: 'ime', 'prezime', 'korisnickoIme', 'lozinka', 'brojTelefona'
     */
    @Override
    public String getVrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', '" + korisnickoIme + "', '" + lozinka + "', '" + brojTelefona + "'";
    }

    /**
     * Vraca string koji predstavlja deo SQL upita za azuriranje podataka (UPDATE).
     * 
     * @return string u formatu "ime = '...', prezime = '...', brojTelefona = '...'"
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "ime = '" + ime + "', prezime = '" + prezime + "', brojTelefona = '" + brojTelefona + "'";
    }

    /**
     * Vraca osnovni uslov koji se koristi u SQL upitima za pronalazenje konkretne instance radnika.
     * 
     * @return string u formatu "r.idRadnik = [idRadnik]"
     */
    @Override
    public String requeredUslov() {
        return "r.idRadnik = " + idRadnik;
    }

    /**
     * Vraca SQL uslov za pretragu radnika na osnovu unetih kriterijuma.  
     * Ako su uneti korisnicko ime i lozinka, koristi se njihova kombinacija.  
     * Ako nije uneto, koristi se ime ili prezime, u zavisnosti od dostupnih vrednosti.
     * 
     * @return string koji predstavlja WHERE uslov u SQL upitu
     */
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

    /**
     * Vraca naziv kolona koje ce biti izabrane u SELECT upitu.
     * 
     * @return string "*"
     */
    @Override
    public String getUslov() {
        return "*";
    }

    /**
     * Konvertuje ResultSet objekat dobijen iz baze u listu objekata tipa Radnik.
     * 
     * @param rs rezultat SQL upita
     * @return lista objekata tipa Radnik
     * @throws SQLException ako dodje do greske prilikom citanja iz baze
     */
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

    /**
     * Konvertuje jedan red iz ResultSet-a u objekat tipa Radnik.
     * 
     * @param rs rezultat SQL upita
     * @return objekat klase Radnik popunjen vrednostima iz baze
     * @throws SQLException ako dodje do greske prilikom citanja podataka iz baze
     */
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
