/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domen.Tiket;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Darko
 */
public class JsonUtil {
    
    public static void sacuvajTiketUJson(Tiket tiket) {
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writter = new FileWriter("tiket.json");
            gson.toJson(tiket, writter);
            writter.close();
            System.out.println("Tiket sacuvan u JSON fajl");
        }catch(IOException e){
            System.out.println("Greska pri cuvanju JSON fajla: " + e.getMessage());
        }
    }
    
    public static Tiket ucitajTiketIzJson(){
        Gson gson = new Gson();
        try(FileReader reader = new FileReader("tiket.json")){
            Tiket tiket = gson.fromJson(reader, Tiket.class);
            System.out.println("Tiket uspesno ucitan i JSON fajla");
            return tiket;
        }catch(IOException e){
            System.err.println("Nije moguce ucitati tiket iz JSON fajla: " + e.getMessage());
            return null;
        }
        
    }
    
}
