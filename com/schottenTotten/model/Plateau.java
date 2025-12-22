package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau {

    private List<Borne> bornes;
    private List<Carte> pioche;
    private List<Carte> piocheTactique;
    private List<Carte> defausse;

    public List<Borne> getBornes() { return this.bornes; }
    public List<Carte> getPioche() { return this.pioche; }
    public List<Carte> getPiocheTactique() { return this.piocheTactique; }
    public List<Carte> getDefausse() { return this.defausse; }

    public void addPioche(Carte c) { this.pioche.add(c); }

    public void setPiocheTactique(List<Carte> piocheTactique) { this.piocheTactique = piocheTactique; }
    public void addPiocheTactique(Carte c) { this.piocheTactique.add(c); }

    public void addDefausse(Carte carte) {
        this.defausse.add(carte);
    }

    public int getNbCartesPioche() { return this.pioche.size(); }
    public int getNbCartesPiocheTactique() { return this.piocheTactique.size(); }

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

        piocheTactique = null;
        defausse = new ArrayList<Carte>();
    }

    public Carte piocher() {
        if (this.pioche == null || this.pioche.isEmpty()) {
            return null;
        }
        return this.pioche.remove(0);
    }

    public Carte piocherTactique() {
        if (this.piocheTactique == null || this.piocheTactique.isEmpty()) {
            return null;
        }
        return this.piocheTactique.remove(0);
    }

    // Constructeur secondaire : Spécialement pour les tests que j'ai réalisé
    public Plateau(List<Borne> bornes, List<Carte> pioche) {
        this.bornes = bornes;
        this.pioche = pioche;
    }
}
