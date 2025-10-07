package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Klasa koja predstavlja utakmicu sa informacijama o domacinu, gostu i terminu.
 */
public class Utakmica implements Serializable, OpstiDomenskiObjekat {
    
    private int idUtakmica;
    private String domacin;
    private String gost;
    private Date termin;

    /**
     * Prazan konstruktor.
     */
    public Utakmica() {
    }

    /**
     * Konstruktor koji postavlja sve atribute utakmice.
     * 
     * @param idUtakmica identifikator utakmice
     * @param domacin naziv domaceg tima
     * @param gost naziv gostujuceg tima
     * @param termin datum i vreme utakmice
     */
    public Utakmica(int idUtakmica, String domacin, String gost, Date termin) {
        setIdUtakmica(idUtakmica);
        setDomacin(domacin);
        setGost(gost);
        setTermin(termin);
    }

    /**
     * @return identifikator utakmice
     */
    public int getIdUtakmica() {
        return idUtakmica;
    }

    /**
     * @param idUtakmica identifikator utakmice
     */
    public void setIdUtakmica(int idUtakmica) {
        this.idUtakmica = idUtakmica;
    }

    /**
     * @return naziv domaceg tima
     */
    public String getDomacin() {
        return domacin;
    }

    /**
     * @param domacin naziv domaceg tima
     */
    public void setDomacin(String domacin) {
        this.domacin = domacin;
    }

    /**
     * @return naziv gostujuceg tima
     */
    public String getGost() {
        return gost;
    }

    /**
     * @param gost naziv gostujuceg tima
     */
    public void setGost(String gost) {
        this.gost = gost;
    }

    /**
     * @return termin utakmice
     */
    public Date getTermin() {
        return termin;
    }

    /**
     * @param termin datum i vreme utakmice
     */
    public void setTermin(Date termin) {
        this.termin = termin;
    }

    /**
     * @return string reprezentacija utakmice u formatu "domacin-gost"
     */
    @Override
    public String toString() {
        return domacin + "-" + gost;
    }

    /**
     * @return naziv tabele u bazi podataka
     */
    @Override
    public String getNazivTabele() {
        return "utakmica";
    }

    /**
     * @return alijas za tabelu u SQL upitima
     */
    @Override
    public String alies() {
        return "u";
    }

    /**
     * @return uslov za spajanje tabela
     */
    @Override
    public String getJoinUslov() {
        return "";
    }

    /**
     * @return kolone koje se koriste za unos nove utakmice
     */
    @Override
    public String getKoloneZaInsert() {
        return "domacin, gost, termin";
    }

    /**
     * @return vrednosti atributa koje se unose u bazu
     */
    @Override
    public String getVrednostiZaInsert() {
        return "'" + domacin + "', '" + gost + "', '" + new java.sql.Timestamp(termin.getTime()) + "'";
    }

    /**
     * @return vrednosti koje se koriste pri azuriranju utakmice
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "domacin = '" + domacin + "', gost = '" + gost + "', termin = '" + new java.sql.Timestamp(termin.getTime()) + "'";
    }

    /**
     * @return uslov za pronalazenje utakmice po ID-u
     */
    @Override
    public String requeredUslov() {
        return "u.idUtakmica = " + idUtakmica;
    }

    /**
     * @return SQL uslov za pretragu utakmica
     */
    @Override
    public String getUslovZaPretragu() {
        StringBuilder uslov = new StringBuilder();
        boolean prviUslov = true;

        if (domacin != null && !domacin.isEmpty()) {
            uslov.append("u.domacin LIKE '%").append(domacin).append("%'");
            prviUslov = false;
        }

        if (gost != null && !gost.isEmpty()) {
            if (!prviUslov) {
                uslov.append(" AND ");
            }
            uslov.append("u.gost LIKE '%").append(gost).append("%'");
            prviUslov = false;
        }

        if (termin != null) {
            if (!prviUslov) {
                uslov.append(" AND ");
            }
            java.sql.Date sqlDate = new java.sql.Date(termin.getTime());
            uslov.append("u.termin = '").append(sqlDate).append("'");
        }

        return uslov.toString();
    }

    /**
     * @return kolone koje se selektuju u SQL upitu
     */
    @Override
    public String getUslov() {
        return "*";
    }

    /**
     * @param rs skup rezultata iz baze podataka
     * @return lista objekata tipa Utakmica
     * @throws SQLException ako dodje do greske pri citanju podataka iz baze
     */
    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Utakmica utakmica = new Utakmica();
            utakmica.setIdUtakmica(rs.getInt("idUtakmica"));
            utakmica.setDomacin(rs.getString("domacin"));
            utakmica.setGost(rs.getString("gost"));
            utakmica.setTermin(rs.getTimestamp("termin"));
            lista.add(utakmica);
        }
        return lista;
    }

    /**
     * @param rs skup rezultata iz baze podataka
     * @return objekat klase Utakmica sa podacima iz baze
     * @throws SQLException ako dodje do greske pri citanju podataka iz baze
     */
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Utakmica utakmica = new Utakmica();
        utakmica.setIdUtakmica(rs.getInt("idUtakmica"));
        utakmica.setDomacin(rs.getString("domacin"));
        utakmica.setGost(rs.getString("gost"));
        utakmica.setTermin(rs.getTimestamp("termin"));
        return utakmica;
    }
}
