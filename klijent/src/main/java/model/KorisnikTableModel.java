/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.Korisnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Darko
 */
public class KorisnikTableModel extends AbstractTableModel{
    
    private List<Korisnik> korisnici;
    private String[] kolone = {"ID", "Ime", "Prezime", "JMBG", "Grad"};

    public KorisnikTableModel(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
    
    @Override
    public int getRowCount() {
        return korisnici == null ? 0 : korisnici.size();
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
        Korisnik k = korisnici.get(rowIndex);
        switch (columnIndex) {
            case 0: return k.getIdKorisnik(); 
            case 1: return k.getIme();
            case 2: return k.getPrezime();
            case 3: return k.getJmbg();
            case 4: return k.getGrad().getNaziv();
            default: return "Nepoznato";
        }
    }
}
