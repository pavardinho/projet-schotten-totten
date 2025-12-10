package com.schottenTotten.view;

import java.util.List;

import com.schottenTotten.model.*;
import com.schottenTotten.controller.*;

public class VueConsole implements Vue {

    private static final String MARGE = "      ";

    private static final int LARGEUR_CARTE = 15;

    public void afficherMenuPrincipal(){
        System.out.println("\n" + MARGE + "#####  MENU PRINCIPAL #####");
        System.out.println(MARGE + "[J] Jouer");
        System.out.println(MARGE + "[P] Paramètres");
        System.out.println(MARGE + "[Q] Quitter");
        System.out.print("\n" + MARGE + "Que voulez-vous faire ? ");

    }

    public void afficherPlateau(Plateau p, ModeDeJeu mode){
        System.out.println("\n");
        System.out.println(MARGE + "                       #####################  PLATEAU  ##################### ");

        int cartesMax = mode.getCartesMaxParBornes();
        int largeurZone = cartesMax * LARGEUR_CARTE;

        System.out.printf(MARGE + "%45s     %s     %-45s%n", "Joueur 1", "BORNES", "Joueur 2");
        System.out.println("");

        List<Borne> bs = p.getBornes();

        for (Borne b : bs) {

            int num = b.getNumero();
            String affichageBorne = "";
            Borne.Statut st = b.getStatut();

            if (st == Borne.Statut.NON_RECLAMEE) {
                affichageBorne = String.format("( %d )", num);
            } else if (st == Borne.Statut.RECLAMEE_J1) {
                affichageBorne = String.format("\u001B[32m< %d ]\u001B[0m", num);
            } else if (st == Borne.Statut.RECLAMEE_J2) {
                affichageBorne = String.format("\u001B[31m[ %d >\u001B[0m", num);
            }


            // Construction affichage joueur 1
            StringBuilder sb1 = new StringBuilder();
            List<Carte> l1 = b.getCarteJoueur1();

            int nbCartel1 = l1.size();
            for (int i = 0; i < cartesMax - nbCartel1; i++) {
                sb1.append(String.format("%" + LARGEUR_CARTE + "s", ""));
            }
            for (Carte c : l1) {
                sb1.append(formaterCarte(c, true));
            }

            // Construction affichage joueur 2
            StringBuilder sb2 = new StringBuilder();
            List<Carte> l2 = b.getCarteJoueur2();

            int nbCartel2 = l2.size();
            for (Carte c : l2) {
                sb2.append(formaterCarte(c, false));
            }

            for (int i = 0; i < cartesMax - l2.size(); i++) {
                sb2.append(String.format("%-" + LARGEUR_CARTE + "s", ""));
            }
            
            System.out.println(MARGE + sb1.toString() + "  " + affichageBorne + "  " + sb2.toString());
        }
        System.out.println("\n" + MARGE + "Pioche : " + p.getPioche().size() + " carte(s) restante(s)");
    }

    private String formaterCarte(Carte c, boolean alignerDroite) {
        if (c == null) return String.format("%" + LARGEUR_CARTE + "s", "");

        String nom = c.getNom();
        
        String rendu = "[" + nom + "]";
        
        if (rendu.length() > LARGEUR_CARTE) {
            return rendu.substring(0, LARGEUR_CARTE);
        }
        
        if (alignerDroite) {
            return String.format("%" + LARGEUR_CARTE + "s", rendu);
        } else {
            return String.format("%-" + LARGEUR_CARTE + "s", rendu);
        }
    }

    public void afficherParamètres(Jeu j){
        System.out.println("\n" + MARGE + "#####  PARAMETRES #####");
        System.out.println(MARGE + "[M] Mode de Jeu : " + j.getModeDeJeu().getName());
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            Joueur joueur = j.getJoueur(i);
            String type = joueur.isAI() ? "(IA)" : "(Humain)";
            System.out.println(MARGE + "Joueur " + (i+1) + " : " + joueur.getNom() + " " + type);
        }
        System.out.println(MARGE + "[Q] Quitter");
        System.out.println(MARGE + "[ ] Que voulez-vous faire ?");
    }

    public void afficherMain(String joueurName, List<Carte> main){
        System.out.println("\n" + MARGE + "--- Main de " + joueurName + " ---");
        System.out.print(MARGE); // Décalage de la main
        for (int i = 0; i < main.size(); i++) {
            System.out.print(String.format("[%d] %-15s  ", (i+1), main.get(i).getNom()));
            if ((i + 1) % 3 == 0) System.out.print("\n" + MARGE);
        }
        System.out.println();
    }
}
