package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Predstavlja jedan par na tiketu.
 * Sadrzi podatke o utakmici, tipu opklade, kvoti i redosledu na tiketu.
 * Implementira interfejs OpstiDomenskiObjekat radi integracije sa bazom podataka.
 */
public class Par implements Serializable, OpstiDomenskiObjekat {

    private int idPar;
    private int idTiket;
    private Utakmica utakmica;
    private TipOpklade tipOpklade;
    private double kvota;
    private int redosled;

    /**
     * Bezparametarski konstruktor.
     */
    public Par() {
    }

    /**
     * Konstruktor sa svim parametrima.
     *
     * @param idPar ID para
     * @param idTiket ID tiketa
     * @param utakmica Utakmica na koju se odnosi par
     * @param tipOpklade Tip opklade
     * @param kvota Kvota
     * @param redosled Redosled na tiketu
     */
    public Par(int idPar, int idTiket, Utakmica utakmica, TipOpklade tipOpklade, double kvota, int redosled) {
        setIdPar(idPar);
        setIdTiket(idTiket);
        setUtakmica(utakmica);
        setTipOpklade(tipOpklade);
        setKvota(kvota);
        setRedosled(redosled);
    }

    /**
     * Vraca ID para.
     *
     * @return ID para
     */
    public int getIdPar() {
        return idPar;
    }

    /**
     * Postavlja ID para.
     *
     * @param idPar ID para
     */
    public void setIdPar(int idPar) {
        this.idPar = idPar;
    }

    /**
     * Vraca ID tiketa na kojem se nalazi par.
     *
     * @return ID tiketa
     */
    public int getIdTiket() {
        return idTiket;
    }

    /**
     * Postavlja ID tiketa.
     *
     * @param idTiket ID tiketa
     */
    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    /**
     * Vraca utakmicu na koju se odnosi par.
     *
     * @return Utakmica
     */
    public Utakmica getUtakmica() {
        return utakmica;
    }

    /**
     * Postavlja utakmicu za par.
     *
     * @param utakmica Utakmica
     */
    public void setUtakmica(Utakmica utakmica) {
        this.utakmica = utakmica;
    }

    /**
     * Vraca tip opklade za par.
     *
     * @return Tip opklade
     */
    public TipOpklade getTipOpklade() {
        return tipOpklade;
    }

    /**
     * Postavlja tip opklade za par.
     *
     * @param tipOpklade Tip opklade
     */
    public void setTipOpklade(TipOpklade tipOpklade) {
        this.tipOpklade = tipOpklade;
    }

    /**
     * Vraca kvotu para.
     *
     * @return kvota
     */
    public double getKvota() {
        return kvota;
    }

    /**
     * Postavlja kvotu para.
     *
     * @param kvota kvota
     */
    public void setKvota(double kvota) {
        this.kvota = kvota;
    }

    /**
     * Vraca redosled para na tiketu.
     *
     * @return redosled
     */
    public int getRedosled() {
        return redosled;
    }

    /**
     * Postavlja redosled para na tiketu.
     *
     * @param redosled redosled
     */
    public void setRedosled(int redosled) {
        this.redosled = redosled;
    }

    /**
     * Vraca string reprezentaciju para.
     *
     * @return string sa svim podacima o paru
     */
    @Override
    public String toString() {
        return "Par{" +
                "idPar=" + idPar +
                ", idTiket=" + idTiket +
                ", utakmica=" + utakmica +
                ", tipOpklade='" + tipOpklade + '\'' +
                ", kvota=" + kvota +
                ", redosled=" + redosled +
                '}';
    }

    /**
     * Vraca naziv tabele u bazi za ovaj objekat.
     *
     * @return naziv tabele
     */
    @Override
    public String getNazivTabele() {
        return "par";
    }

    /**
     * Vraca alias koji se koristi u SQL upitima.
     *
     * @return alias tabele
     */
    @Override
    public String alies() {
        return "p";
    }

