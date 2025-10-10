package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja Grad sa osnovnim informacijama i metodama za rad sa bazom podataka.
 */
public class Grad implements Serializable, OpstiDomenskiObjekat {

    private int idGrad;
    private String naziv;
    private String postanskiBroj;

    /**
     * Parametarski konstruktor za kreiranje objekta Grad sa svim atributima.
     *
     * @param idGrad ID grada
     * @param naziv Naziv grada
     * @param postanskiBroj Postanski broj grada
     */
    public Grad(int idGrad, String naziv, String postanskiBroj) {
        setIdGrad(idGrad);
        setNaziv(naziv); 
        setPostanskiBroj(postanskiBroj);
    }

    /**
     * Bezparametarski konstruktor.
     */
    public Grad() {
    }

    /**
     * Vraca ID grada.
     *
     * @return ID grada
     */
    public int getIdGrad() {
        return idGrad;
    }

    /**
     * Postavlja ID grada.
     *
     * @param idGrad ID grada (ne sme biti negativan)
     */
    public void setIdGrad(int idGrad) {
        if(idGrad < 0){
            throw new IllegalArgumentException("ID Grada ne sme da bude negativan.");
        }
        this.idGrad = idGrad;
    }

    /**
     * Vraca naziv grada.
     *
     * @return naziv grada
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv grada.
     *
     * @param naziv naziv grada
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraca postanski broj grada.
     *
     * @return poštanski broj grada
     */
    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Postavlja postanski broj grada.
     *
     * @param postanskiBroj postanski broj 
     */
    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    /**
     * Vraca tekstualnu reprezentaciju grada (naziv).
     *
     * @return naziv grada
     */
    @Override
    public String toString() {
        return naziv;
    }

    /**
     * Vraca naziv tabele u bazi podataka koja predstavlja Grad.
     *
     * @return naziv tabele
     */
    @Override
    public String getNazivTabele() {
        return "grad";
    }

    /**
     * Vraca alias za tabelu u SQL upitima.
     *
     * @return alias tabele
     */
    @Override
    public String alies() {
        return "g";
    }

    /**
     * Vraca JOIN uslov (ako postoji).
     *
     * @return string JOIN uslova
     */
    @Override
    public String getJoinUslov() {
        return "";
    }

    /**
     * Vraca kolone koje se koriste za INSERT upit.
     *
     * @return string sa imenima kolona
     */
    @Override
    public String getKoloneZaInsert() {
        return "naziv, postanskiBroj";
    }

    /**
     * Vraca vrednosti koje se koriste za INSERT upit.
     *
     * @return string sa vrednostima
     */
    @Override
    public String getVrednostiZaInsert() {
        return "'" + naziv + "', '" + postanskiBroj + "'";
    }

    /**
     * Vraca string za UPDATE upit sa vrednostima.
     *
     * @return string za UPDATE
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "naziv = '" + naziv + "', postanskiBroj = '" + postanskiBroj + "'";
    }

    /**
     * Vraca uslov koji se koristi za identifikaciju objekta (po ID-u).
     *
     * @return string sa uslovom
     */
    @Override
    public String requeredUslov() {
        return "g.gradID = " + idGrad;
    }

    /**
     * Vraca uslov za pretragu po nazivu grada.
     *
     * @return string uslova za pretragu
     */
    @Override
    public String getUslovZaPretragu() {
        if (naziv != null && !naziv.isEmpty()) {
            return "g.naziv LIKE '%" + naziv + "%'";
        }
        return "";
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
     * Konvertuje ResultSet u listu objekata Grad.
     *
     * @param rs ResultSet iz baze podataka
     * @return lista Grad objekata
     * @throws SQLException ako dodje do greske pri citanju ResultSet-a
     */
    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Grad grad = new Grad();
            grad.setIdGrad(rs.getInt("idGrad"));
            grad.setNaziv(rs.getString("naziv"));
            grad.setPostanskiBroj(rs.getString("postanskiBroj"));
            lista.add(grad);
        }
        return lista;
    }

    /**
     * Konverzija ResultSet-a u pojedinacni objekat Grad nije podrzana.
     * Ne koristi se ni u jednoj sistemskoj operaciji tako da nije dodata logika
     *
     * @param rs ResultSet iz baze podataka
     * @return ništa (baceno UnsupportedOperationException)
     * @throws SQLException 
     * @throws UnsupportedOperationException uvek
     */
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
