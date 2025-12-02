package com.schottenTotten.controller;

import java.util.Scanner;

import com.schottenTotten.model.*;
import com.schottenTotten.view.*;

public class Jeu {

    private Plateau plateau;
    private Joueur[] joueurs;
    private ModeDeJeu mode;
    private int indexJoueurCourant;
    private Vue vue;
    private int nbJoueurs = 2;

    public Plateau getPlateau() { return this.plateau; }
    public Joueur getJoueur(int i) { return this.joueurs[i]; }
    public ModeDeJeu getModeDeJeu() { return this.mode; }
    public int getIndexJoueurCourant() { return this.indexJoueurCourant; }
    public int getNbJoueurs() { return this.nbJoueurs; }

    public void setPlateau(Plateau p) { this.plateau = p; }
    public void setJoueur(Joueur j, int i) { this.joueurs[i] = j; }
    public void setModeDeJeu(ModeDeJeu m) { this.mode = m; }
    public void setIndexJoueurCourant(int i) { this.indexJoueurCourant = i; }
    public void setNbJoueurs(int n) { this.nbJoueurs = n; }

    public boolean aJouerTour(Scanner sc) {
        System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+mode.getNbCarteMain()+") : ");
        int e = sc.nextInt();
        while (!(0 < e && e < mode.getNbCarteMain())) {
            System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+mode.getNbCarteMain()+") : ");
            e = sc.nextInt();
        }

        System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+mode.getNbBornes()+") : ");
        e = sc.nextInt();
        while (!(0 < e && e < mode.getNbBornes())) {
            System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+mode.getNbBornes()+") : ");
        e = sc.nextInt();
        }

        

    }

    public void boucleJeu(Scanner sc){
        
        mode.initialiserJeu(this);
        
        boolean finJeu = false;
        while (!finJeu) {
            vue.afficherPlateau(plateau, mode);
            Joueur j = getJoueur(indexJoueurCourant);
            vue.afficherMain(j.getNom(), j.getMain());
            while (!aJouerTour(sc)) { }


            indexJoueurCourant = (indexJoueurCourant + 1) % nbJoueurs;
        }
    }

    public void boucleParametres(Scanner sc){
        boolean finParam = false;
        while (!finParam) {
            vue.afficherParamètres(this);
            String e = sc.nextLine();
        }
    }

    public void bouclePrincipale() {
        Scanner sc = new Scanner(System.in);
        
        boolean finDeJeu = false;
        while (!finDeJeu) {
            vue.afficherMenuPrincipal();
            String e = sc.nextLine();

            if (e.equals("J")) {
                boucleJeu(sc);
            } else if (e.equals("P")){
                boucleParametres(sc);
            } else if (e.equals("Q")){
                finDeJeu = true;
            }
        }
        sc.close();
    }

    public Jeu() {
        this.mode = new ModeClassique();
        this.joueurs = new Joueur[this.nbJoueurs];
        for (int i = 0; i < joueurs.length; i++) {
            this.joueurs[i] = new Joueur("Joueur "+(i+1), null);
        }
        this.indexJoueurCourant = 0;
        this.plateau = new Plateau(mode.getNbBornes());
        this.vue = new VueConsole();
    }
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        jeu.bouclePrincipale();
    }
}
