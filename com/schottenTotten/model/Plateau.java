package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau {

    private List<Borne> bornes;
    private List<Carte> pioche;

    public List<Borne> getBornes() { return this.bornes; }
    public List<Carte> getPioche() { return this.pioche; }

    Plateau(int nbBornes){
        bornes = new ArrayList<Borne>();
        for (int i = 0; i < nbBornes; i++) {
            bornes.add(new Borne(i));
        }

        pioche = new ArrayList<Carte>();
        for (Couleur c : Couleur.values()) {
            for (Valeur v : Valeur.values()) {
                pioche.add(new CarteNormale(v, c));
            }
        }
        Collections.shuffle(pioche);
    }
}
