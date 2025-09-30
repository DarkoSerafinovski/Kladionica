/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import forme.KlijentskaForma;

/**
 *
 * @author Darko
 */
public class StartKlijent {
    public static void main(String[] args) {
        try {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new KlijentskaForma().setVisible(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
