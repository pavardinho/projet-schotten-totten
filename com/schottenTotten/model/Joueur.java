package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;
import com.schottenTotten.ai.*;
import com.schottenTotten.controller.Jeu;

public class Joueur {

    private String nom;
    private List<Carte> main;
    private Strategie strategie;
    private int nbBornesControllees;

    public String getNom () { return this.nom; }
    public List<Carte> getMain() { return this.main; }
    public Strategie getStrategie() { return this.strategie; }

    public void setNom(String s) { this.nom = s; }
    public void setStrategie(Strategie s) { this.strategie = s; }

    public void resetMain() { this.main = new ArrayList<>(); }
    public void resetNbBornesControllees() { this.nbBornesControllees = 0; }

    public Joueur(String nom, Strategie strategie){
        this.nom = nom;
        this.main = new ArrayList<>();
        this.strategie = strategie;
        this.nbBornesControllees = 0;
    }

    public void addNbBornesControllees() { this.nbBornesControllees += 1; }
    public int getNbBornesControllees() { return this.nbBornesControllees; }

    public void recevoirCarte(Carte carte) { /// FONCITON POUR RECEVOIR UNE CARTE (A METTRE EN COMMUN AVEC LE CODE DANS CONTROLLER.JAVA)
        this.main.add(carte);
    }

    public void retirerCarte(Carte carte) { /// FONCTION POUR RETIRER UNE CARTE DE LA MAIN (A METTRE EN COMMUN AVEC LE CODE DANS CONTROLLER.JAVA)
        this.main.remove(carte);
    }

    public boolean isAI() { return strategie != null; }

    public Action demanderAction(Jeu jeu) {
        if (isAI()) {
            return this.strategie.choisirAction(jeu, this);
        }
        return null;
    }
}
