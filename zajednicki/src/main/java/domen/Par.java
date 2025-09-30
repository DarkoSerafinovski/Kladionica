package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Par implements Serializable, OpstiDomenskiObjekat {
    private int idPar;
    private int idTiket;
    private Utakmica utakmica;
    private TipOpklade tipOpklade;
    private double kvota;
    private int redosled;

    public Par() {
    }

    public Par(int idPar, int idTiket, Utakmica utakmica, TipOpklade tipOpklade, double kvota, int redosled) {
        this.idPar = idPar;
        this.idTiket = idTiket;
        this.utakmica = utakmica;
        this.tipOpklade = tipOpklade;
        this.kvota = kvota;
        this.redosled = redosled;
    }

    public int getIdPar() {
        return idPar;
    }

    public void setIdPar(int idPar) {
        this.idPar = idPar;
    }

    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public Utakmica getUtakmica() {
        return utakmica;
    }

    public void setUtakmica(Utakmica utakmica) {
        this.utakmica = utakmica;
    }

    

    public TipOpklade getTipOpklade() {
        return tipOpklade;
    }

    public void setTipOpklade(TipOpklade tipOpklade) {
        this.tipOpklade = tipOpklade;
    }

    public double getKvota() {
        return kvota;
    }

    public void setKvota(double kvota) {
        this.kvota = kvota;
    }

    public int getRedosled() {
        return redosled;
    }

    public void setRedosled(int redosled) {
        this.redosled = redosled;
    }


    @Override
    public String toString() {
        return "Par{" +
                "idPar=" + idPar +
                ", idTiket=" + idTiket +
                ", Utakmica=" + utakmica +
                ", tipOpklade='" + tipOpklade + '\'' +
                ", kvota=" + kvota +
                ", redosled=" + redosled +
                '}';
    }

    @Override
    public String getNazivTabele() {
        return "par";
    }

    @Override
    public String alies() {
        return "p";
    }

    @Override
    public String getJoinUslov() {
        return " JOIN tiket t ON p.idTiket = t.tiketID"
             + " JOIN utakmica u ON p.idUtakmica = u.utakmicaID";
    }

    @Override
    public String getKoloneZaInsert() {
        return "idTiket, idUtakmica, tipOpklade, kvota, redosled";
    }

    @Override
    public String getVrednostiZaInsert() {
        return idTiket + ", " + utakmica.getIdUtakmica() + ", '" + tipOpklade + "', " + kvota  + ", " + redosled;
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "tipOpklade = '" + tipOpklade + "', kvota = " + kvota + ", status = " + ", redosled = " + redosled;
    }

    @Override
    public String requeredUslov() {
        return "p.idPar = " + idPar;
    }

    @Override
    public String getUslovZaPretragu() {
        return "p.idTiket = " + idTiket;
    }

    @Override
    public String getUslov() {
        return "*";
    }

    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {

            // Kreiranje utakmice
            Utakmica u = new Utakmica();
            u.setIdUtakmica(rs.getInt("idUtakmica"));
            u.setDomacin(rs.getString("domacin"));   // pretpostavljam da imaš kolonu "domacin"
            u.setGost(rs.getString("gost"));         // pretpostavljam "gost"
            u.setTermin(rs.getTimestamp("termin"));    // pretpostavljam "datum"
            // dodaj ostale atribute iz tabele utakmica koje imaš

            // Kreiranje para
            Par par = new Par();
            par.setIdPar(rs.getInt("idPar"));
            par.setIdTiket(rs.getInt("idTiket"));
            par.setUtakmica(u);   // umesto idUtakmica stavljamo ceo objekat
            String tipOpkladeStr = rs.getString("tipOpklade");
            par.setTipOpklade(TipOpklade.valueOf(tipOpkladeStr));
            par.setKvota(rs.getDouble("kvota"));
            par.setRedosled(rs.getInt("redosled"));

            lista.add(par);
        }
        return lista;
    }


    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Utakmica u = new Utakmica();
            u.setIdUtakmica(rs.getInt("idUtakmica"));
            u.setDomacin(rs.getString("domacin"));   // pretpostavljam da imaš kolonu "domacin"
            u.setGost(rs.getString("gost"));         // pretpostavljam "gost"
            u.setTermin(rs.getTimestamp("termin"));    // pretpostavljam "datum"
            // dodaj ostale atribute iz tabele utakmica koje imaš

            // Kreiranje para
            Par par = new Par();
            par.setIdPar(rs.getInt("idPar"));
            par.setIdTiket(rs.getInt("idTiket"));
            par.setUtakmica(u);   // umesto idUtakmica stavljamo ceo objekat
            String tipOpkladeStr = rs.getString("tipOpklade");
            par.setTipOpklade(TipOpklade.valueOf(tipOpkladeStr));
            par.setKvota(rs.getDouble("kvota"));
            par.setRedosled(rs.getInt("redosled"));
            
            return par;
    }

   
}