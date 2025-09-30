/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.Par;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Darko
 */
public class ParTableModel extends AbstractTableModel{

    private final String[] kolone = {"Redni broj", "Utakmica", "Tip opklade", "Kvota"};
    private List<Par> parovi;

    public ParTableModel(List<Par> parovi) {
        this.parovi = parovi;
    }
    
    @Override
    public int getRowCount() {
        return parovi.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Par p = parovi.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return p.getRedosled();
            case 1:
                return p.getUtakmica(); // ako klasa Utakmica ima naziv
            case 2:
                return p.getTipOpklade();
            case 3:
                return p.getKvota();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public void fireTableDataChangedCustom() {
        fireTableDataChanged();
    }
    
}
