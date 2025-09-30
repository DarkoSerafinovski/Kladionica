package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utakmica implements Serializable, OpstiDomenskiObjekat {
    
    private int idUtakmica;
    private String domacin;
    private String gost;
    private Date termin;

    public Utakmica() {
    }

    public Utakmica(int idUtakmica, String domacin, String gost, Date termin) {
        this.idUtakmica = idUtakmica;
        this.domacin = domacin;
        this.gost = gost;
        this.termin = termin;
    }

    // getteri i setteri ovde...
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
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idUtakmica;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utakmica other = (Utakmica) obj;
        return this.idUtakmica == other.idUtakmica;
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
        return "'" + domacin + "', '" + gost + "', '" + termin + "'";
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "domacin = '" + domacin + "', gost = '" + gost + "', termin = '" + termin + "'";
    }

    @Override
    public String requeredUslov() {
        return "u.utakmica = " + idUtakmica;
    }

    @Override
    public String getUslovZaPretragu() {
        if (domacin != null && !domacin.isEmpty()) {
            return "u.domacin LIKE '%" + domacin + "%'";
        }
        if (gost != null && !gost.isEmpty()) {
            return "u.gost LIKE '%" + gost + "%'";
        }
        if (termin != null) {
            java.sql.Date sqlDate = new java.sql.Date(termin.getTime());
            return "u.termin = '" + sqlDate + "'";
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
            Utakmica utakmica = new Utakmica();
            utakmica.setIdUtakmica(rs.getInt("idUtakmica"));
            utakmica.setDomacin(rs.getString("domacin"));
            utakmica.setGost(rs.getString("gost"));
            utakmica.setTermin(rs.getDate("termin"));
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
        utakmica.setTermin(rs.getDate("termin"));
        return utakmica;
    }


    
}