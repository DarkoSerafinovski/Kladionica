package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OpstiDomenskiObjekat {
    
    String getNazivTabele();
    String alies();
    String getJoinUslov();
    String getKoloneZaInsert();
    String getVrednostiZaInsert();
    String getVrednostiZaUpdate();
    String requeredUslov();
    String getUslovZaPretragu();
    String getUslov();
    ArrayList<OpstiDomenskiObjekat> konvertujResultSetUListu(ResultSet rs) throws SQLException;

    public OpstiDomenskiObjekat konvertujResultSetUObjekat(ResultSet rs) throws SQLException;
}