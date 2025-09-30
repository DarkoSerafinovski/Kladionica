/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domen.Grad;
import domen.Korisnik;
import domen.Poslovnica;
import domen.Radnik;
import domen.Tiket;
import domen.Utakmica;
import java.util.ArrayList;
import java.util.List;
import so.SOIzmeniKorisnik;
import so.SOIzmeniPoslovnica;
import so.SOIzmeniTiket;
import so.SOKreirajKorisnika;
import so.SOKreirajTiket;
import so.SOObrisiKorisnika;
import so.SOPretraziKorisnik;
import so.SOPretraziTiket;
import so.SOPrijavaRadnika;
import so.SOUbaciPoslovnica;
import so.SOVratiListuKorisnikKriterijumGrad;
import so.SOVratiListuKorisnikKriterijumKorisnik;
import so.SOVratiListuSviGrad;
import so.SOVratiListuSviKorisnik;
import so.SOVratiListuSviRadnik;
import so.SOVratiListuSviUtakmica;
import so.SOVratiListuTiketKriterijumKorisnik;
import so.SOVratiListuTiketKriterijumRadnik;
import so.SOVratiListuTiketKriterijumTiket;
import so.SOVratiListuTiketKriterijumUtakmica;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Darko
 */
public class ServerController {
    
    private static ServerController instance;
    private List<Radnik> trenutnoUlogovani = new ArrayList<>();

    private ServerController() {
    }

    public List<Radnik> getTrenutnoUlogovani() {
        return trenutnoUlogovani;
    }

