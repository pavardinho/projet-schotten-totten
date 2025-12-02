package com.schottenTotten.controller;

import com.schottenTotten.model.*;

public class ModeClassique implements ModeDeJeu{

    private int nbBornes = 9;
    private int cartesMaxParBornes = 3;
    private int nbCarteMain = 6;

    public int getNbBornes() { return this.nbBornes; }
    public int getCartesMaxParBornes() { return this.cartesMaxParBornes; }
    public int getNbCarteMain() { return this.nbCarteMain; }


    public void setNbBornes(int nb) { this.nbBornes = nb; }
    public void setCartesMaxParBornes(int nb) { this.cartesMaxParBornes = nb; }
    public void setNbCarteMain(int nb) { this.nbCarteMain = nb; }

    public void initialiserJeu(Jeu j){
        Plateau p = j.getPlateau();
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            Joueur joueur = j.getJoueur(i);
            for (int k = 0; k < nbCarteMain; k++) {
                joueur.recevoirCarte(p.piocher());
            }
        }
        for (Borne b : j.getPlateau().getBornes()) {
            b.setJoueurs(j.getJoueur(0), j.getJoueur(1));
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
