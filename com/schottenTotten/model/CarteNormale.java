package com.schottenTotten.model;

enum Couleur {A, B, C, D, E, F}
enum Valeur {UN, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF}

public class CarteNormale extends Carte {
    private Valeur valeur;
    private Couleur couleur;

    public Valeur getValeur() { return this.valeur; }
    public Couleur getCouleur() { return this.couleur; }

    public CarteNormale(Valeur v, Couleur c){
        this.valeur = v;
        this.couleur = c;
    }
}
