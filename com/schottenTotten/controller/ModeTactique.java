package com.schottenTotten.controller;

import java.util.List;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.CarteTactique;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.model.Plateau;

public class ModeTactique implements ModeDeJeu{

    private int nbBornes = 9;
    private int cartesMaxParBornes = 3;
    private int nbCarteMain = 6;

    
    public int getNbBornes() { return this.nbBornes; }
    public int getCartesMaxParBornes() { return this.cartesMaxParBornes; }
    public int getNbCarteMain() { return this.nbCarteMain; }

    public void setNbBornes(int nb) { this.nbBornes = nb; }
    public void setCartesMaxParBornes(int nb) { this.cartesMaxParBornes = nb; }
    public void setNbCarteMain(int nb) {this.nbCarteMain = nb; }

    public void initialiserJeu(Jeu j){

    }

    public boolean peutReclamerBorne(List<Carte> cj_reclame, List<Carte> cj_autre, Borne b){
        return false;
    }

    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Plateau p){
        return false;
    }
    
    public String getName(){ return "Tactique"; }
    
}
