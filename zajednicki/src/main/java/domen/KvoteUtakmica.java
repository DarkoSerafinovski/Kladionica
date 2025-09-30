/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Darko
 */
public class KvoteUtakmica {
    private Map<Integer, Map<TipOpklade, Double>> kvotePoUtakmici = new HashMap<>();

    public KvoteUtakmica() {

        dodajKvote(1, TipOpklade.POBEDA_DOMACIN, 1.5);
        dodajKvote(1, TipOpklade.POBEDA_GOST, 2.8);
        dodajKvote(1, TipOpklade.NERESENO, 3.2);
        dodajKvote(1, TipOpklade.ILI_1X, 1.8);
        dodajKvote(1, TipOpklade.ILI_X2, 2.1);
        dodajKvote(1, TipOpklade.ILI_12, 1.6);
        dodajKvote(1, TipOpklade.GG, 1.9);
        dodajKvote(1, TipOpklade.NG, 1.85);
        dodajKvote(1, TipOpklade.VISE_OD_2_5, 2);
        dodajKvote(1, TipOpklade.GG_VISE_OD_2_5, 2.3);

        dodajKvote(2, TipOpklade.POBEDA_DOMACIN, 1.9);
        dodajKvote(2, TipOpklade.POBEDA_GOST, 2);
        dodajKvote(2, TipOpklade.NERESENO, 3.5);
        dodajKvote(2, TipOpklade.ILI_1X, 1.7);
        dodajKvote(2, TipOpklade.ILI_X2, 2.2);
        dodajKvote(2, TipOpklade.ILI_12, 1.8);
        dodajKvote(2, TipOpklade.GG, 2.1);
        dodajKvote(2, TipOpklade.NG, 1.95);
        dodajKvote(2, TipOpklade.VISE_OD_2_5, 2.3);
        dodajKvote(2, TipOpklade.GG_VISE_OD_2_5, 2.4);
        
        dodajKvote(3, TipOpklade.POBEDA_DOMACIN, 1.8);
        dodajKvote(3, TipOpklade.POBEDA_GOST, 2.2);
        dodajKvote(3, TipOpklade.NERESENO, 3.1);
        dodajKvote(3, TipOpklade.ILI_1X, 1.7);
        dodajKvote(3, TipOpklade.ILI_X2, 2.0);
        dodajKvote(3, TipOpklade.ILI_12, 1.6);
        dodajKvote(3, TipOpklade.GG, 1.9);
        dodajKvote(3, TipOpklade.NG, 1.85);
        dodajKvote(3, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(3, TipOpklade.GG_VISE_OD_2_5, 2.3);

        dodajKvote(4, TipOpklade.POBEDA_DOMACIN, 2.0);
        dodajKvote(4, TipOpklade.POBEDA_GOST, 1.9);
        dodajKvote(4, TipOpklade.NERESENO, 3.2);
        dodajKvote(4, TipOpklade.ILI_1X, 1.8);
        dodajKvote(4, TipOpklade.ILI_X2, 2.1);
        dodajKvote(4, TipOpklade.ILI_12, 1.7);
        dodajKvote(4, TipOpklade.GG, 2.0);
        dodajKvote(4, TipOpklade.NG, 1.9);
        dodajKvote(4, TipOpklade.VISE_OD_2_5, 2.2);
        dodajKvote(4, TipOpklade.GG_VISE_OD_2_5, 2.4);

        dodajKvote(5, TipOpklade.POBEDA_DOMACIN, 1.6);
        dodajKvote(5, TipOpklade.POBEDA_GOST, 2.5);
        dodajKvote(5, TipOpklade.NERESENO, 3.3);
        dodajKvote(5, TipOpklade.ILI_1X, 1.7);
        dodajKvote(5, TipOpklade.ILI_X2, 2.0);
        dodajKvote(5, TipOpklade.ILI_12, 1.5);
        dodajKvote(5, TipOpklade.GG, 1.8);
        dodajKvote(5, TipOpklade.NG, 1.9);
        dodajKvote(5, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(5, TipOpklade.GG_VISE_OD_2_5, 2.2);

        dodajKvote(6, TipOpklade.POBEDA_DOMACIN, 2.1);
        dodajKvote(6, TipOpklade.POBEDA_GOST, 1.8);
        dodajKvote(6, TipOpklade.NERESENO, 3.0);
        dodajKvote(6, TipOpklade.ILI_1X, 1.9);
        dodajKvote(6, TipOpklade.ILI_X2, 2.2);
        dodajKvote(6, TipOpklade.ILI_12, 1.7);
        dodajKvote(6, TipOpklade.GG, 2.0);
        dodajKvote(6, TipOpklade.NG, 1.85);
        dodajKvote(6, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(6, TipOpklade.GG_VISE_OD_2_5, 2.3);
        
        dodajKvote(7, TipOpklade.POBEDA_DOMACIN, 1.9);
        dodajKvote(7, TipOpklade.POBEDA_GOST, 2.0);
        dodajKvote(7, TipOpklade.NERESENO, 3.2);
        dodajKvote(7, TipOpklade.ILI_1X, 1.8);
        dodajKvote(7, TipOpklade.ILI_X2, 2.1);
        dodajKvote(7, TipOpklade.ILI_12, 1.7);
        dodajKvote(7, TipOpklade.GG, 2.0);
        dodajKvote(7, TipOpklade.NG, 1.9);
        dodajKvote(7, TipOpklade.VISE_OD_2_5, 2.2);
        dodajKvote(7, TipOpklade.GG_VISE_OD_2_5, 2.4);

        // Utakmica 8
        dodajKvote(8, TipOpklade.POBEDA_DOMACIN, 1.7);
        dodajKvote(8, TipOpklade.POBEDA_GOST, 2.3);
        dodajKvote(8, TipOpklade.NERESENO, 3.1);
        dodajKvote(8, TipOpklade.ILI_1X, 1.6);
        dodajKvote(8, TipOpklade.ILI_X2, 2.0);
        dodajKvote(8, TipOpklade.ILI_12, 1.5);
        dodajKvote(8, TipOpklade.GG, 1.8);
        dodajKvote(8, TipOpklade.NG, 1.9);
        dodajKvote(8, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(8, TipOpklade.GG_VISE_OD_2_5, 2.1);

        // Utakmica 9
        dodajKvote(9, TipOpklade.POBEDA_DOMACIN, 2.0);
        dodajKvote(9, TipOpklade.POBEDA_GOST, 1.8);
        dodajKvote(9, TipOpklade.NERESENO, 3.0);
        dodajKvote(9, TipOpklade.ILI_1X, 1.9);
        dodajKvote(9, TipOpklade.ILI_X2, 2.2);
        dodajKvote(9, TipOpklade.ILI_12, 1.7);
        dodajKvote(9, TipOpklade.GG, 2.0);
        dodajKvote(9, TipOpklade.NG, 1.85);
        dodajKvote(9, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(9, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 10
        dodajKvote(10, TipOpklade.POBEDA_DOMACIN, 1.6);
        dodajKvote(10, TipOpklade.POBEDA_GOST, 2.4);
        dodajKvote(10, TipOpklade.NERESENO, 3.3);
        dodajKvote(10, TipOpklade.ILI_1X, 1.7);
        dodajKvote(10, TipOpklade.ILI_X2, 2.0);
        dodajKvote(10, TipOpklade.ILI_12, 1.5);
        dodajKvote(10, TipOpklade.GG, 1.9);
        dodajKvote(10, TipOpklade.NG, 1.8);
        dodajKvote(10, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(10, TipOpklade.GG_VISE_OD_2_5, 2.2);

        // Utakmica 11
        dodajKvote(11, TipOpklade.POBEDA_DOMACIN, 1.8);
        dodajKvote(11, TipOpklade.POBEDA_GOST, 2.2);
        dodajKvote(11, TipOpklade.NERESENO, 3.0);
        dodajKvote(11, TipOpklade.ILI_1X, 1.7);
        dodajKvote(11, TipOpklade.ILI_X2, 2.1);
        dodajKvote(11, TipOpklade.ILI_12, 1.6);
        dodajKvote(11, TipOpklade.GG, 1.9);
        dodajKvote(11, TipOpklade.NG, 1.85);
        dodajKvote(11, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(11, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 12
        dodajKvote(12, TipOpklade.POBEDA_DOMACIN, 1.9);
        dodajKvote(12, TipOpklade.POBEDA_GOST, 1.9);
        dodajKvote(12, TipOpklade.NERESENO, 3.2);
        dodajKvote(12, TipOpklade.ILI_1X, 1.8);
        dodajKvote(12, TipOpklade.ILI_X2, 2.0);
        dodajKvote(12, TipOpklade.ILI_12, 1.7);
        dodajKvote(12, TipOpklade.GG, 2.0);
        dodajKvote(12, TipOpklade.NG, 1.9);
        dodajKvote(12, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(12, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 13
        dodajKvote(13, TipOpklade.POBEDA_DOMACIN, 2.0);
        dodajKvote(13, TipOpklade.POBEDA_GOST, 1.8);
        dodajKvote(13, TipOpklade.NERESENO, 3.1);
        dodajKvote(13, TipOpklade.ILI_1X, 1.9);
        dodajKvote(13, TipOpklade.ILI_X2, 2.2);
        dodajKvote(13, TipOpklade.ILI_12, 1.7);
        dodajKvote(13, TipOpklade.GG, 2.0);
        dodajKvote(13, TipOpklade.NG, 1.85);
        dodajKvote(13, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(13, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 14
        dodajKvote(14, TipOpklade.POBEDA_DOMACIN, 1.7);
        dodajKvote(14, TipOpklade.POBEDA_GOST, 2.3);
        dodajKvote(14, TipOpklade.NERESENO, 3.2);
        dodajKvote(14, TipOpklade.ILI_1X, 1.6);
        dodajKvote(14, TipOpklade.ILI_X2, 2.0);
        dodajKvote(14, TipOpklade.ILI_12, 1.5);
        dodajKvote(14, TipOpklade.GG, 1.8);
        dodajKvote(14, TipOpklade.NG, 1.9);
        dodajKvote(14, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(14, TipOpklade.GG_VISE_OD_2_5, 2.1);

        // Utakmica 15
        dodajKvote(15, TipOpklade.POBEDA_DOMACIN, 1.9);
        dodajKvote(15, TipOpklade.POBEDA_GOST, 2.0);
        dodajKvote(15, TipOpklade.NERESENO, 3.0);
        dodajKvote(15, TipOpklade.ILI_1X, 1.8);
        dodajKvote(15, TipOpklade.ILI_X2, 2.1);
        dodajKvote(15, TipOpklade.ILI_12, 1.7);
        dodajKvote(15, TipOpklade.GG, 2.0);
        dodajKvote(15, TipOpklade.NG, 1.85);
        dodajKvote(15, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(15, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 16
        dodajKvote(16, TipOpklade.POBEDA_DOMACIN, 1.8);
        dodajKvote(16, TipOpklade.POBEDA_GOST, 2.2);
        dodajKvote(16, TipOpklade.NERESENO, 3.1);
        dodajKvote(16, TipOpklade.ILI_1X, 1.7);
        dodajKvote(16, TipOpklade.ILI_X2, 2.0);
        dodajKvote(16, TipOpklade.ILI_12, 1.6);
        dodajKvote(16, TipOpklade.GG, 1.9);
        dodajKvote(16, TipOpklade.NG, 1.85);
        dodajKvote(16, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(16, TipOpklade.GG_VISE_OD_2_5, 2.3);
        
        // Utakmica 17
        dodajKvote(17, TipOpklade.POBEDA_DOMACIN, 2.0);
        dodajKvote(17, TipOpklade.POBEDA_GOST, 1.8);
        dodajKvote(17, TipOpklade.NERESENO, 3.1);
        dodajKvote(17, TipOpklade.ILI_1X, 1.9);
        dodajKvote(17, TipOpklade.ILI_X2, 2.2);
        dodajKvote(17, TipOpklade.ILI_12, 1.7);
        dodajKvote(17, TipOpklade.GG, 2.0);
        dodajKvote(17, TipOpklade.NG, 1.85);
        dodajKvote(17, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(17, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 18
        dodajKvote(18, TipOpklade.POBEDA_DOMACIN, 1.7);
        dodajKvote(18, TipOpklade.POBEDA_GOST, 2.3);
        dodajKvote(18, TipOpklade.NERESENO, 3.0);
        dodajKvote(18, TipOpklade.ILI_1X, 1.6);
        dodajKvote(18, TipOpklade.ILI_X2, 2.0);
        dodajKvote(18, TipOpklade.ILI_12, 1.5);
        dodajKvote(18, TipOpklade.GG, 1.8);
        dodajKvote(18, TipOpklade.NG, 1.9);
        dodajKvote(18, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(18, TipOpklade.GG_VISE_OD_2_5, 2.2);

        // Utakmica 19
        dodajKvote(19, TipOpklade.POBEDA_DOMACIN, 1.9);
        dodajKvote(19, TipOpklade.POBEDA_GOST, 2.1);
        dodajKvote(19, TipOpklade.NERESENO, 3.2);
        dodajKvote(19, TipOpklade.ILI_1X, 1.8);
        dodajKvote(19, TipOpklade.ILI_X2, 2.1);
        dodajKvote(19, TipOpklade.ILI_12, 1.7);
        dodajKvote(19, TipOpklade.GG, 2.0);
        dodajKvote(19, TipOpklade.NG, 1.9);
        dodajKvote(19, TipOpklade.VISE_OD_2_5, 2.1);
        dodajKvote(19, TipOpklade.GG_VISE_OD_2_5, 2.3);

        // Utakmica 20
        dodajKvote(20, TipOpklade.POBEDA_DOMACIN, 1.8);
        dodajKvote(20, TipOpklade.POBEDA_GOST, 2.2);
        dodajKvote(20, TipOpklade.NERESENO, 3.0);
        dodajKvote(20, TipOpklade.ILI_1X, 1.7);
        dodajKvote(20, TipOpklade.ILI_X2, 2.0);
        dodajKvote(20, TipOpklade.ILI_12, 1.6);
        dodajKvote(20, TipOpklade.GG, 1.9);
        dodajKvote(20, TipOpklade.NG, 1.85);
        dodajKvote(20, TipOpklade.VISE_OD_2_5, 2.0);
        dodajKvote(20, TipOpklade.GG_VISE_OD_2_5, 2.3);


    }

    private void dodajKvote(int idUtakmica, TipOpklade tip, double kvota) {
        kvotePoUtakmici.putIfAbsent(idUtakmica, new HashMap<>());
        kvotePoUtakmici.get(idUtakmica).put(tip, kvota);
    }

    public Map<TipOpklade, Double> getKvoteZaUtakmicu(int idUtakmica) {
        return kvotePoUtakmici.getOrDefault(idUtakmica, new HashMap<>());
    }
}

