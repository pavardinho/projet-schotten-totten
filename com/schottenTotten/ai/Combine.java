package com.schottenTotten.ai;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.model.Action;
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.CarteNormale;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Combine implements Strategie {

    @Override

    public Action choisirAction(Jeu jeu, Joueur joueur) {
        List<Carte> main = joueur.getMain();
        List<Borne> bornes = jeu.getPlateau().getBornes();

        Action meilleurCoup = null;
        int meilleurscore = -1000;

        for (Carte c : main) {

            // On vérifie seulement les cartes normales pour l'instant
            if (!(c instanceof CarteNormale)) continue; 
            CarteNormale carteMain = (CarteNormale) c;

            for (Borne b : bornes) {

                if(b.estPleine(joueur) || b.estControlee()) {
                    continue;
                }
                int scoreDuCoup = calculerScoreCoup(carteMain, b, joueur);

                if (scoreDuCoup > meilleurscore) {
                    meilleurscore = scoreDuCoup;
                    meilleurCoup = new Action(c, b);
                }
            }
        }
        return meilleurCoup;
    }


    private int calculerScoreCoup(CarteNormale carteMain, Borne b, Joueur joueur) {
        int score = 0;

        List<Carte> cartesSurBorne = b.getCartesJoueur(joueur);

        if (cartesSurBorne.isEmpty()) {
            return carteMain.getValeur().ordinal();
        }

        for (Carte c : cartesSurBorne) {
            if (c instanceof CarteNormale) {
                CarteNormale cPosee = (CarteNormale) c;

                if (cPosee.getCouleur() == carteMain.getCouleur()) {
                    score += 20;
                    int difference = Math.abs(cPosee.getValeur().ordinal() - carteMain.getValeur().ordinal());

                    if (difference == 1) {
                        score += 15;
                    } else if (difference == 2) {
                        score += 10;    
                    }
                }

                if (cPosee.getValeur() == carteMain.getValeur()) {
                    score += 10;
                }
            }
        }
        return score;
    }
}