    public void setTrenutnoUlogovani(List<Radnik> trenutnoUlogovani) {
        this.trenutnoUlogovani = trenutnoUlogovani;
    }
    
    

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }
    
    public ServerskiOdgovor prijaviRadnik(Radnik radnik) {
    ServerskiOdgovor so = new ServerskiOdgovor();
    try {
        if (trenutnoUlogovani.contains(radnik)) {
            throw new Exception("Radnik je već ulogovan!");
        }

        SOPrijavaRadnika soPrijava = new SOPrijavaRadnika();
        soPrijava.izvrsi(radnik);
        Radnik r = soPrijava.getRadnik();

        if (r != null) {
            trenutnoUlogovani.add(r);
            so.setUspesno(true);
            so.setPoruka("Uspešna prijava.");
            so.setOdgovor(r);
        } else {
            throw new Exception("Pogrešno uneti kredencijali.");
        }

    } catch (Exception e) {
        so.setUspesno(false);
        so.setPoruka(e.getMessage());
        so.setException(e);
    }
    return so;
}
    
    public ServerskiOdgovor kreirajKorisnik(){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOKreirajKorisnika soK = new SOKreirajKorisnika();
            soK.izvrsi(null);
            so.setUspesno(true);
            so.setPoruka("Korisnik je uspesno kreiran sa default vrednostima.");
            so.setOdgovor(soK.getKorisnik());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor obrisiKorisnik(Korisnik k){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            SOObrisiKorisnika soObrisi = new SOObrisiKorisnika();
            soObrisi.izvrsi(k);
            so.setUspesno(true);
            so.setPoruka("Korisnik je uspešno obrisan.");
        } catch (Exception e) {
            so.setUspesno(false);
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("foreign key")) {
                so.setPoruka("Nije moguće obrisati korisnika jer postoje povezani podaci u bazi.");
            } else {
                so.setPoruka("Greška prilikom brisanja korisnika: " + e.getMessage());
            }
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor pretraziKorisnika(Korisnik k) {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            SOPretraziKorisnik soPretraga = new SOPretraziKorisnik();
            soPretraga.izvrsi(k);
            so.setUspesno(true);
            so.setOdgovor(soPretraga.getRezultat());
            so.setPoruka("Pretraga uspešno izvršena.");
        } catch (Exception e) {
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor izmeniKorisnik(Korisnik k) {
    ServerskiOdgovor so = new ServerskiOdgovor();
    try {
        SOIzmeniKorisnik soIzmeni = new SOIzmeniKorisnik();
        soIzmeni.izvrsi(k);
        so.setUspesno(true);
        so.setPoruka("Korisnik je uspešno izmenjen.");
        so.setOdgovor(k);
    } catch (Exception e) {
        so.setUspesno(false);
        if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
            so.setPoruka("JMBG koji ste uneli već postoji u bazi. Unesite jedinstveni JMBG.");
        } else {
            so.setPoruka(e.getMessage());
        }
        so.setException(e);
    }
    return so;
}
    
    public ServerskiOdgovor ubaciPoslovnica(Poslovnica p) throws Exception{
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOUbaciPoslovnica soU = new SOUbaciPoslovnica();
            soU.izvrsi(p);
            so.setUspesno(true);
            so.setPoruka("Poslovnica uspesno ubacen.");
            so.setOdgovor(p);
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka("Neuspesno ubacivanje poslovnica: " + e.getMessage());
        }
        return so;
    }

    public ServerskiOdgovor izmeniPoslovnica(Poslovnica p){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOIzmeniPoslovnica soIzmeni = new SOIzmeniPoslovnica();
            soIzmeni.izvrsi(p);
            so.setUspesno(true);
            so.setPoruka("Poslovnica je uspesno izmenjena.");
            so.setOdgovor(p);
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka("Neuspesno ubacivanje poslovnice: " + e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor kreirajTiket(Tiket t){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOKreirajTiket soK = new SOKreirajTiket();
            soK.izvrsi(t);
            so.setUspesno(true);
            so.setPoruka("Prazan tiket je uspesno kreiran!");
            so.setOdgovor(soK.getTiket());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListaSviRadnik(){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuSviRadnik sov = new SOVratiListuSviRadnik();
            sov.izvrsi(null);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista radnika.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListaSviGrad(){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuSviGrad sov = new SOVratiListuSviGrad();
            sov.izvrsi(null);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista gradova.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListaSviKorisnik(){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuSviKorisnik sov = new SOVratiListuSviKorisnik();
            sov.izvrsi(null);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista korisnika.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListaSviUtakmica(){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuSviUtakmica sov = new SOVratiListuSviUtakmica();
            sov.izvrsi(null);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista utakmica.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuKorisnikKriterijumGrad(Grad g){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuKorisnikKriterijumGrad sov = new SOVratiListuKorisnikKriterijumGrad();
            sov.izvrsi(g);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista korisnika.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuKorisnikKriterijumKorisnik(Korisnik k){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuKorisnikKriterijumKorisnik sov = new SOVratiListuKorisnikKriterijumKorisnik();
            sov.izvrsi(k);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista korisnika.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumRadnik(Radnik r){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuTiketKriterijumRadnik sov = new SOVratiListuTiketKriterijumRadnik();
            sov.izvrsi(r);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista tiketa.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumKorisnik(Korisnik k){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuTiketKriterijumKorisnik sov = new SOVratiListuTiketKriterijumKorisnik();
            sov.izvrsi(k);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista tiketa.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumUtakmica(Utakmica u){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuTiketKriterijumUtakmica sov = new SOVratiListuTiketKriterijumUtakmica();
            sov.izvrsi(u);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista tiketa.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor vratiListuTiketKriterijumTiket(Tiket t){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOVratiListuTiketKriterijumTiket sov = new SOVratiListuTiketKriterijumTiket();
            sov.izvrsi(t);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracena lista tiketa.");
            so.setOdgovor(sov.getLista());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor pretraziTiket(Tiket t){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOPretraziTiket sov = new SOPretraziTiket();
            sov.izvrsi(t);
            so.setUspesno(true);
            so.setPoruka("Uspesno vracen tiket.");
            so.setOdgovor(sov.getRezultat());
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }
    
    public ServerskiOdgovor izmeniTiket(Tiket t){
        ServerskiOdgovor so = new ServerskiOdgovor();
        try{
            SOIzmeniTiket sov = new SOIzmeniTiket();
            sov.izvrsi(t);
            so.setUspesno(true);
            so.setPoruka("Uspesno izmenjen tiket.");
            so.setOdgovor(t);
        }catch(Exception e){
            so.setUspesno(false);
            so.setPoruka(e.getMessage());
            so.setException(e);
        }
        return so;
    }

}
