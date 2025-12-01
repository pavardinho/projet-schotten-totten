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
    public void afficherPlateau(Plateau p){
        System.out.println("#####  PLATEAU #####");
        System.out.println("Joueur 1\t\tBornes\t\t\tJoueur 2");
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

    public void afficherMain(List<Carte> main){

    };
}
