package com.schottenTotten.controller;

import com.schottenTotten.model.*;
import com.schottenTotten.view.*;

public class Jeu {
    
    private Plateau plateau;
    private Joueur[] joueurs;
    private ModeDeJeu mode;
    private int indexJoueurCourant;
    private Vue vue;

    public Plateau getPlateau() { return this.plateau; }
    public Joueur getJoueur(int i) { return this.joueurs[i]; }
    public ModeDeJeu getModeDeJeu() { return this.mode; }
    public int getIndexJoueurCourant() { return this.indexJoueurCourant; }

    public void setPlateau(Plateau p) { this.plateau = p; }
    public void setJoueur(Joueur j, int i) { this.joueurs[i] = j; }
    public void setModeDeJeu(ModeDeJeu m) { this.mode = m; }
    public void setIndexJoueurCourant(int i) { this.indexJoueurCourant = i; }

    public Jeu() {
        this.vue = new VueConsole();
    }
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        jeu.vue.afficherParamètres(jeu);
    }
}