    /**
     * Vraca SQL JOIN uslov za povezivanje sa drugim tabelama.
     *
     * @return JOIN uslov
     */
    @Override
    public String getJoinUslov() {
        return " JOIN tiket t ON p.idTiket = t.tiketID"
             + " JOIN utakmica u ON p.idUtakmica = u.utakmicaID";
    }

    /**
     * Vraca kolone koje se koriste prilikom INSERT upita.
     *
     * @return kolone za INSERT
     */
    @Override
    public String getKoloneZaInsert() {
        return "idTiket, idUtakmica, tipOpklade, kvota, redosled";
    }

    /**
     * Vraca vrednosti koje se koriste prilikom INSERT upita.
     *
     * @return vrednosti za INSERT
     */
    @Override
    public String getVrednostiZaInsert() {
        return idTiket + ", " + utakmica.getIdUtakmica() + ", '" + tipOpklade + "', " + kvota  + ", " + redosled;
    }

    /**
     * Vraca string sa kolonom i vrednostima za UPDATE upit.
     *
     * @return vrednosti za UPDATE
     */
    @Override
    public String getVrednostiZaUpdate() {
        return "tipOpklade = '" + tipOpklade + "', kvota = " + kvota + ", redosled = " + redosled;
    }

    /**
     * Vraca uslov za identifikaciju objekta u bazi.
     *
     * @return uslov za WHERE klauzulu
     */
    @Override
    public String requeredUslov() {
        return "p.idPar = " + idPar;
    }

    /**
     * Vraca uslov za pretragu objekata u bazi prema ID tiketa.
     *
     * @return uslov za pretragu
     */
    @Override
    public String getUslovZaPretragu() {
        return "p.idTiket = " + idTiket;
    }

    /**
     * Vraca znak za selekciju svih kolona u upitu.
     *
     * @return znak '*' za sve kolone
     */
    @Override
    public String getUslov() {
        return "*";
    }

    /**
     * Konvertuje ResultSet u listu Par objekata.
     *
     * @param rs ResultSet iz baze podataka
     * @return lista Par objekata
     * @throws SQLException ako dodje do greske prilikom citanja ResultSet-a
     */
    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        ArrayList<OpstiDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Utakmica u = new Utakmica();
            u.setIdUtakmica(rs.getInt("idUtakmica"));
            u.setDomacin(rs.getString("domacin"));
            u.setGost(rs.getString("gost"));
            u.setTermin(rs.getTimestamp("termin"));

            Par par = new Par();
            par.setIdPar(rs.getInt("idPar"));
            par.setIdTiket(rs.getInt("idTiket"));
            par.setUtakmica(u);
            String tipOpkladeStr = rs.getString("tipOpklade");
            if (tipOpkladeStr != null) {
                par.setTipOpklade(TipOpklade.valueOf(tipOpkladeStr));
            } else {
                par.setTipOpklade(null);
            }
            par.setKvota(rs.getDouble("kvota"));
            par.setRedosled(rs.getInt("redosled"));

            lista.add(par);
        }
        return lista;
    }

    /**
     * Konvertuje ResultSet u jedan Par objekat.
     *
     * @param rs ResultSet iz baze podataka
     * @return Par objekat
     * @throws SQLException ako dodje do greske prilikom citanja ResultSet-a
     */
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Utakmica u = new Utakmica();
        u.setIdUtakmica(rs.getInt("idUtakmica"));
        u.setDomacin(rs.getString("domacin"));
        u.setGost(rs.getString("gost"));
        u.setTermin(rs.getTimestamp("termin"));

        Par par = new Par();
        par.setIdPar(rs.getInt("idPar"));
        par.setIdTiket(rs.getInt("idTiket"));
        par.setUtakmica(u);
        String tipOpkladeStr = rs.getString("tipOpklade");
        if (tipOpkladeStr != null) {
            par.setTipOpklade(TipOpklade.valueOf(tipOpkladeStr));
        } else {
            par.setTipOpklade(null);
        }
        par.setKvota(rs.getDouble("kvota"));
        par.setRedosled(rs.getInt("redosled"));

        return par;
    }
}
