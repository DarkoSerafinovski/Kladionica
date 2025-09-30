/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Darko
 */
public class Konekcija {
    
    private static Konekcija instance;
    private Connection connection;
    
    private Konekcija() {
        try {
            Properties prop = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                System.out.println("Ne može se pronaći config.properties");
                return;
            }
            prop.load(input);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        } catch (SQLException | IOException ex) {
            System.out.println("Greška prilikom konekcije: " + ex.getMessage());
        }
    }
    
    public static Konekcija getInstance() throws SQLException{
        if(instance == null || instance.getConnection().isClosed()){
            instance = new Konekcija();
        }return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void commit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
                System.out.println("Commit transakcije uspešan.");
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri commit-u: " + ex.getMessage());
        }
    }

    // rollback transakcije
    public void rollback() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                System.out.println("Rollback transakcije izvršen.");
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri rollback-u: " + ex.getMessage());
        }
    }
    
    public boolean isConnectionValid() {
    try {
        return connection != null && !connection.isClosed() && connection.isValid(2);
    } catch (SQLException ex) {
        System.out.println("Greška pri proveri konekcije: " + ex.getMessage());
        return false;
    }
}

    
    
}
