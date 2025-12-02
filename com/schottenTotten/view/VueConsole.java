package com.schottenTotten.view;

import java.util.List;

import com.schottenTotten.model.*;
import com.schottenTotten.controller.*;

public class VueConsole implements Vue {

    public void afficherMenuPrincipal(){
        System.out.println("#####  MENU PRINCIPAL #####");
        System.out.println("[J] Jouer");
        System.out.println("[P] Paramètres");
        System.out.println("[Q] Quitter");
        System.out.println("[ ] Que voulez-vous faire ?");

    }

    public void afficherPlateau(Plateau p, ModeDeJeu mode){
        System.out.println("#####################  PLATEAU ##################### ");
        System.out.println("Joueur 1\t\tBornes\t\t\tJoueur 2");

        List<Borne> bs = p.getBornes();
        List<Carte> pioche = p.getPioche();

        for (Borne b : bs) {

            int num = b.getNumero();
            String affichage = "";
            Borne.Statut st = b.getStatut();

            if (st == Borne.Statut.NON_RECLAMEE) {
                affichage = " "+num+" ";
            } else if (st == Borne.Statut.RECLAMEE_J1) {
                affichage = "<"+num+" ";
            } else if (st == Borne.Statut.RECLAMEE_J2) {
                affichage = " "+num+">";
            }

            int cartesMaxParBornes = mode.getCartesMaxParBornes();

            List<Carte> l1 = b.getCarteJoueur1();
            String affichCarte1 = "";
            int nbCartel1 = l1.size();
            for (int i = 0; i < cartesMaxParBornes; i++) {
                if (i <  nbCartel1) {
                    affichCarte1 = "["+l1.get(i).getNom()+"]" + affichCarte1;
                } else {
                    affichCarte1 = "\t" + affichCarte1;
                }
            }

            List<Carte> l2 = b.getCarteJoueur2();
            String affichCarte2 = "";
            int nbCartel2 = l2.size();
            for (int i = 0; i < cartesMaxParBornes; i++) {
                if (i <  nbCartel2) {
                    affichCarte2 += "["+l2.get(i).getNom()+"]";
                } else {
                    affichCarte2 += "\t";
                }
            }
            
            System.out.println(affichCarte1+"["+affichage+"]"+affichCarte2);
        }
        System.out.println("Pioche : "+pioche.size()+" carte(s) restante(s)");
    }

    public void afficherParamètres(Jeu j){
        System.out.println("#####  PARAMETRES #####");
        System.out.println("[M] Mode de Jeu : " + j.getModeDeJeu().getName());
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            Joueur joueur = j.getJoueur(i);
            String type = joueur.isAI() ? "(IA)" : "";
            String number = "["+(i+1)+"] ";
            System.out.println(number+"Joueur "+(i+1)+" : "+joueur.getNom()+" "+type);
        }
        System.out.println("[Q] Quitter");
        System.out.println("[ ] Que voulez-vous faire ?");
    }

    public void afficherMain(String joueurName, List<Carte> main){
        System.out.println("A ton tour "+joueurName+", voici ta main :");
        for (int i = 0; i < main.size(); i++) {
            System.out.println("["+(i+1)+"] "+main.get(i).getNom());
        }
    };
}
