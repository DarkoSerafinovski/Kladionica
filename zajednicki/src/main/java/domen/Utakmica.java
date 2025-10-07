package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Utakmica implements Serializable, OpstiDomenskiObjekat {
    
    private int idUtakmica;
    private String domacin;
    private String gost;
    private Date termin;

    public Utakmica() {
    }

    public Utakmica(int idUtakmica, String domacin, String gost, Date termin) {
        setIdUtakmica(idUtakmica);
        setDomacin(domacin);
        setGost(gost);
        setTermin(termin);
    }

    public int getIdUtakmica() {
        return idUtakmica;
    }

    public void setIdUtakmica(int idUtakmica) {
        this.idUtakmica = idUtakmica;
    }

    public String getDomacin() {
        return domacin;
    }

    public void setDomacin(String domacin) {
        this.domacin = domacin;
    }

    public String getGost() {
        return gost;
    }

    public void setGost(String gost) {
        this.gost = gost;
    }

    public Date getTermin() {
        return termin;
    }

    public void setTermin(Date termin) {
        this.termin = termin;
    }
    
    @Override
    public String toString() {
        return domacin + "-" + gost;
    }
    

    @Override
    public String getNazivTabele() {
        return "utakmica";
    }

    @Override
    public String alies() {
        return "u";
    }

    @Override
    public String getJoinUslov() {
        return "";
    }

    @Override
    public String getKoloneZaInsert() {
        return "domacin, gost, termin";
    }

    @Override
    public String getVrednostiZaInsert() {
        return "'" + domacin + "', '" + gost + "', '" + new java.sql.Timestamp(termin.getTime()) + "'";
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "domacin = '" + domacin + "', gost = '" + gost + "', termin = '" + new java.sql.Timestamp(termin.getTime()) + "'";
    }

    @Override
    public String requeredUslov() {
        return "u.idUtakmica = " + idUtakmica;
    }

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

    @Override
    public String getUslov() {
        return "*";
    }

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
