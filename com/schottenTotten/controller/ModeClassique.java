package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.CarteTactique;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.model.Plateau;

public class ModeClassique implements ModeDeJeu{

    private int nbBornes = 9;
    private int cartesMaxParBornes = 3;

    public int getNbBornes() { return this.nbBornes; }
    public int getCartesMaxParBornes() { return this.cartesMaxParBornes; }


    public void setNbBornes(int nb) { this.nbBornes = nb; }
    public void setCartesMaxParBornes(int nb) { this.cartesMaxParBornes = nb; }

    public void initialiserJeu(Jeu j){
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            
        }
    }


    public boolean peutReclamerBorne(Joueur j, Borne b, Plateau p){
        return false;
    }

    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Plateau p){
        return false;
    }

    public String getName(){ return "Classique"; }
}
