package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tiket implements Serializable, OpstiDomenskiObjekat {

    private int idTiket;
    private double ulog;
    private double ukupnaKvota = 1;
    private double moguciDobitak;
    private int status = -1;
    private Radnik radnik;
    private Korisnik korisnik;
    private List<Par> parovi;

    public Tiket() {
        parovi = new ArrayList<>();
    }

    public Tiket(int idTiket, double ulog, double ukupnaKvota, double moguciDobitak, int status, Radnik radnik, Korisnik korisnik, List<Par> parovi) {
        this.idTiket = idTiket;
        this.ulog = ulog;
        this.ukupnaKvota = ukupnaKvota;
        this.moguciDobitak = moguciDobitak;
        this.status = status;
        this.radnik = radnik;
        this.korisnik = korisnik;
        this.parovi = parovi;
    }

    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public double getUlog() {
        return ulog;
    }

    public void setUlog(double ulog) {
        this.ulog = ulog;
    }

    public double getUkupnaKvota() {
        return ukupnaKvota;
    }

    public void setUkupnaKvota(double ukupnaKvota) {
        this.ukupnaKvota = ukupnaKvota;
    }

    public double getMoguciDobitak() {
        return moguciDobitak;
    }

    public void setMoguciDobitak(double moguciDobitak) {
        this.moguciDobitak = moguciDobitak;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<Par> getParovi() {
        return parovi;
    }

    public void setParovi(List<Par> parovi) {
        this.parovi = parovi;
    }
    
    public void dodajPar(Par par) {
        if (parovi == null) parovi = new ArrayList<>();
        parovi.add(par);
    }

    @Override
    public String toString() {
        return "Tiket{" +
                "idTiket=" + idTiket +
                ", ulog=" + ulog +
                ", ukupnaKvota=" + ukupnaKvota +
                ", moguciDobitak=" + moguciDobitak +
                ", status=" + status +
                ", radnik=" + (radnik != null ? radnik.getIme() + " " + radnik.getPrezime() : "null") +
                ", korisnik=" + (korisnik != null ? korisnik.getIme() + " " + korisnik.getPrezime() : "null") +
                ", parovi=" + (parovi != null ? parovi.size() + " parova" : "null") +
                '}';
    }



     @Override
    public String getNazivTabele() {
        return "tiket";
    }

    @Override
    public String alies() {
        return "t";
    }

    @Override
    public String getJoinUslov() {
        return " JOIN radnik r ON t.idRadnik = r.idRadnik"
             + " JOIN korisnik k ON t.idKorisnik = k.idKorisnik"
             + " JOIN grad g ON k.idGrad = g.idGrad"
             + " JOIN par p ON t.idTiket = p.idTiket"
             + " JOIN utakmica u ON p.idUtakmica = u.idUtakmica";
    }



    @Override
    public String getKoloneZaInsert() {
        return "ulog, statusTiket, ukupnaKvota, dobitak, idRadnik, idKorisnik";
    }

    @Override
    public String getVrednostiZaInsert() {
        return ulog + ", " + status + ", " + ukupnaKvota + ", " + moguciDobitak + ", " + radnik.getIdRadnik() + ", " + korisnik.getIdKorisnik();
    }


    @Override
    public String getVrednostiZaUpdate() {
        StringBuilder sb = new StringBuilder();

        sb.append("statusTiket = ").append(status);

        if (ulog > 0) {
            sb.append(", ulog = ").append(ulog);
        }

        if (radnik != null && radnik.getIdRadnik() > 0) {
            sb.append(", idRadnik = ").append(radnik.getIdRadnik());
        }
        if (korisnik != null && korisnik.getIdKorisnik() > 0) {
            sb.append(", idKorisnik = ").append(korisnik.getIdKorisnik());
        }

        if (ukupnaKvota > 1) {
            sb.append(", ukupnaKvota = ").append(ukupnaKvota);
        }
        if (moguciDobitak > 0) {
            sb.append(", dobitak = ").append(moguciDobitak);
        }

        return sb.toString();
    }



    @Override
    public String requeredUslov() {
        return "t.idTiket = " + idTiket;
    }

    @Override
    public String getUslovZaPretragu() {
        StringBuilder uslov = new StringBuilder();

        if (radnik != null && radnik.getIdRadnik() > 0) {
            uslov.append("t.idRadnik = ").append(radnik.getIdRadnik());
        }
        if (korisnik != null && korisnik.getIdKorisnik() > 0) {
            if (uslov.length() > 0) uslov.append(" AND ");
            uslov.append("t.idKorisnik = ").append(korisnik.getIdKorisnik());
        }
        if (parovi != null && !parovi.isEmpty() && parovi.get(0).getUtakmica() != null) {
            int idUtakmica = parovi.get(0).getUtakmica().getIdUtakmica();
            if (idUtakmica > 0) {
                if (uslov.length() > 0) uslov.append(" AND ");
                uslov.append("u.idUtakmica = ").append(idUtakmica);
            }
        }
        if (ulog > 0) {
            if (uslov.length() > 0) uslov.append(" AND ");
            uslov.append("t.ulog > ").append(ulog);
        }
        if (ukupnaKvota > 0) {
            if (uslov.length() > 0) uslov.append(" AND ");
            uslov.append("t.ukupnaKvota > ").append(ukupnaKvota);
        }
        if (moguciDobitak > 0) {
            if (uslov.length() > 0) uslov.append(" AND ");
            uslov.append("t.dobitak > ").append(moguciDobitak);
        }
        if (status > -1 && status < 3) {
            if (uslov.length() > 0) uslov.append(" AND ");
            uslov.append("t.statusTiket = '").append(status).append("'");
        }

        return uslov.toString();
    }


    @Override
    public String getUslov() {
        return "t.*,\n" +
                "    r.idRadnik AS idRadnik, r.ime AS imeRadnik, r.prezime AS prezimeRadnik, r.korisnickoIme AS korisnickoIme, r.brojTelefona AS brojTelefona, \n" +
                "    k.idKorisnik AS idKorisnik, k.ime AS imeKorisnik, k.prezime AS prezimeKorisnik, k.jmbg AS jmbg,\n" +
                "    p.idPar AS idPar, p.redosled AS redosled, p.tipOpklade AS tipOpklade, p.kvota AS kvota, \n" +
                "    g.idGrad AS idGrad, g.naziv AS naziv, g.postanskiBroj AS postanskiBroj, \n" +            
                "    u.idUtakmica AS idUtakmica, u.domacin AS domacin, u.gost AS gost, u.termin AS termin";
    }

    @Override
    public ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException {
        Map<Integer, Tiket> mapaTiketa = new HashMap<>();

        while (rs.next()) {
            int idTiket = rs.getInt("idTiket");

            Tiket t = mapaTiketa.get(idTiket);
            if (t == null) {
                t = new Tiket();
                t.setIdTiket(idTiket);
                t.setUlog(rs.getDouble("ulog"));
                t.setUkupnaKvota(rs.getDouble("ukupnaKvota"));
                t.setMoguciDobitak(rs.getDouble("dobitak"));
                t.setStatus(rs.getInt("statusTiket"));

                Radnik r = new Radnik();
                r.setIdRadnik(rs.getInt("idRadnik"));
                r.setIme(rs.getString("imeRadnik"));
                r.setPrezime(rs.getString("prezimeRadnik"));
                r.setKorisnickoIme(rs.getString("korisnickoIme"));
                r.setBrojTelefona(rs.getString("brojTelefona"));
                t.setRadnik(r);

                Grad g = new Grad();
                g.setIdGrad(rs.getInt("idGrad"));
                g.setNaziv(rs.getString("naziv"));
                g.setPostanskiBroj(rs.getString("postanskiBroj"));

                Korisnik k = new Korisnik();
                k.setIdKorisnik(rs.getInt("idKorisnik"));
                k.setIme(rs.getString("imeKorisnik"));
                k.setPrezime(rs.getString("prezimeKorisnik"));
                k.setJmbg(rs.getString("jmbg"));
                k.setGrad(g);
                t.setKorisnik(k);

                t.setParovi(new ArrayList<>());

                mapaTiketa.put(idTiket, t);
            }

            Par p = new Par();
            p.setIdPar(rs.getInt("idPar"));
            p.setRedosled(rs.getInt("redosled"));

            String tipOpkladeStr = rs.getString("tipOpklade");
            if(tipOpkladeStr != null) {
                p.setTipOpklade(TipOpklade.valueOf(tipOpkladeStr.toUpperCase())); 
            } else {
                p.setTipOpklade(null);
            }

            p.setKvota(rs.getDouble("kvota"));

            Utakmica u = new Utakmica();
            u.setIdUtakmica(rs.getInt("idUtakmica"));
            u.setDomacin(rs.getString("domacin"));
            u.setGost(rs.getString("gost"));
            u.setTermin(rs.getTimestamp("termin"));
            p.setUtakmica(u);

            t.getParovi().add(p);
        }
        return new ArrayList<>(mapaTiketa.values());
    }
    
    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        Tiket tiket = null;
        Map<Integer, Tiket> mapaTiketa = new HashMap<>();

        while (rs.next()) {
            int idTiket = rs.getInt("idTiket");

            tiket = mapaTiketa.get(idTiket);
            if (tiket == null) {
                tiket = new Tiket();
                tiket.setIdTiket(idTiket);
                tiket.setUlog(rs.getDouble("ulog"));
                tiket.setUkupnaKvota(rs.getDouble("ukupnaKvota"));
                tiket.setMoguciDobitak(rs.getDouble("dobitak"));
                tiket.setStatus(rs.getInt("statusTiket"));

                // Radnik
                Radnik r = new Radnik();
                r.setIdRadnik(rs.getInt("idRadnik"));
                r.setIme(rs.getString("imeRadnik"));
                r.setPrezime(rs.getString("prezimeRadnik"));
                r.setKorisnickoIme(rs.getString("korisnickoIme"));
                tiket.setRadnik(r);

                // Korisnik + Grad
                Grad g = new Grad();
                g.setIdGrad(rs.getInt("idGrad"));
                g.setNaziv(rs.getString("naziv"));
                g.setPostanskiBroj(rs.getString("postanskiBroj"));

                Korisnik k = new Korisnik();
                k.setIdKorisnik(rs.getInt("idKorisnik"));
                k.setIme(rs.getString("imeKorisnik"));
                k.setPrezime(rs.getString("prezimeKorisnik"));
                k.setJmbg(rs.getString("jmbg"));
                k.setGrad(g);

                tiket.setKorisnik(k);

                tiket.setParovi(new ArrayList<>());
                mapaTiketa.put(idTiket, tiket);
            }

            // Par
            Par p = new Par();
            p.setIdPar(rs.getInt("idPar"));
            Utakmica u = new Utakmica();
            u.setIdUtakmica(rs.getInt("idUtakmica"));
            u.setDomacin(rs.getString("domacin"));
            u.setGost(rs.getString("gost"));
            u.setTermin(rs.getTimestamp("termin"));
            p.setUtakmica(u);
            p.setTipOpklade(Enum.valueOf(TipOpklade.class, rs.getString("tipOpklade")));
            p.setKvota(rs.getDouble("kvota"));
            p.setRedosled(rs.getInt("redosled"));

            tiket.getParovi().add(p);
        }

        if (mapaTiketa.isEmpty()) return null;

        return mapaTiketa.values().iterator().next();
    }
}