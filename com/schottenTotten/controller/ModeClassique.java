package com.schottenTotten.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.schottenTotten.ai.Strategie;
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
        j.setIndexJoueurCourant(0);
        
        Plateau p = new Plateau(nbBornes);
        j.setPlateau(p);
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            Joueur joueur = j.getJoueur(i);
            joueur.resetMain();
            joueur.resetNbBornesControllees();
            joueur.resetNbCartesTactiquesJouees();
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
            return true;
        }
    }

    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Joueur autreJoueur, Plateau p){
        return false;
    }

    public void jouerTour(Jeu jeu, Scanner sc, Joueur j) {
        Plateau plateau = jeu.getPlateau();

        System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+nbCarteMain+") : ");
        int e = sc.nextInt();
        while (!(0 < e && e <= nbCarteMain)) {
            System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+nbCarteMain+") : ");
            e = sc.nextInt();
        }

        Carte c = j.getMain().get(e-1);

        System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
        e = sc.nextInt();
        while (!(0 < e && e <= nbBornes)) {
            System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
            e = sc.nextInt();
        }

        while (plateau.getBornes().get(e-1).estPleine(j) || !(0 < e && e <= nbBornes)) {
            System.out.println("La borne choisit est déja remplie, choisissez-en une autre (1-"+nbBornes+") : ");
            e = sc.nextInt();
        }

        Borne b = plateau.getBornes().get(e-1);
        jeu.jouerCarte(j, c, b);

        // On regarde si on peut reclamer des bornes 

        List<Borne> bornes_reclamable = new ArrayList<Borne>();

        for (Borne borne : plateau.getBornes()) {
            if (peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
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

    public void joueTourIA(Jeu jeu, Joueur j) {
        Plateau plateau = jeu.getPlateau();
        
        Strategie s = j.getStrategie();
        Action a = s.choisirAction(jeu, j);
        if (a!=null){
            jeu.jouerCarte(j, a.getCarte(), a.getBorne());
        }

        // On regarde si on peut reclamer des bornes 
        List<Borne> bornes_reclamable = new ArrayList<Borne>();

        for (Borne borne : plateau.getBornes()) {
            if (peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
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

    public String getName(){ return "Classique"; }
}
