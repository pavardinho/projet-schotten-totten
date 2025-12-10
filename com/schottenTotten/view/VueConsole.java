package com.schottenTotten.view;

import java.util.List;

import com.schottenTotten.model.*;
import com.schottenTotten.controller.*;

public class VueConsole implements Vue {

    // Codes Couleurs ANSI
    private static final String RESET = "\u001B[0m";
    private static final String ROUGE = "\u001B[31m";
    private static final String VERT = "\u001B[32m";
    private static final String JAUNE = "\u001B[33m";
    private static final String BLEU = "\u001B[34m";
    private static final String VIOLET = "\u001B[35m";
    private static final String ORANGE = "\u001B[38;5;208m"; 
    private static final String GRIS = "\u001B[90m"; 

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

        System.out.printf(MARGE + "%" + (largeurZone - 1) + "s   %s   %-" + largeurZone + "s%n", "Joueur 1", "BORNES", "Joueur 2");
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

            for (int i = 0; i < cartesMax - nbCartel2; i++) {
                sb2.append(String.format("%-" + LARGEUR_CARTE + "s", ""));
            }
            
            System.out.println(MARGE + sb1.toString() + "  " + affichageBorne + "  " + sb2.toString());
        }
        System.out.println("\n" + MARGE + "Pioche : " + p.getPioche().size() + " carte(s) restante(s)");
    }

    private String formaterCarte(Carte c, boolean alignerDroite) {
        if (c == null) {
            return String.format("%" + LARGEUR_CARTE + "s", "");
        } 

        String[] infos = analyserCarte(c.getNom());
        String numero = infos[0];
        String codeCouleur = infos[1];

        String contenuVisible = "[" + numero + "]";
        
        String contenuColore = "[" + codeCouleur + numero + RESET + "]";

        int nbEspaces = LARGEUR_CARTE - contenuVisible.length();
        if (nbEspaces < 0) nbEspaces = 0;
        
        StringBuilder espaces = new StringBuilder();
        for (int i = 0; i < nbEspaces; i++) {
            espaces.append(" ");
        }

        if (alignerDroite) {
            return espaces.toString() + contenuColore;
        } else {
            return contenuColore + espaces.toString();
        }
    }

    private String[] analyserCarte(String nomBrut) {
        String numero = "?";
        String couleur = RESET;

        if (nomBrut == null || !nomBrut.contains("_")) {
            return new String[]{nomBrut, RESET};
        }

        String[] parts = nomBrut.split("_");
        String valStr = parts[0];
        String coulStr = parts[1];

        switch (valStr) {
            case "UN": numero = "1"; break;
            case "DEUX": numero = "2"; break;
            case "TROIS": numero = "3"; break;
            case "QUATRE": numero = "4"; break;
            case "CINQ": numero = "5"; break;
            case "SIX": numero = "6"; break;
            case "SEPT": numero = "7"; break;
            case "HUIT": numero = "8"; break;
            case "NEUF": numero = "9"; break;
            default: numero = valStr.substring(0, 1);
        }

        switch (coulStr) {
            case "VERT": couleur = VERT; break;
            case "ROUGE": couleur = ROUGE; break;
            case "JAUNE": couleur = JAUNE; break;
            case "BLEU": couleur = BLEU; break;
            case "VIOLET": couleur = VIOLET; break;
            case "MARRON": couleur = ORANGE; break; 
            default: couleur = GRIS; break;
        }

        return new String[]{numero, couleur};
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

    public void afficherChoixModeDeJeux(ModeDeJeu m){
        System.out.println("#####  MODE DE JEU #####");
        System.out.println("[C] Classique");
        System.out.println("[T] Tactique");
        System.out.println("[R] Retour");
        System.out.println("[ ] Que voulez-vous faire ?");
    }

    public void afficherChoixJoueur(Joueur j, int i){
        System.out.println("#####  Joueur "+i+" #####");
        System.out.println("[N] Nom : "+j.getNom());
        String afficheStrategie = "";
        if (j.isAI()) {
            afficheStrategie = j.getStrategie().getName();
        } else {
            afficheStrategie = "Aucune";
        }
        System.out.println("[ ] Strategie : "+afficheStrategie);
        System.out.println("    [S] Aucune (joueur humain)");
        System.out.println("    [A] Aleatoire");
        System.out.println("    [C] Combine");
        System.out.println("[R] Retour");
        System.out.println("[ ] Que voulez-vous faire ?");
    }

    public void afficherMain(String joueurName, List<Carte> main){
        System.out.println("\n" + MARGE + "--- Main de " + joueurName + " ---");
        System.out.print("\n");
        System.out.print(MARGE); // Décalage de la main
        for (int i = 0; i < main.size(); i++) {
            Carte c = main.get(i);
            System.out.print(String.format(" %s  ", formaterCarte(c, false)));
        }
        System.out.print("\n");
        System.out.println();
    }
}
