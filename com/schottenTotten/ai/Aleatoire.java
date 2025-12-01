package com.schottenTotten.ai;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.model.Action;
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Aleatoire implements Strategie {
    // Cette première stratégie d'ia selectionne une carte au hasard parmi les cartes jouables

    private Random random = new Random();

    public Action choisirAction(Jeu jeu, Joueur joueur) {

        // On choisit d'abord un carte
        List<Carte> main = joueur.getMain();

        if (main == null || main.isEmpty()) { // Cas ou lla main du joueur est vide
            return null; 
        }

        int id_Carte = random.nextInt(main.size());
        Carte carte_choisie = main.get(id_Carte);

        // On choisit maintenant une borne

        List<Borne> bornes = jeu.getPlateau().getBornes();

        List<Borne> bornes_jouables = new ArrayList<>();

        for (Borne b : bornes) {
            if (!b.estPleine(joueur) && !b.estControlee()) {
                bornes_jouables.add(b);
            }
        }

        if (bornes_jouables.isEmpty()) { // Cas ou il n'y a pas de borne jouable
            return null; 
        }

        int id_Borne = random.nextInt(bornes_jouables.size());
        Borne borne_choisie = bornes_jouables.get(id_Borne);

        return new Action(carte_choisie, borne_choisie);
    }
}
