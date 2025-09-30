/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

/**
 *
 * @author Darko
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.Tiket;
import java.sql.Statement;

public class DBBroker {
    
    private static DBBroker instance;
    private Connection connection;
    
    private DBBroker() throws SQLException{
        connection = Konekcija.getInstance().getConnection();
    }
    
    public static DBBroker getInstance() throws SQLException{
        if(instance == null){
            instance = new DBBroker();
        }
        return instance;
    }
    

    public int kreiraj(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "INSERT INTO " + odo.getNazivTabele() + " (" 
                     + odo.getKoloneZaInsert() + ") VALUES (" 
                     + odo.getVrednostiZaInsert() + ")";
        System.out.println(query);

        try (PreparedStatement ps = Konekcija.getInstance()
                .getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // vraća ID
                }
            }
        }

        return -1; // ako iz nekog razloga ne dobije ID
    }

    
    public OpstiDomenskiObjekat vratiJednog(OpstiDomenskiObjekat odo) throws SQLException {
        
        String query;
        PreparedStatement stmt;
        
        if(odo instanceof Radnik){
            Radnik r = (Radnik) odo;
            if(r.getIdRadnik() == 0){
                query = "SELECT " + odo.getUslov() + " FROM radnik WHERE korisnickoIme = ? AND lozinka = ?";
                stmt = connection.prepareStatement(query);
                stmt.setString(1, r.getKorisnickoIme());
                stmt.setString(2, r.getLozinka());
            }
            else{
                query = "SELECT " + odo.getUslov() + " FROM " + odo.getNazivTabele() + " " + odo.alies() + " " + odo.getJoinUslov() +
                    " WHERE " + odo.requeredUslov();
                stmt = connection.prepareStatement(query);
            }
        }else{
            query = "SELECT " + odo.getUslov() + " FROM " + odo.getNazivTabele() + " " + odo.alies() + " " + odo.getJoinUslov() +
                    " WHERE " + odo.requeredUslov();
            stmt = connection.prepareStatement(query);
        }
        
        System.out.println(query);

        if(!(odo instanceof Tiket)){
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return odo.konvertujResultSetUObjekat(rs);
                } else {
                    return null;
                }
            }
        }else{
            try (ResultSet rs = stmt.executeQuery()){
                return odo.konvertujResultSetUObjekat(rs);
            }
        }
        
    }

    public List<OpstiDomenskiObjekat> pretrazi(OpstiDomenskiObjekat odo) throws SQLException {
        
        String query = "SELECT " + odo.getUslov() + " FROM " + odo.getNazivTabele() + " " + odo.alies() + " " + odo.getJoinUslov();
        String uslov = odo.getUslovZaPretragu();
        
        if (uslov != null && !uslov.trim().isEmpty() && !uslov.equalsIgnoreCase("ALL")) {
            query += " WHERE " + uslov;
        }

        System.out.println("SQL query: " + query);

        try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {
            return odo.konvertujResultSetUListu(rs);
        }
    }

    public void izmeni(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "UPDATE " + odo.getNazivTabele() + " " + odo.alies() + " SET " + odo.getVrednostiZaUpdate() + " WHERE " + odo.requeredUslov();
        System.out.println(query);
        try (Statement s = connection.createStatement()) {
            s.executeUpdate(query);
        }
    }

    public void obrisi(OpstiDomenskiObjekat odo) throws SQLException {
        String query = "DELETE FROM " + odo.getNazivTabele() + " WHERE " + odo.requeredUslov();
        try (Statement s = connection.createStatement()) {
            s.executeUpdate(query);
        }
    }

}