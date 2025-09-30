/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darko
 */
public class Grad implements Serializable, OpstiDomenskiObjekat{
    
    private int idGrad;
    private String naziv;
    private String postanskiBroj;

    public Grad(int idGrad, String naziv, String postanskiBroj) {
        this.idGrad = idGrad;
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }

    public Grad() {
        
    }
    
    public int getIdGrad() {
        return idGrad;
    }

    public void setIdGrad(int idGrad) {
        this.idGrad = idGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    @Override
    public String toString() {
        return naziv;
        
    }

    @Override
    public String getNazivTabele() {
        return "grad";
    }

    @Override
    public String alies() {
        return "g";
    }

    @Override
    public String getJoinUslov() {
        return "";
    }

    @Override
    public String getKoloneZaInsert() {
        return "naziv, postanskiBroj";
    }

    @Override
    public String getVrednostiZaInsert() {
        return "'" + naziv + "', '" + postanskiBroj + "'";
    }

    @Override
    public String getVrednostiZaUpdate() {
        return "naziv = '" + naziv + "', postanskiBroj = '" + postanskiBroj + "'";
    }

    @Override
    public String requeredUslov() {
        return "g.gradID = " + idGrad;
    }

    @Override
    public String getUslovZaPretragu() {
        if (naziv != null && !naziv.isEmpty()) {
            return "g.naziv LIKE '%" + naziv + "%'";
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
            Grad grad = new Grad();
            grad.setIdGrad(rs.getInt("idGrad"));
            grad.setNaziv(rs.getString("naziv"));
            grad.setPostanskiBroj(rs.getString("postanskiBroj"));
            lista.add(grad);
        }
        return lista;
    }

    @Override
    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
}