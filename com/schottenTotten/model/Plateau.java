package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau {

    private List<Borne> bornes;
    private List<Carte> pioche;

    public List<Borne> getBornes() { return this.bornes; }
    public List<Carte> getPioche() { return this.pioche; }

    public Plateau(int nbBornes){
        bornes = new ArrayList<Borne>();
        for (int i = 0; i < nbBornes; i++) {
            bornes.add(new Borne(i+1));
        }

        pioche = new ArrayList<Carte>();
        for (CarteNormale.Couleur c : CarteNormale.Couleur.values()) {
            for (CarteNormale.Valeur v : CarteNormale.Valeur.values()) {
                pioche.add(new CarteNormale(v, c));
            }
        }
        Collections.shuffle(pioche);
    }
}
