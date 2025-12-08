package com.schottenTotten.controller;

import java.util.List;

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


    public boolean peutReclamerBorne(List<Carte> cj_reclame, List<Carte> cj_autre, Borne b){
        
        if (b.getStatut() != Borne.Statut.NON_RECLAMEE || cj_reclame.size() != 3 || cj_autre.size() != 3) {
            return false;
        }

        Combinaison combj_reclame = new Combinaison(cj_reclame);
        Combinaison combj_autre = new Combinaison(cj_autre);

        int compare = Combinaison.compareCombinaison(combj_reclame, combj_autre);
        
        if (compare > 0) {
            return true;
        } else if (compare < 0){
            return false;
        } else {
            // gerer le cas ou le type et la somme des valeurs et la même -> regardé qui a posé la 3eme carte en premier
            return false;
        }
    }

    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Plateau p){
        return false;
    }

    public String getName(){ return "Classique"; }
}
