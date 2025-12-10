package com.schottenTotten.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.schottenTotten.ai.Aleatoire;
import com.schottenTotten.ai.Combine;
import com.schottenTotten.ai.Strategie;
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

    public void jouerCarte(Joueur j, Carte c, Borne b){
        j.retirerCarte(c);
        b.addCarte(j, c);
    }

    public void joueTour(Scanner sc, Joueur j) {
        System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+mode.getNbCarteMain()+") : ");
        int e = sc.nextInt();
        while (!(0 < e && e <= mode.getNbCarteMain())) {
            System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+mode.getNbCarteMain()+") : ");
            e = sc.nextInt();
        }

        Carte c = j.getMain().get(e-1);

        System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+mode.getNbBornes()+") : ");
        e = sc.nextInt();
        while (!(0 < e && e <= mode.getNbBornes())) {
            System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+mode.getNbBornes()+") : ");
            e = sc.nextInt();
        }

        while (plateau.getBornes().get(e-1).estPleine(j) || !(0 < e && e <= mode.getNbBornes())) {
            System.out.println("La borne choisit est déja remplie, choisissez-en une autre (1-"+mode.getNbBornes()+") : ");
            e = sc.nextInt();
        }

        Borne b = plateau.getBornes().get(e-1);
        jouerCarte(j, c, b);

        // On regarde si on peut reclamer des bornes 

        List<Borne> bornes_reclamable = new ArrayList<Borne>();

        for (Borne borne : plateau.getBornes()) {
            if (mode.peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
                bornes_reclamable.add(borne);
            }
        }

        if (bornes_reclamable.size() > 0) {
            System.out.println("Tu peux réclamer les bornes suivantes : ");
            for (Borne borne : bornes_reclamable) {
                System.out.println(borne.getNumero());
            }
            System.out.println("Veux-tu en réclamer une ? ('N' si tu ne veux pas/plus en réclamer sinon entre le numéro de la borne que tu veux réclamer)");
            String s = sc.next();
            while (!s.equals("N")) {

                int num = Integer.parseInt(s);
                boolean peutChoisir = false;
                for (Borne borne : bornes_reclamable) {
                    if (borne.getNumero() == num) {
                        peutChoisir = true;
                        borne.reclamerBorne(j);
                        j.addNbBornesControllees();
                        System.out.println("Borne revendiquée ! Veux tu en revendiquer d'autre ?");
                    }
                }
                if (!peutChoisir) {
                    System.out.println("Tu ne peux pas choisir cette Borne");
                }
                s = sc.next();
            }
        }

        if (plateau.getNbCartesPioche() > 0) {
            j.recevoirCarte(plateau.piocher());
        }
    }

    public void joueTourIA(Joueur j) {
        
        Strategie s = j.getStrategie();
        Action a = s.choisirAction(this, j);
        jouerCarte(j, a.getCarte(), a.getBorne());

        // On regarde si on peut reclamer des bornes 
        List<Borne> bornes_reclamable = new ArrayList<Borne>();

        for (Borne borne : plateau.getBornes()) {
            if (mode.peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
                bornes_reclamable.add(borne);
            }
        }

        for (Borne borne : bornes_reclamable) {
            borne.reclamerBorne(j);
            j.addNbBornesControllees();
        }

        if (plateau.getNbCartesPioche() > 0) {
            j.recevoirCarte(plateau.piocher());
        }
    }

    public boolean estTerminee(Joueur j, Plateau p){

        if (j.getNbBornesControllees() == 5) {
            return true;
        }
        else {
            List<Borne> bornes = p.getBornes();

            for (int i = 0; i < bornes.size() - 2; i++) {
                if (bornes.get(i).estControleeParJoueur(j) && bornes.get(i+1).estControleeParJoueur(j) && bornes.get(i+2).estControleeParJoueur(j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void boucleJeu(Scanner sc){
        
        mode.initialiserJeu(this);
        
        boolean finJeu = false;
        while (!finJeu) {
            Joueur j = getJoueur(indexJoueurCourant);

            if (j.isAI()){
                joueTourIA(j);
            } else {
                vue.afficherPlateau(plateau, mode);
                vue.afficherMain(j.getNom(), j.getMain());
                
                joueTour(sc, j);
            }

            finJeu = estTerminee(j, plateau);

            if (!finJeu) {
                indexJoueurCourant = (indexJoueurCourant + 1) % nbJoueurs;
            }
            else{
                vue.afficherPlateau(plateau, mode);
                System.out.println(j.getNom()+" a gagner !");
            }
        }
    }

    public void boucleChoixModeDeJeu(Scanner sc){
        boolean finParam = false;
        while (!finParam) {
            vue.afficherChoixModeDeJeux(this.mode);
            String e = sc.nextLine();

            if (e.equals("C")) {
                mode = new ModeClassique();
            } else if (e.equals("T")){
                mode = new ModeTactique();
            } else if (e.equals("R")){
                finParam = true;
            }
        }
    }

    public void boucleChoixJoueur(Scanner sc, Joueur j, int i){
        boolean finParam = false;
        while (!finParam) {
            vue.afficherChoixJoueur(j, i);
            String e = sc.nextLine();

            if (e.equals("N")) {
                System.out.println("Entrez le pseudo : ");
                j.setNom(sc.nextLine());
            } else if (e.equals("S")){
                j.setStrategie(null);
            } else if (e.equals("A")){
                j.setStrategie(new Aleatoire());
            } else if (e.equals("C")){
                j.setStrategie(new Combine());
            } else if (e.equals("R")){
                finParam = true;
            }
        }
    }

    public void boucleParametres(Scanner sc){
        boolean finParam = false;
        while (!finParam) {
            vue.afficherParamètres(this);
            String e = sc.nextLine();

            if (e.equals("M")) {
                boucleChoixModeDeJeu(sc);
            } else if (e.equals("1")){
                boucleChoixJoueur(sc, this.joueurs[0], 1);
            } else if (e.equals("2")){
                boucleChoixJoueur(sc, this.joueurs[1], 2);
            } else if (e.equals("Q")){
                finParam = true;
            }
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
        /*for (int i = 0; i < joueurs.length; i++) {
           this.joueurs[i] = new Joueur("Joueur "+(i+1), null);
        }*/
        this.joueurs[0] = new Joueur("T", new Combine());
        this.joueurs[1] = new Joueur("CACA", new Combine());
        this.indexJoueurCourant = 0;
        this.plateau = new Plateau(mode.getNbBornes());
        this.vue = new VueConsole();
    }
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        jeu.bouclePrincipale();
    }
}
