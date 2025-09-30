/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.Korisnik;
import domen.Radnik;
import domen.Tiket;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import kontroler.KlijentController;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Darko
 */
public class TiketTableModel extends AbstractTableModel {

    private List<Tiket> tiketi;
    private String[] kolone = {"ID", "Ulog", "Ukupna kvota", "Dobitak", "Status", "Korisnik"};

    public TiketTableModel(List<Tiket> tiketi) {
        this.tiketi = tiketi;
    }

    @Override
    public int getRowCount() {
        return tiketi == null ? 0 : tiketi.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tiket t = tiketi.get(rowIndex);

        switch (columnIndex) {
            case 0: return t.getIdTiket(); // ID tiketa
            case 1: return t.getUlog();
            case 2: return t.getUkupnaKvota();
            case 3: return t.getMoguciDobitak();
            case 4: 
                switch (t.getStatus()) {
                    case 0: return "U toku";
                    case 1: return "Dobitan";
                    case 2: return "Gubitan";
                    default: return "Nepoznat";
                }
            case 5: return t.getKorisnik().toString();
            default: return "Nepoznato";
        }
    }

}

